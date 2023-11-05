package com.postech.parquimetro.aplicacao.parquimetro;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.postech.parquimetro.aplicacao.DTO.parquimetro.EnviarEmailsDeAlertasParaAgendamentoDTO;
import com.postech.parquimetro.aplicacao.DTO.parquimetro.EstacionamentoPorTempoFixoDTO;
import com.postech.parquimetro.aplicacao.DTO.parquimetro.AgendamentosDTO;
import com.postech.parquimetro.aplicacao.exceptions.ConteudoNaoEncontrado;
import com.postech.parquimetro.aplicacao.enums.TipoDoAgendamento;
import com.postech.parquimetro.dominio.CriarCronJob;
import com.postech.parquimetro.dominio.NaoIniciarOTemporizadorSeOCondutorNaoTiverAutomveisRegistrado;
import com.postech.parquimetro.dominio.NaoIniciarOTemporizadorSeOCondutorNaoTiverPagamentosRegistrado;
import com.postech.parquimetro.dominio.CalcularValorDoPagamento;
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
import com.postech.parquimetro.view.form.parquimetro.EstacionamentoPorTempoFixoForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EstacionamentoPorTempoFixo {

	private RegistroDeEstacionamentoRepository registroDeEstacionamentoRepository;

	private CondutorRepository condutorRepository;

	private PagamentoRepository pagamentoRepository;

	private AutomovelRepository automovelRepository;

	private Agendar agendar = new Agendar();

	private String nomeDoEventoParaAgendamento = String.format("agendamentoPorTempoFixo_%s", UUID.randomUUID().toString());
	private String nomeDoEventoParaAlertas = String.format("alertaParaEnviarEmail_%s", UUID.randomUUID().toString());

	@Value("${aws.sqs.enviar_email_arn}")
	private String alertasNoEmailFilaSqs;

	@Value("${aws.sqs.remover_evento_arn}")
	private String removerEventoFilaSqs;

	@Autowired
	EstacionamentoPorTempoFixo(
		RegistroDeEstacionamentoRepository registroDeEstacionamentoRepository,
		CondutorRepository condutorRepository,
		PagamentoRepository pagamentoRepository,
		AutomovelRepository automovelRepository) {
		this.registroDeEstacionamentoRepository = registroDeEstacionamentoRepository;
		this.condutorRepository = condutorRepository;
		this.pagamentoRepository = pagamentoRepository;
		this.automovelRepository = automovelRepository;
	}

	public void iniciarEstacionamentoPorTempoFixo(EstacionamentoPorTempoFixoForm estacionamentoPorTempoFixo) {
		var estacionamentoPorTempoFixoDTO = EstacionamentoPorTempoFixoDTO
			.converterEstacionamentoPorTempoFixoFormParaEstacionamentoPorTempoFixoDTO(estacionamentoPorTempoFixo);

		Condutor condutor = condutorRepository.findCondutorByIdAndStatusIsTrue(estacionamentoPorTempoFixoDTO.condutor())
			.orElseThrow(() -> new ConteudoNaoEncontrado("Nao foi possivel encontrar o condutor"));

		MetodoDePagamento pagamentoSelecionado = this
			.NaoIniciarOTemporizadorSeOCondutorNaoTiverEsteMetodoDePagamentoRegistrado(estacionamentoPorTempoFixoDTO);

		this.NaoIniciarOTemporizadorSeOCondutorNaoTiverAutomoveisRegistrados(condutor.getAutomovel());

		var valorDoPagamento = new CalcularValorDoPagamento();
		BigDecimal valorTotalASerPago = valorDoPagamento.calcular(
			estacionamentoPorTempoFixoDTO.temporizador().tempoInicial(),
			estacionamentoPorTempoFixoDTO.temporizador().tempoFinal()
		);

		var extratoDePagamento = new ExtratoDePagamento(valorTotalASerPago, LocalDateTime.now(), pagamentoSelecionado);

		var enderecoDoEstacionamento = new EnderecoDoEstacionamento(
			estacionamentoPorTempoFixoDTO.endereco().rua(),
			estacionamentoPorTempoFixoDTO.endereco().cidade(),
			estacionamentoPorTempoFixoDTO.endereco().estado(),
			estacionamentoPorTempoFixoDTO.endereco().cep(),
			estacionamentoPorTempoFixoDTO.endereco().descricao());

		var registroDeEstacionamento = new RegistroDeEstacionamento(
			condutor,
			extratoDePagamento,
			enderecoDoEstacionamento,
			estacionamentoPorTempoFixoDTO.temporizador().tempoInicial(),
			estacionamentoPorTempoFixoDTO.temporizador().tempoFinal(),
			this.nomeDoEventoParaAlertas,
			this.nomeDoEventoParaAgendamento
		);

		this.registroDeEstacionamentoRepository.save(registroDeEstacionamento);

		this.iniciarTemporizador(registroDeEstacionamento);
		this.iniciarTemporizadorParaAlertas(condutor, estacionamentoPorTempoFixoDTO.temporizador().tempoFinal());

	}

	private MetodoDePagamento NaoIniciarOTemporizadorSeOCondutorNaoTiverEsteMetodoDePagamentoRegistrado(
		EstacionamentoPorTempoFixoDTO estacionamentoPorTempoFixoDTO
	) {
		Optional<MetodoDePagamento> pagamento = this.pagamentoRepository
			.findById(estacionamentoPorTempoFixoDTO.metodoDePagamento());

		MetodoDePagamento pagamentoSelecionado = NaoIniciarOTemporizadorSeOCondutorNaoTiverPagamentosRegistrado
			.verificar(pagamento);

		return pagamentoSelecionado;
	}

	private List<Automovel> NaoIniciarOTemporizadorSeOCondutorNaoTiverAutomoveisRegistrados(List<Automovel> automoveis) {
		NaoIniciarOTemporizadorSeOCondutorNaoTiverAutomveisRegistrado.verificar(automoveis);
		return automoveis;
	}


	/*
	* Ao iniciar o tmeporizador, ja criamos tambem a mensagem que será enviada a fila sqs,
	* com as informações necessárias para remoção dos eventos criado no evento bridge e
	* conclusão do agendamento no banco de dados
	* */
	private void iniciarTemporizador(RegistroDeEstacionamento registroDeEstacionamento) {

		var criarCronJob = new CriarCronJob();
		String cronJob = criarCronJob.paraAgendamentosPorTempoFixo(registroDeEstacionamento.getTempoFinal());

		var finalizarAgendamentoAoTerminarOEvento = new AgendamentosDTO(
			registroDeEstacionamento.getId()
		);

		try {
			var mapper = new ObjectMapper();
			String body = mapper.writer().writeValueAsString(finalizarAgendamentoAoTerminarOEvento);
			this.agendar.porTempoFixo(this.nomeDoEventoParaAgendamento, cronJob, body, this.removerEventoFilaSqs);

		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	private void iniciarTemporizadorParaAlertas(Condutor condutor, LocalDateTime tempoFinal) {

		var cronJob = new CriarCronJob();
		String agendamento = cronJob.paraEnvioDeAlerta(tempoFinal);

		var agendamentoDTO = new EnviarEmailsDeAlertasParaAgendamentoDTO(
			condutor.getEmail(),
			condutor.getId(),
			TipoDoAgendamento.PorTempoFixo
		);

		try {

			var mapper = new ObjectMapper();
			String body = mapper.writer().writeValueAsString(agendamentoDTO);
			this.agendar.porTempoFixo(this.nomeDoEventoParaAlertas, agendamento, body, this.alertasNoEmailFilaSqs);

		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

}
