package com.postech.parquimetro.aplicacao.DTO.pagamentos;

import com.postech.parquimetro.view.form.pagamentos.PixForm;

import java.util.UUID;

public record PixDTO(
	UUID condutor,
	String chavePix
) {

	public static PixDTO converterDePixFormParaPixDTO(PixForm pixForm) {
		return new PixDTO(
			pixForm.condutor(),
			pixForm.chavePix()
		);
	}
}
