package com.postech.parquimetro.aplicacao.DTO.parquimetro;

import com.postech.parquimetro.view.form.parquimetro.FinalizarAgendamentoForm;

import java.util.UUID;

public record AgendamentosDTO(
	UUID agendamentoID

) {

	public static AgendamentosDTO converterDe(FinalizarAgendamentoForm finalizarAgendamento) {
		return new AgendamentosDTO(
			finalizarAgendamento.agendamentoID()
		);
	}
}
