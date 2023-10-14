package com.postech.parquimetro.aplicacao.DTO.pagamentos;

import com.postech.parquimetro.view.form.pagamentos.PixForm;

public record PixDTO(
	String condutor,
	String chavePix
) {

	public static PixDTO converterDePixFormParaPixDTO(PixForm pixForm) {
		return new PixDTO(
			pixForm.condutor(),
			pixForm.chavePix()
		);
	}
}
