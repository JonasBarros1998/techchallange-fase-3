package com.postech.parquimetro.view.controller;

import com.postech.parquimetro.aplicacao.parquimetro.EnviarEmailsParaAlerta;
import com.postech.parquimetro.view.form.parquimetro.AlertasDoAgendamentoForm;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.springframework.stereotype.Service;

@Service
public class EnviarEmailSqsListener {

	EnviarEmailsParaAlerta enviarEmailsDeAlerta = new EnviarEmailsParaAlerta();

	public EnviarEmailSqsListener() {}

	@SqsListener("postech_enviar_email")
	public void enviarEmail(AlertasDoAgendamentoForm alertasDoAgendamentoForm) {
		this.enviarEmailsDeAlerta.enviar(alertasDoAgendamentoForm);
	}

}
