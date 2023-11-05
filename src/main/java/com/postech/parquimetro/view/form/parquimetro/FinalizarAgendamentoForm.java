package com.postech.parquimetro.view.form.parquimetro;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record FinalizarAgendamentoForm(

	@NotNull
	UUID agendamentoID
) {}
