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

	@Column(nullable = false, length = 2, columnDefinition = "char")
	String estado;

	@Column(nullable = false, length = 40)
	String complemento;

	public EnderecoDoCondutor() {}

	public EnderecoDoCondutor(String rua, String numero, String cidade, String estado, String complemento) {
		this.rua = rua;
		this.numero = numero;
		this.cidade = cidade;
		this.estado = estado;
		this.complemento = complemento;
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

	public String getComplemento() {
		return complemento;
	}
}
