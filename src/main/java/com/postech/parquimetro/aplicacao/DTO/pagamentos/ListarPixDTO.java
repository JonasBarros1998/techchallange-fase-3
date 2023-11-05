package com.postech.parquimetro.aplicacao.DTO.pagamentos;

import com.postech.parquimetro.dominio.enums.TiposDePagamento;

import java.util.UUID;

public record ListarPixDTO(

	UUID metodoDePagamentoID,

	UUID id,
	TiposDePagamento tipo,

	String chavePix
) {
}
