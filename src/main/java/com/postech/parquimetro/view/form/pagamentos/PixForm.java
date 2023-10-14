package com.postech.parquimetro.view.form.pagamentos;

import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

public record PixForm(

	@NotEmpty(message = "o campo condutor e obrigatorio")
	@Length(min = 11, max = 11, message = "campo cpf deve ter 11 digitos")
	String condutor,

	@NotEmpty(message = "o campo chavePix e obrigatorio")
	@Length(min = 10, max = 70, message = "campo chavePix deve conter no minimo 10 e no maximo 70 caracteres")
	String chavePix
) {}
