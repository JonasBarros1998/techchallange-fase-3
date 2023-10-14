package com.postech.parquimetro.view.form.pagamentos;

import com.postech.parquimetro.dominio.entities.enums.TiposDePagamento;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record CreditoForm(

	@NotEmpty(message = "o campo condutor e obrigatorio")
	@Length(min = 11, max = 11, message = "campo cpf deve ter 11 digitos")
	String condutor,

	@NotEmpty(message = "o campo numeroDoCartao e obrigatorio")
	@Length(min = 16, max = 16, message = "o numeroDoCartao deve contrar 16 caracteres")
	String numeroDoCartao,

	@NotEmpty(message = "o campo nomeDoTitular e obrigatorio")
	@Length(min = 2, max = 30, message = "o nomeDoTitular deve conter no maximo 30 caracteres")
	String nomeDoTitular,

	@NotEmpty(message = "o campo bandeira e obrigatorio")
	@Length(max = 30, message = "o campo bandeira deve conter no maximo 30 caracteres")
	String bandeira,

	@NotEmpty(message = "o campo codigoDeSeguranca e obrigatorio")
	@Length(min = 3, max = 3, message = "o campo codigoDeSeguranca deve conter 3 caracteres")
	String codigoDeSeguranca,

	@NotEmpty(message = "o campo codigoDeSeguranca e obrigatorio")
	@Length(min = 7, max = 7, message = "o campo dataDeValidade invalido. Envie no seguinte formato mes e ano. Exemplo: 01/1998")
	String dataDeValidade,

	@NotNull(message = "o campo metodoDePagamento e obrigatorio")
	@Valid
	TiposDePagamento metodoDePagamento
) {}
