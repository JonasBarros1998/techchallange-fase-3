package com.postech.parquimetro.dominio.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "endereco_do_condutor")
public class EnderecoDoCondutor {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false, length = 100)
	String rua;

	@Column(nullable = false, length = 10)
	String numero;

	@Column(nullable = false, length = 40)
	String cidade;

	@Column(nullable = false, length = 2)
	String estado;

	@Column(nullable = false, length = 8)
	String cep;

	@Column(nullable = true, length = 40)
	String complemento;

	public EnderecoDoCondutor() {}

	public EnderecoDoCondutor(String rua, String numero, String cep, String cidade, String estado, String complemento) {
		this.rua = rua;
		this.numero = numero;
		this.cidade = cidade;
		this.estado = estado;
		this.complemento = complemento;
		this.cep = cep;
	}

	public String getRua() {
		return rua;
	}

	public String getNumero() {
		return numero;
	}

	public String getCidade() {
		return cidade;
	}

	public String getEstado() {
		return estado;
	}

	public String getCep() {
		return cep;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
}
