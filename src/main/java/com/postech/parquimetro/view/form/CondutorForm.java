package com.postech.parquimetro.view.form;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record CondutorForm (

	@NotEmpty(message = "campo email e obrigatorio")
	@Length(max = 70, message = "campo email deve ter no maximo 70 caracteres")
	String email,

	@NotEmpty(message = "campo nome e obrigatorio")
	@Length(max = 70, message = "campo nome deve ter no maximo 100 caracteres")
	String nome,

	@NotEmpty(message = "campo cpf e obrigatorio")
	@Length(min = 11, max = 11, message = "campo cpf deve ter 11 digitos")
	String cpf,

	@NotEmpty(message = "campo telefone e obrigatorio")
	@Length(min = 11, max = 11, message = "campo telefone deve ter 11 digitos. Inclua o codigo da area (DDD)")
	String telefone,

	@Valid
	EnderecoForm endereco
) {

}
