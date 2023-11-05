package com.postech.parquimetro.aplicacao.parquimetro;

import com.postech.parquimetro.aplicacao.DTO.parquimetro.EnviarEmailsDeAlertasParaAgendamentoDTO;
import com.postech.parquimetro.aplicacao.enums.TipoDoAgendamento;
import com.postech.parquimetro.dominio.EnviarEmailMensagens;
import com.postech.parquimetro.infra.email.Email;
import com.postech.parquimetro.view.form.parquimetro.AlertasDoAgendamentoForm;

public class EnviarEmailsParaAlerta {

	Email email = new Email();

	public void enviar(AlertasDoAgendamentoForm alertasDoAgendamento) {
		var agendamentoDTO = EnviarEmailsDeAlertasParaAgendamentoDTO
			.converterAgendamentoFormParaEnviarEmailsDeAlertasParaAgendamento(alertasDoAgendamento);

		if(agendamentoDTO.tipoDoAgendamento().getCode().equals(TipoDoAgendamento.PorTempoFixo.getCode())) {
			email.enviar(agendamentoDTO.email(), EnviarEmailMensagens.paraAlerta());
		} else {
			email.enviar(agendamentoDTO.email(), EnviarEmailMensagens.paraRenovacao());
		}

	}
}
