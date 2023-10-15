package com.postech.parquimetro.view.form.pagamentos;

import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

public record EditarCreditoForm(
	@NotEmpty(message = "o campo nomeDoTitular e obrigatorio")
	@Length(min = 2, max = 30, message = "o nomeDoTitular deve conter no maximo 30 caracteres")
	String nomeDoTitular,

	@NotEmpty(message = "o campo dataDeValidade e obrigatorio")
	@Length(min = 7, max = 7, message = "o campo dataDeValidade invalido. Envie no seguinte formato mes e ano. Exemplo: 01/1998")
	String dataDeValidade
) {
}
