package com.postech.parquimetro.aplicacao.DTO;

public record EnderecoDTO(
	String rua,
	String numero,
	String cidade,
	String estado,
	String cep,
	String complemento
) {
}
