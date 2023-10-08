package com.postech.parquimetro.aplicacao.DTO;

import com.postech.parquimetro.dominio.entities.Condutor;
import com.postech.parquimetro.view.DTO.consultaCondutores.ConsultaAutomoveisDoCondutorDTO;
import com.postech.parquimetro.view.DTO.consultaCondutores.ConsultaCondutoresDTO;
import com.postech.parquimetro.view.form.CondutorForm;
import com.postech.parquimetro.view.form.EditarCondutorForm;

import java.util.List;


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

	public static List<ConsultaCondutoresDTO> converterCondutorParaConsultaCondutoresDTO(List<Condutor> condutores) {
		return condutores.stream().map((condutor) -> {

			var endereco = new EnderecoDTO(
				condutor.getEndereco().getRua(),
				condutor.getEndereco().getNumero(),
				condutor.getEndereco().getCidade(),
				condutor.getEndereco().getEstado(),
				condutor.getEndereco().getNumero(),
				condutor.getEndereco().getComplemento()
			);

			List<ConsultaAutomoveisDoCondutorDTO> automoveis = condutor.getAutomovel().stream().map((automovel) ->
				new ConsultaAutomoveisDoCondutorDTO(
					automovel.getPlaca(),
					automovel.getModelo(),
					automovel.getTipoDoAutomovel(),
					automovel.getId())
			).toList();

			return new ConsultaCondutoresDTO(
				condutor.getId(),
				condutor.getEmail(),
				condutor.getNome(),
				condutor.getTelefone(),
				endereco,
				automoveis
			);

		}).toList();
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
