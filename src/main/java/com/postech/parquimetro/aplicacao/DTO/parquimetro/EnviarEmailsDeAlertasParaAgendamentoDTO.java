package com.postech.parquimetro.aplicacao.DTO.parquimetro;

import com.postech.parquimetro.aplicacao.enums.TipoDoAgendamento;
import com.postech.parquimetro.view.form.parquimetro.AlertasDoAgendamentoForm;

import java.util.UUID;

public record EnviarEmailsDeAlertasParaAgendamentoDTO(
	String email,
	UUID condutorID,
	TipoDoAgendamento tipoDoAgendamento
) {

	public static EnviarEmailsDeAlertasParaAgendamentoDTO converterAgendamentoFormParaEnviarEmailsDeAlertasParaAgendamento(
		AlertasDoAgendamentoForm agendamento
	) {
		return new EnviarEmailsDeAlertasParaAgendamentoDTO(
			agendamento.email(),
			agendamento.condutorID(),
			agendamento.tipoDoAgendamento()
		);
	}
}
