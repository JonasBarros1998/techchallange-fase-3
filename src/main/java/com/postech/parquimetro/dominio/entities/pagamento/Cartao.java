package com.postech.parquimetro.dominio.entities.pagamento;

import com.postech.parquimetro.dominio.entities.enums.TipoDePagamento;
import jakarta.persistence.*;
import jakarta.websocket.ClientEndpoint;
import org.hibernate.annotations.Proxy;

import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Proxy(lazy = false)
sealed abstract class Cartao permits Credito, Debito {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID uuid;

	@Column(length = 16, nullable = false, columnDefinition = "char")
	private String numeroDoCartao;

	@Column(length = 20, nullable = false)
	private String nomeDoTitularDoCartao;

	@Column(length = 6, nullable = false, columnDefinition = "char")
	private String dataDeVencimento;

	@Column(length = 3, columnDefinition = "char", nullable = false)
	private String codigoDeSeguranca;

	public Cartao() {}

	public Cartao(String numeroDoCartao, String nomeDoTitularDoCartao, String dataDeVencimento, String codigoDeSeguranca) {
		this.numeroDoCartao = numeroDoCartao;
		this.nomeDoTitularDoCartao = nomeDoTitularDoCartao;
		this.dataDeVencimento = dataDeVencimento;
		this.codigoDeSeguranca = codigoDeSeguranca;
	}

	public String getNumeroDoCartao() {
		return numeroDoCartao;
	}

	public String getNomeDoTitularDoCartao() {
		return nomeDoTitularDoCartao;
	}

	public String getDataDeVencimento() {
		return dataDeVencimento;
	}

	public String getCodigoDeSeguranca() {
		return codigoDeSeguranca;
	}
}


