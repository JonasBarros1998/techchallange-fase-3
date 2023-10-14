package com.postech.parquimetro.dominio.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.stream.Stream;

public enum TiposDePagamento {

	PIX("PIX"),
	CREDITO("CREDITO"),
	DEBITO("DEBITO");

	private final String tipoDePagamento;

	TiposDePagamento(String tipoDePagamento) {
		this.tipoDePagamento = tipoDePagamento;
	}

	@JsonCreator
	public static TiposDePagamento decode(final String tipo) {
		return Stream.of(TiposDePagamento.values())
			.filter(targetEnum -> targetEnum.tipoDePagamento.equals(tipo))
			.findFirst()
			.orElseThrow();
	}

	@JsonValue
	public String getCode() {
		return tipoDePagamento;
	}

}
