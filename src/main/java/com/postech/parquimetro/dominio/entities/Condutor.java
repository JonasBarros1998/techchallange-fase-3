package com.postech.parquimetro.dominio.entities;


import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "condutor")
public class Condutor {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(length = 70, nullable = false)
	private String email;

	@Column(length = 100, nullable = false)
	private String nome;

	@Column(length = 11, columnDefinition = "char", nullable = false)
	private String cpf;

	@Column(length = 11, columnDefinition = "char", nullable = false)
	private String telefone;

	@OneToOne
	private EnderecoDoCondutor enderecoDoCondutor;

	@OneToMany(mappedBy = "condutor")
	List<Automovel> automovel;

	public Condutor() {
	}

	public Condutor(
		String nome,
		String cpf,
		EnderecoDoCondutor enderecoDoCondutor,
		List<Automovel> automovel,
		String telefone,
		String email) {
		this.nome = nome;
		this.cpf = cpf;
		this.enderecoDoCondutor = enderecoDoCondutor;
		this.automovel = automovel;
		this.telefone = telefone;
		this.email = email;
	}


	public String getNome() {
		return nome;
	}

	public String getCpf() {
		return cpf;
	}

	public EnderecoDoCondutor getEndereco() {
		return enderecoDoCondutor;
	}

	public String getTelefone() {
		return telefone;
	}

	public String getEmail() {
		return email;
	}

	public List<Automovel> getAutomovel() {
		return automovel;
	}

}


