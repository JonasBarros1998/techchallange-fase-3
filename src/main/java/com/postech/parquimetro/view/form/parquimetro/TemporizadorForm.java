package com.postech.parquimetro.view.form.parquimetro;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record TemporizadorForm(

	@NotNull
	@FutureOrPresent
	LocalDateTime tempoInicial,

	@NotNull
	@Future
	LocalDateTime tempoFinal
) { }
