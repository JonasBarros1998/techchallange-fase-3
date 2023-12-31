package com.postech.parquimetro.dominio.entities;


import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "condutor")
public class Condutor {

	@Id
	@Column(length = 11, nullable = false, unique = true)
	private String cpf;

	@Column(nullable = false, unique = true)
	private UUID id = UUID.randomUUID();

	@Column(length = 70, nullable = false)
	private String email;

	@Column(length = 100, nullable = false)
	private String nome;

	@Column(length = 11, nullable = false)
	private String telefone;

	@OneToOne(cascade = {CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
	@JoinColumn(referencedColumnName = "id", updatable = false)
	private EnderecoDoCondutor enderecoDoCondutor;

	@Column(nullable = false)
	private Boolean status = Boolean.TRUE;

	@OneToMany(mappedBy = "condutor", cascade = CascadeType.REMOVE)
	private List<Automovel> automovel;

	/*
	@OneToMany(mappedBy = "condutor", cascade = CascadeType.REMOVE)
	private List<MetodoDePagamento> metodoDePagamentos;*/

	public Condutor() {
	}

	public Condutor(
		String nome,
		String cpf,
		String telefone,
		String email,
		EnderecoDoCondutor enderecoDoCondutor) {
		this.nome = nome;
		this.cpf = cpf;
		this.enderecoDoCondutor = enderecoDoCondutor;
		this.telefone = telefone;
		this.email = email;
	}

	public Condutor(
		String nome,
		String cpf,
		String telefone,
		String email) {
		this.nome = nome;
		this.cpf = cpf;
		this.telefone = telefone;
		this.email = email;
	}

	public UUID getId() {
		return id;
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

	public Boolean getStatus() {
		return status;
	}

	public List<Automovel> getAutomovel() {
		return automovel;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public void desativarCondutor() {
		this.status = Boolean.FALSE;
	}
}


