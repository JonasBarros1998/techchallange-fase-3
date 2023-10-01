package com.postech.parquimetro.application.DTO;

public record EnderecoDTO(
	String rua,
	String numero,
	String cidade,
	String estado,
	String cep,
	String complemento
) {
}
