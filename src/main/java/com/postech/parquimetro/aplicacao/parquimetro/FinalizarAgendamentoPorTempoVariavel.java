package com.postech.parquimetro.aplicacao.parquimetro;

import com.postech.parquimetro.aplicacao.DTO.ComprovanteDePagamentoDTO;
import com.postech.parquimetro.aplicacao.DTO.parquimetro.AgendamentosDTO;
import com.postech.parquimetro.dominio.CalcularValorDoPagamento;
import com.postech.parquimetro.dominio.entities.parquimetro.RegistroDeEstacionamento;
import com.postech.parquimetro.dominio.EnviarEmailMensagens;
import com.postech.parquimetro.infra.agendamento.RemoverAgendamentos;
import com.postech.parquimetro.infra.email.Email;
import com.postech.parquimetro.infra.repository.RegistroDeEstacionamentoRepository;
import com.postech.parquimetro.view.form.parquimetro.FinalizarAgendamentoForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Optional;

@Service
public class FinalizarAgendamentoPorTempoVariavel {

	private RegistroDeEstacionamentoRepository registroDeEstacionamentoRepository;

	private RemoverAgendamentos removerAgendamentos = new RemoverAgendamentos();

	private Email email = new Email();

	@Autowired
	FinalizarAgendamentoPorTempoVariavel(RegistroDeEstacionamentoRepository registroDeEstacionamentoRepository) {
		this.registroDeEstacionamentoRepository = registroDeEstacionamentoRepository;
	}

	public ComprovanteDePagamentoDTO finalizar(FinalizarAgendamentoForm finalizarAgendamentoForm) {

		LocalDateTime horaEDataDeFinalizacao = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));

		var finalizarAgendamentoDTO = AgendamentosDTO.converterDe(finalizarAgendamentoForm);

		RegistroDeEstacionamento registroDoEstacionamento = this.consultarRegistroDeEstacionamentoPorID(finalizarAgendamentoDTO);

		BigDecimal valorTotal = this.calcularValorTotal(registroDoEstacionamento.getDataDeEntrada(), horaEDataDeFinalizacao);

		registroDoEstacionamento.setTempoFinal(horaEDataDeFinalizacao);
		registroDoEstacionamento.getExtratoDePagamento().setValor(valorTotal);
		this.registroDeEstacionamentoRepository.save(registroDoEstacionamento);

		registroDoEstacionamento.finalizar();
		this.registroDeEstacionamentoRepository.save(registroDoEstacionamento);

		this.removerAgendamentos.remover(registroDoEstacionamento.getNomeDoEventoParaAlertas());

		this.enviarEmail(registroDoEstacionamento.getCondutor().getEmail(), valorTotal);

		return new ComprovanteDePagamentoDTO(
			NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(valorTotal),
			registroDoEstacionamento.getId(),
			registroDoEstacionamento.getTempoInicial(),
			registroDoEstacionamento.getTempoFinal());
	}

	private void enviarEmail(String email, BigDecimal valorDoPagamento) {

		this.email.enviar(
			email,
			EnviarEmailMensagens.paraFinalizacao(valorDoPagamento));
	}

	private RegistroDeEstacionamento consultarRegistroDeEstacionamentoPorID(AgendamentosDTO finalizarAgendamentoDTO) {
		Optional<RegistroDeEstacionamento> registro = this.registroDeEstacionamentoRepository
			.findById(finalizarAgendamentoDTO.agendamentoID());

		return registro.orElseThrow(null);
	}

	private BigDecimal calcularValorTotal(LocalDateTime entrada, LocalDateTime saida) {
		var calcularValorDoPagamento = new CalcularValorDoPagamento();
		return calcularValorDoPagamento
			.calcular(entrada, saida);
	}

}
