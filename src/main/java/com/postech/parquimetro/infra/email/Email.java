package com.postech.parquimetro.infra.email;

import com.postech.parquimetro.infra.exceptions.NaoFoiPossivelEnviarEmail;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.*;

public class Email {

	public void enviar(String destinatario, String corpoDoEmail) {

		SesClient sesCliente = SesClient.builder().build();

		Destination destino = Destination.builder()
			.toAddresses(destinatario)
			.build();

		Content conteudo = Content.builder()
			.data(corpoDoEmail)
			.build();

		Content titulo = Content.builder()
			.data("POSTECH - FASE 3 | Parquimetro")
			.build();

		Body body = Body.builder()
			.html(conteudo)
			.build();

		Message msg = Message.builder()
			.subject(titulo)
			.body(body)
			.build();

		SendEmailRequest emailRequest = SendEmailRequest.builder()
			.destination(destino)
			.message(msg)
			.source("jonas_barros@outlook.com")
			.build();

		try {
			sesCliente.sendEmail(emailRequest);
		} catch (SesException e) {
			throw new NaoFoiPossivelEnviarEmail(e.getMessage());
		}
	}
}

