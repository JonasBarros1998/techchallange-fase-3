package com.postech.parquimetro.dominio.entities.pagamento;

import com.postech.parquimetro.infra.criptografia.Criptografia;
import jakarta.persistence.*;
import org.hibernate.annotations.Proxy;

import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Proxy(lazy = false)
sealed abstract class Cartao permits Credito, Debito {


	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Convert(converter = Criptografia.class)
	@Column(nullable = false, unique = true)
	private String numeroDoCartao;

	@Convert(converter = Criptografia.class)
	@Column(nullable = false)
	private String nomeDoTitular;

	@Convert(converter = Criptografia.class)
	@Column(nullable = false)
	private String dataDeVencimento;

	@Convert(converter = Criptografia.class)
	@Column(nullable = false, unique = true)
	private String codigoDeSeguranca;

	public Cartao() {}

	public Cartao(String numeroDoCartao, String nomeDoTitular, String dataDeVencimento, String codigoDeSeguranca) {
		this.numeroDoCartao = numeroDoCartao;
		this.nomeDoTitular = nomeDoTitular;
		this.dataDeVencimento = dataDeVencimento;
		this.codigoDeSeguranca = codigoDeSeguranca;
	}


	public Cartao(String nomeDoTitular, String dataDeVencimento) {
		this.nomeDoTitular = nomeDoTitular;
		this.dataDeVencimento = dataDeVencimento;
	}

	public String getNumeroDoCartao() {
		return numeroDoCartao;
	}

	public String getNomeDoTitular() {
		return nomeDoTitular;
	}

	public String getDataDeVencimento() {
		return dataDeVencimento;
	}

	public String getCodigoDeSeguranca() {
		return codigoDeSeguranca;
	}

	public UUID getId() {
		return id;
	}

	public void setNomeDoTitular(String nomeDoTitular) {
		this.nomeDoTitular = nomeDoTitular;
	}

	public void setDataDeVencimento(String dataDeVencimento) {
		this.dataDeVencimento = dataDeVencimento;
	}
}


