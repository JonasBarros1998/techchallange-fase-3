package com.postech.parquimetro.aplicacao.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.stream.Stream;

public enum TipoDoAgendamento {

	PorTempoFixo("por_tempo_fixo"),
	PorTempoVariavel("por_tempo_variavel");

	private final String tipoDoAgendamento;

	TipoDoAgendamento(String tipoDoAgendamento) {
		this.tipoDoAgendamento = tipoDoAgendamento;
	}

	@JsonCreator
	public static TipoDoAgendamento decode(final String tipo) {
		return Stream.of(TipoDoAgendamento.values())
			.filter(targetEnum -> targetEnum.tipoDoAgendamento.equals(tipo))
			.findFirst()
			.orElseThrow();
	}

	@JsonValue
	public String getCode() {
		return tipoDoAgendamento;
	}
}
