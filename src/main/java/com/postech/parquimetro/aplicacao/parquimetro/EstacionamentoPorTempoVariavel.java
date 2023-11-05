package com.postech.parquimetro.aplicacao.parquimetro;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.postech.parquimetro.aplicacao.DTO.parquimetro.EnviarEmailsDeAlertasParaAgendamentoDTO;
import com.postech.parquimetro.aplicacao.DTO.parquimetro.EstacionamentoPorTempoVariavelDTO;
import com.postech.parquimetro.aplicacao.exceptions.ConteudoNaoEncontrado;
import com.postech.parquimetro.aplicacao.enums.TipoDoAgendamento;
import com.postech.parquimetro.dominio.CriarCronJob;
import com.postech.parquimetro.dominio.NaoIniciarOTemporizadorSeOCondutorNaoTiverAutomveisRegistrado;
import com.postech.parquimetro.dominio.NaoIniciarOTemporizadorSeOCondutorNaoTiverPagamentosRegistrado;
import com.postech.parquimetro.dominio.NaoIniciarOTemporizadorSeOCondutorSelecionarPagamentoDoTipoPix;
import com.postech.parquimetro.dominio.entities.Automovel;
import com.postech.parquimetro.dominio.entities.Condutor;
import com.postech.parquimetro.dominio.entities.parquimetro.RegistroDeEstacionamento;
import com.postech.parquimetro.dominio.entities.pagamento.ExtratoDePagamento;
import com.postech.parquimetro.dominio.entities.pagamento.MetodoDePagamento;
import com.postech.parquimetro.dominio.entities.parquimetro.EnderecoDoEstacionamento;
import com.postech.parquimetro.infra.agendamento.Agendar;
import com.postech.parquimetro.infra.repository.AutomovelRepository;
import com.postech.parquimetro.infra.repository.CondutorRepository;
import com.postech.parquimetro.infra.repository.PagamentoRepository;
import com.postech.parquimetro.infra.repository.RegistroDeEstacionamentoRepository;
import com.postech.parquimetro.view.form.parquimetro.EstacionamentoPorTempoVariavelForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EstacionamentoPorTempoVariavel {

	RegistroDeEstacionamentoRepository registroDeEstacionamentoRepository;

	CondutorRepository condutorRepository;

	PagamentoRepository pagamentoRepository;

	AutomovelRepository automovelRepository;

	Agendar agendar = new Agendar();

	private String nomeDoEventoParaAgendamento = String.format("agendamentoPorTempoVariavel_%s", UUID.randomUUID().toString());
	private String nomeDoEventoParaAlertas = String.format("alertaParaEnviarEmail_%s", UUID.randomUUID().toString());

	@Value("${aws.sqs.enviar_email_arn}")
	String enderecoSQSARN;

	@Autowired
	EstacionamentoPorTempoVariavel(
		RegistroDeEstacionamentoRepository registroDeEstacionamentoRepository,
		CondutorRepository condutorRepository,
		PagamentoRepository pagamentoRepository,
		AutomovelRepository automovelRepository) {
		this.registroDeEstacionamentoRepository = registroDeEstacionamentoRepository;
		this.condutorRepository = condutorRepository;
		this.pagamentoRepository = pagamentoRepository;
		this.automovelRepository = automovelRepository;

	}

	public void iniciarEstacionamentoPorTempoVariavel(EstacionamentoPorTempoVariavelForm estacionamentoPorTempoVariavel) {
		var estacionamentoPorTempoVariavelDTO = EstacionamentoPorTempoVariavelDTO
			.converterEstacionamentoPorTempoVariavelFormParaEstacionamentoPorTempoVariavelDTO(estacionamentoPorTempoVariavel);

		Condutor condutor = condutorRepository.findCondutorByIdAndStatusIsTrue(estacionamentoPorTempoVariavelDTO.condutor())
			.orElseThrow(() -> new ConteudoNaoEncontrado("Nao foi possivel encontrar o condutor"));


		MetodoDePagamento pagamentoSelecionado = this
			.naoIniciarOTemporizadorSeNaoTiverEsteMetodoDePagamentoRegistrado(estacionamentoPorTempoVariavelDTO);

		this.naoIniciarOTemporizadorSeOMetodoDePagamentoForDoTipoPix(estacionamentoPorTempoVariavelDTO);

		this.naoIniciarOTemporizadorSeNaoTiverAutomoveisRegistrados(condutor.getAutomovel());

		var extratoDePagamento = new ExtratoDePagamento(LocalDateTime.now(), pagamentoSelecionado);

		var enderecoDoEstacionamento = new EnderecoDoEstacionamento(
			estacionamentoPorTempoVariavelDTO.endereco().rua(),
			estacionamentoPorTempoVariavelDTO.endereco().cidade(),
			estacionamentoPorTempoVariavelDTO.endereco().estado(),
			estacionamentoPorTempoVariavelDTO.endereco().cep(),
			estacionamentoPorTempoVariavelDTO.endereco().descricao());

		LocalDateTime dataDeInicio = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));

		var registroDeEstacionamento = new RegistroDeEstacionamento(
			condutor,
			enderecoDoEstacionamento,
			extratoDePagamento,
			dataDeInicio,
			this.nomeDoEventoParaAlertas,
			this.nomeDoEventoParaAgendamento
		);

		this.registroDeEstacionamentoRepository.save(registroDeEstacionamento);

		this.iniciarTemporizador(condutor);

	}

	private MetodoDePagamento naoIniciarOTemporizadorSeNaoTiverEsteMetodoDePagamentoRegistrado(
		EstacionamentoPorTempoVariavelDTO estacionamentoPorTempoFixoDTO
	) {
		Optional<MetodoDePagamento> pagamento = this.pagamentoRepository
			.findById(estacionamentoPorTempoFixoDTO.metodoDePagamento());

		MetodoDePagamento pagamentoSelecionado = NaoIniciarOTemporizadorSeOCondutorNaoTiverPagamentosRegistrado
			.verificar(pagamento);

		return pagamentoSelecionado;
	}

	private MetodoDePagamento naoIniciarOTemporizadorSeOMetodoDePagamentoForDoTipoPix(
		EstacionamentoPorTempoVariavelDTO estacionamentoPorTempoFixoDTO
	) {
		Optional<MetodoDePagamento> pagamento = this.pagamentoRepository
			.findById(estacionamentoPorTempoFixoDTO.metodoDePagamento());

		MetodoDePagamento pagamentoSelecionado = NaoIniciarOTemporizadorSeOCondutorSelecionarPagamentoDoTipoPix
			.verificar(pagamento);

		return pagamentoSelecionado;
	}

	private List<Automovel> naoIniciarOTemporizadorSeNaoTiverAutomoveisRegistrados(List<Automovel> automoveis) {
		NaoIniciarOTemporizadorSeOCondutorNaoTiverAutomveisRegistrado.verificar(automoveis);
		return automoveis;
	}


	private void iniciarTemporizador(Condutor condutor) {

		var agendamento = new EnviarEmailsDeAlertasParaAgendamentoDTO(
			condutor.getEmail(),
			condutor.getId(),
			TipoDoAgendamento.PorTempoVariavel
		);

		try {
			var criarCronJob = new CriarCronJob();

			var mapper = new ObjectMapper();
			String body = mapper.writer().writeValueAsString(agendamento);

			this.agendar.porTempoVariavel(
				this.nomeDoEventoParaAlertas,
				body,
				criarCronJob.paraAgendamentosPorTempoVariavel(),
				enderecoSQSARN);

		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

}
