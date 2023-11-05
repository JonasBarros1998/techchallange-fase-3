package com.postech.parquimetro.dominio.entities.parquimetro;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "endereco_do_estacionamento")
public class EnderecoDoEstacionamento {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false, length = 100)
	private String rua;

	@Column(nullable = false, length = 40)
	private String cidade;

	@Column(nullable = false, length = 2)
	private String Estado;

	@Column(nullable = false, length = 8)
	private String cep;

	@Column(length = 100)
	private String descricao;

	public EnderecoDoEstacionamento() {}

	public EnderecoDoEstacionamento(String rua, String cidade, String estado, String cep, String descricao) {
		this.rua = rua;
		this.cidade = cidade;
		Estado = estado;
		this.cep = cep;
		this.descricao = descricao;
	}


	public UUID getId() {
		return id;
	}

	public String getRua() {
		return rua;
	}

	public String getCidade() {
		return cidade;
	}

	public String getEstado() {
		return Estado;
	}

	public String getCep() {
		return cep;
	}

	public String getDescricao() {
		return descricao;
	}
}
