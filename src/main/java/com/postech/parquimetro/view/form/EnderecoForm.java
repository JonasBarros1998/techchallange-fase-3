package com.postech.parquimetro.view.form;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

public record EnderecoForm(

	@NotEmpty(message = "o campo rua nao pode ser vazio")
	@Length(max = 100, message = "o campo rua deve ter no maximo 100 caracteres")
	String rua,

	@NotEmpty(message = "o campo numero nao pode ser vazio")
	@Length(max = 10, message = "o campo numero deve ter no maximo 10 caracteres")
	String numero,

	@NotEmpty(message = "o campo cidade nao pode ser vazio")
	@Length(max = 40, message = "o campo cidade deve ter no maximo 40 caracteres")
	String cidade,

	@NotEmpty(message = "o campo estado nao pode ser vazio")
	@Length(min = 2, max = 2, message = "o campo estado deve ter no maximo 2 caracteres. Envie somente a UF do seu estado")
	String estado,

	@NotEmpty(message = "o campo cep nao pode ser vazio")
	@Length(min = 8, max = 8, message = "o campo cep deve ter 8 caracteres.")
	String cep,

	@Nullable
	@Length(max = 40, message = "o campo complemento deve ter no maximo 40 caracteres")
	String complemento
){}
