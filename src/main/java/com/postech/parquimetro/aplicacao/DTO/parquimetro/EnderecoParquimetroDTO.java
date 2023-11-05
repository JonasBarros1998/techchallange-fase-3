package com.postech.parquimetro.aplicacao.DTO.parquimetro;

public record EnderecoParquimetroDTO(
	String rua,
	String cidade,
	String estado,
	String cep,
	String descricao
) {
}
