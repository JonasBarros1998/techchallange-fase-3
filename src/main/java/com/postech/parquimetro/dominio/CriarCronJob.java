package com.postech.parquimetro.dominio;

import java.time.*;

public class CriarCronJob {


	private Duration minutos = Duration.ofMinutes(10);

	/*
	 * Cria uma duracao de 10 minutos, que ira subtrair o tempo final para criacao do alerta
	 * de envio de e-mails
	 * */
	public String paraEnvioDeAlerta(LocalDateTime tempoFinal) {

		/**
		 * Por conta dos horarios gerenciados pelo event bridge que esta no padrao UTC,
		 * e recebemos da API o padrao UTC-3, temos que converter para UTC e enviar ao event bridge
		 */
		ZonedDateTime agendamentoUTC = tempoFinal.atZone(ZoneOffset.ofHours(-3));
		LocalDateTime agendamentoParaAlerta = agendamentoUTC
			.withZoneSameInstant(ZoneId.of("UTC"))
			.toLocalDateTime()
			.minus(minutos);

		return String.format("cron(%s %s %s %s ? %s)",
			agendamentoParaAlerta.getMinute(),
			agendamentoParaAlerta.getHour(),
			agendamentoParaAlerta.getDayOfMonth(),
			agendamentoParaAlerta.getMonthValue(),
			agendamentoParaAlerta.getYear());

	}




	public String paraAgendamentosPorTempoFixo(LocalDateTime tempoFinal) {

		/**
		 * Por conta dos horarios gerenciados pelo event bridge que esta no padrao UTC,
		 * e recebemos da API o padrao UTC-3, temos que converter para UTC e enviar ao event bridge
		 */
		ZonedDateTime agendamentoUTC = tempoFinal.atZone(ZoneOffset.ofHours(-3));
		LocalDateTime agendamentoParaAlerta = agendamentoUTC
			.withZoneSameInstant(ZoneId.of("UTC"))
			.toLocalDateTime();

		return String.format("cron(%s %s %s %s ? %s)",
			agendamentoParaAlerta.getMinute(),
			agendamentoParaAlerta.getHour(),
			agendamentoParaAlerta.getDayOfMonth(),
			agendamentoParaAlerta.getMonthValue(),
			agendamentoParaAlerta.getYear());

	}

	public String paraAgendamentosPorTempoVariavel() {
		return "rate(60 minutes)";
	}

}
