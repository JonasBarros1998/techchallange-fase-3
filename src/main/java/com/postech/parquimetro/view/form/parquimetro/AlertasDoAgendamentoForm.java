package com.postech.parquimetro.view.form.parquimetro;

import com.postech.parquimetro.aplicacao.enums.TipoDoAgendamento;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

public record AlertasDoAgendamentoForm(

	@NotEmpty(message = "campo email e obrigatorio")
	@Length(max = 70, message = "campo email deve ter no maximo 70 caracteres")
	String email,

	@NotNull
	UUID condutorID,

	@NotNull(message = "o campo tipo de tipoDoAgendamento e obrigatorio")
	@Valid
	TipoDoAgendamento tipoDoAgendamento
){}
