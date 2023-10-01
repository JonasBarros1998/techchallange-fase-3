package com.postech.parquimetro.utils;

public class ErroForm {
	private final String field;
	private final String error;

	public ErroForm(String field, String error) {
		this.field = field;
		this.error = error;
	}

	public String getField() {
		return field;
	}

	public String getError() {
		return error;
	}
}
