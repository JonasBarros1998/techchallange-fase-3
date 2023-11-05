package com.postech.parquimetro.aplicacao.DTO.pagamentos;

import com.postech.parquimetro.dominio.enums.TiposDePagamento;

import java.util.UUID;

public record ListarCartaoDeCreditoDTO(

	UUID metodoDePagamentoID,

	UUID id,
	String nomeDoTitular,
	String dataDeValidade,
	TiposDePagamento tipo
) {}
