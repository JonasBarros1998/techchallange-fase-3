package com.postech.parquimetro.view.form.parquimetro;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record EstacionamentoPorTempoVariavelForm(

	@NotNull
	UUID condutor,

	@NotNull
	UUID metodoDePagamento,

	@NotNull
	@Valid
	EnderecoDoEstacionamentoForm endereco
) {

}
