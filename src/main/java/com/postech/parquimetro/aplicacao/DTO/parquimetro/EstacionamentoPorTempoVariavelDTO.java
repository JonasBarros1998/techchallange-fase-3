package com.postech.parquimetro.aplicacao.DTO.parquimetro;

import com.postech.parquimetro.view.form.parquimetro.EstacionamentoPorTempoVariavelForm;

import java.util.UUID;

public record EstacionamentoPorTempoVariavelDTO(

	UUID condutor,
	UUID metodoDePagamento,
	EnderecoParquimetroDTO endereco
) {

	public static EstacionamentoPorTempoVariavelDTO converterEstacionamentoPorTempoVariavelFormParaEstacionamentoPorTempoVariavelDTO(
		EstacionamentoPorTempoVariavelForm estacionamentoPorTempoVariavel
	) {

		var endereco = new EnderecoParquimetroDTO(
			estacionamentoPorTempoVariavel.endereco().rua(),
			estacionamentoPorTempoVariavel.endereco().cidade(),
			estacionamentoPorTempoVariavel.endereco().estado(),
			estacionamentoPorTempoVariavel.endereco().cep(),
			estacionamentoPorTempoVariavel.endereco().descricao()
		);

		return new EstacionamentoPorTempoVariavelDTO(
			estacionamentoPorTempoVariavel.condutor(),
			estacionamentoPorTempoVariavel.metodoDePagamento(),
			endereco
		);
	}
}
