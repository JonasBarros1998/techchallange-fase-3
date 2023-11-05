package com.postech.parquimetro.aplicacao.parquimetro;

import com.postech.parquimetro.aplicacao.DTO.parquimetro.AgendamentosDTO;
import com.postech.parquimetro.dominio.EnviarEmailMensagens;
import com.postech.parquimetro.dominio.entities.parquimetro.RegistroDeEstacionamento;
import com.postech.parquimetro.infra.agendamento.RemoverAgendamentos;
import com.postech.parquimetro.infra.email.Email;
import com.postech.parquimetro.infra.repository.RegistroDeEstacionamentoRepository;
import com.postech.parquimetro.view.form.parquimetro.FinalizarAgendamentoForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FinalizarAgendamentoPorTempoFixo {

	private RemoverAgendamentos removerAgendamentos = new RemoverAgendamentos();

	private RegistroDeEstacionamentoRepository registroDeEstacionamentoRepository;

	private Email email = new Email();

	@Autowired
	public FinalizarAgendamentoPorTempoFixo(RegistroDeEstacionamentoRepository registroDeEstacionamentoRepository) {
		this.registroDeEstacionamentoRepository = registroDeEstacionamentoRepository;
	}

	public FinalizarAgendamentoPorTempoFixo() {}

	public void remover(FinalizarAgendamentoForm finalizarAgendamentoForm) {
		var finalizarAgendamentoDTO = AgendamentosDTO.converterDe(finalizarAgendamentoForm);

		RegistroDeEstacionamento registroDoEstacionamento = this.consultarRegistroDePagamentoPorID(finalizarAgendamentoDTO);
		registroDoEstacionamento.finalizar();

		this.registroDeEstacionamentoRepository.save(registroDoEstacionamento);
		this.removerAgendamentos.remover(registroDoEstacionamento.getNomeDoEventoParaAlertas());
		this.removerAgendamentos.remover(registroDoEstacionamento.getNomeDoEventoParaAgendamento());

		this.email.enviar(
			registroDoEstacionamento.getCondutor().getEmail(),
			EnviarEmailMensagens.paraFinalizacao(registroDoEstacionamento.getExtratoDePagamento().getValor())
		);

	}

	private RegistroDeEstacionamento consultarRegistroDePagamentoPorID(
		AgendamentosDTO finalizarAgendamentoDTO
	) {
		Optional<RegistroDeEstacionamento> registro = this.registroDeEstacionamentoRepository
			.findById(finalizarAgendamentoDTO.agendamentoID());

		return registro.orElseThrow(null);
	}


}
