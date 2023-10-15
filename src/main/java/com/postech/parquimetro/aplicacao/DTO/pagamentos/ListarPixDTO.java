package com.postech.parquimetro.aplicacao.DTO.pagamentos;

import com.postech.parquimetro.dominio.entities.enums.TiposDePagamento;

import java.util.UUID;

public record ListarPixDTO(
	UUID id,
	TiposDePagamento tipo,

	String chavePix
) {
}
