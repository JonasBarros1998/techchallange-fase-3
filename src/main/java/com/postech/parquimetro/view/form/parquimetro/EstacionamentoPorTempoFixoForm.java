package com.postech.parquimetro.view.form.parquimetro;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record EstacionamentoPorTempoFixoForm(

	@NotNull
	UUID condutor,

	@NotNull
	UUID metodoDePagamento,

	@NotNull
	@Valid
	TemporizadorForm temporizador,

	@NotNull
	@Valid
	EnderecoDoEstacionamentoForm endereco
) {


}
