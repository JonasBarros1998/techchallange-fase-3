package com.postech.parquimetro.view.form.pagamentos;

import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

public record DebitoForm(

	@NotEmpty(message = "o campo condutor e obrigatorio")
	@Length(min = 11, max = 11, message = "campo cpf deve ter 11 digitos")
	String condutor,

	@NotEmpty(message = "o campo numeroDoCartao e obrigatorio")
	@Length(min = 16, max = 16, message = "O numeroDoCartao deve contrar 16 caracteres")
	String numeroDoCartao,

	@NotEmpty(message = "o campo bandeira e obrigatorio")
	@Length(max = 30, message = "o campo bandeira deve conter no maximo 50 caracteres")
	String nomeDoTitular,

	@NotEmpty(message = "o campo codigoDeSeguranca e obrigatorio")
	@Length(min = 3, max = 3, message = "o campo codigoDeSeguranca deve conter 3 caracteres")
	String codigoDeSeguranca,

	@NotEmpty(message = "o campo codigoDeSeguranca e obrigatorio")
	@Length(min = 7, max = 7, message = "o campo dataDeValidade invalido. Envie no seguinte formato mes e ano. Exemplo: 01/1998")
	String dataDeValidade,

	@NotEmpty(message = "o campo nomeDaInstituicaoFinanceira e obrigatorio")
	@Length(min = 1, max = 40)
	String nomeDaInstituicaoFinanceira,

	@NotEmpty(message = "o campo bandeira e obrigatorio")
	@Length(max = 30, message = "o campo bandeira deve conter no maximo 30 caracteres")
	String bandeira
) {}
