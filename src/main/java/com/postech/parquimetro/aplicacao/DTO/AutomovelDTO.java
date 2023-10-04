package com.postech.parquimetro.aplicacao.DTO;

import com.postech.parquimetro.view.form.AutomovelForm;

public record AutomovelDTO(
	String placa,
	String modelo,
	String tipoAutomovel,
	String condutor
) {

	public static AutomovelDTO converterAutomovelFormParaAutomovelDTO(AutomovelForm automovelForm) {
		return new AutomovelDTO(
			automovelForm.placa(),
			automovelForm.modelo(),
			automovelForm.tipoDoAutomovel(),
			automovelForm.condutor()
		);
	}
}
