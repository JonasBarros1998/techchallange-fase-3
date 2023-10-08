package com.postech.parquimetro.aplicacao.DTO;

import com.postech.parquimetro.view.form.CondutorForm;
import com.postech.parquimetro.view.form.EditarCondutorForm;


public class CondutorDTO {

	private String email;
	private String nome;
	private String cpf;
	private String telefone;
	private EnderecoDTO endereco;

	CondutorDTO(String email, String nome, String cpf, String telefone, EnderecoDTO endereco) {
		this.email = email;
		this.nome = nome;
		this.cpf = cpf;
		this.telefone = telefone;
		this.endereco = endereco;
	}

	CondutorDTO(String email, String nome, String telefone) {
		this.email = email;
		this.nome = nome;
		this.telefone = telefone;
	}

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

	public static CondutorDTO converterCondutorFormParaCondutorDTO(EditarCondutorForm condutorForm) {
		return new CondutorDTO(
			condutorForm.email(),
			condutorForm.nome(),
			condutorForm.telefone());
	}

	public String getEmail() {
		return email;
	}

	public String getNome() {
		return nome;
	}

	public String getCpf() {
		return cpf;
	}

	public String getTelefone() {
		return telefone;
	}

	public EnderecoDTO getEndereco() {
		return endereco;
	}
}
