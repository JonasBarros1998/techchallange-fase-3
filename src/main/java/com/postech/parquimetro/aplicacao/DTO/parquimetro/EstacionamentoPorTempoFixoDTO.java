package com.postech.parquimetro.aplicacao.DTO.parquimetro;

import com.postech.parquimetro.view.form.parquimetro.EstacionamentoPorTempoFixoForm;

import java.util.UUID;

public record EstacionamentoPorTempoFixoDTO(
	UUID condutor,
	UUID metodoDePagamento,
	TemporizadorDTO temporizador,
	EnderecoParquimetroDTO endereco
) {


	public static EstacionamentoPorTempoFixoDTO converterEstacionamentoPorTempoFixoFormParaEstacionamentoPorTempoFixoDTO(
		EstacionamentoPorTempoFixoForm estacionamentoPorTempoFixoForm
	) {
		var temporizadorDTO = new TemporizadorDTO(
			estacionamentoPorTempoFixoForm.temporizador().tempoInicial(),
			estacionamentoPorTempoFixoForm.temporizador().tempoFinal());

		var enderecoParquimetroDTO = new EnderecoParquimetroDTO(
			estacionamentoPorTempoFixoForm.endereco().rua(),
			estacionamentoPorTempoFixoForm.endereco().cidade(),
			estacionamentoPorTempoFixoForm.endereco().estado(),
			estacionamentoPorTempoFixoForm.endereco().cep(),
			estacionamentoPorTempoFixoForm.endereco().descricao()
		);

		return new EstacionamentoPorTempoFixoDTO(
			estacionamentoPorTempoFixoForm.condutor(),
			estacionamentoPorTempoFixoForm.metodoDePagamento(),
			temporizadorDTO,
			enderecoParquimetroDTO
		);


	}
}
