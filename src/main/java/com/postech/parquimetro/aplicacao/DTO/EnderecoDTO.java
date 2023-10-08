package com.postech.parquimetro.aplicacao.DTO;

import com.postech.parquimetro.view.form.EnderecoForm;

public record EnderecoDTO(
	String rua,
	String numero,
	String cidade,
	String estado,
	String cep,
	String complemento
) {
	public static EnderecoDTO converterEnderecoFormParaEnderecoDTO(EnderecoForm enderecoForm) {

		return new EnderecoDTO(
			enderecoForm.rua(),
			enderecoForm.numero(),
			enderecoForm.cidade(),
			enderecoForm.estado(),
			enderecoForm.cep(),
			enderecoForm.complemento());
	}
}
