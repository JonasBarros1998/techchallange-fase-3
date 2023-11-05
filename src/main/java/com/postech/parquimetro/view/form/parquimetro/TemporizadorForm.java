package com.postech.parquimetro.view.form.parquimetro;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.TimeZone;

public record TemporizadorForm(

	@NotNull
	@FutureOrPresent()
	LocalDateTime tempoInicial,

	@NotNull
	@Future
	LocalDateTime tempoFinal

) {
}
