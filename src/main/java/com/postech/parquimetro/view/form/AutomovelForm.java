package com.postech.parquimetro.view.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

public record AutomovelForm(

	@NotEmpty(message = "o campo placa e obrigatorio")
	@Length(message = "o campo placa deve ter 7 caracteres", min = 7, max = 7)
	String placa,

	@NotEmpty(message = "o campo modelo e obrigatorio")
	@Length(message = "o campo modelo deve ter no maximo 30 caracteres", max = 30)
	String modelo,

	@NotEmpty(message = "o campo tipoAutomovel e obrigatorio")
	@Length(message = "o campo tipoAutmovel deve ter no maximo 20 caracteres", max = 20)
	String tipoDoAutomovel,

	@NotNull(message = "O campo condutor e obrigatorio")
	String condutor
) { }
