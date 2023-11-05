package com.postech.parquimetro.view.form.pagamentos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

public record PixForm(

	@NotNull
	UUID condutor,

	@NotEmpty(message = "o campo chavePix e obrigatorio")
	@Length(min = 10, max = 70, message = "campo chavePix deve conter no minimo 10 e no maximo 70 caracteres")
	String chavePix
) {}
