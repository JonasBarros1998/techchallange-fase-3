package com.postech.parquimetro.application.DTO;

import com.postech.parquimetro.view.form.CondutorForm;
import com.postech.parquimetro.view.form.EnderecoForm;

public record CondutorDTO(
	String email,
	String nome,
	String cpf,
	String telefone,
	EnderecoDTO endereco
) {

	public static CondutorDTO converterCondutorFormParaCondutorDTO(CondutorForm condutorForm) {
		var enderecoDTO = new EnderecoDTO(
			condutorForm.endereco().rua(),
			condutorForm.endereco().numero(),
			condutorForm.endereco().cidade(),
			condutorForm.endereco().estado(),
			condutorForm.endereco().cep(),
			condutorForm.endereco().complemento());

		return new CondutorDTO(
			condutorForm.email(),
			condutorForm.nome(),
			condutorForm.cpf(),
			condutorForm.telefone(),
			enderecoDTO
		);
	}

}
