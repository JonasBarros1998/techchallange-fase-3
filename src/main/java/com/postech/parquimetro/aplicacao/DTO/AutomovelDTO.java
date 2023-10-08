package com.postech.parquimetro.aplicacao.DTO;

import com.postech.parquimetro.view.form.AutomovelForm;
import com.postech.parquimetro.view.form.EditarAutomovelForm;

import java.util.UUID;

public record AutomovelDTO(
	String placa,
	String modelo,
	String tipoAutomovel,
	UUID condutor
) {

	public static AutomovelDTO converterAutomovelFormParaAutomovelDTO(AutomovelForm automovelForm) {
		return new AutomovelDTO(
			automovelForm.placa(),
			automovelForm.modelo(),
			automovelForm.tipoDoAutomovel(),
			automovelForm.condutor()
		);
	}

	public static AutomovelDTO converterAutomovelFormParaAutomovelDTO(EditarAutomovelForm automovelForm) {
		return new AutomovelDTO(
			automovelForm.placa(),
			automovelForm.modelo(),
			automovelForm.tipoDoAutomovel(),
			null
		);
	}

}
