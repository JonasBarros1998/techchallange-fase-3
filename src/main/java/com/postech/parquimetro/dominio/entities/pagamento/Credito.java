package com.postech.parquimetro.dominio.entities.pagamento;
import com.postech.parquimetro.infra.criptografia.Criptografia;
import jakarta.persistence.*;

@Entity
@Table(name = "credito")
public final class Credito extends Cartao implements IPagamento<Credito> {

	@Convert(converter = Criptografia.class)
	@OneToOne(orphanRemoval = true, optional = false)
	@JoinColumn(referencedColumnName = "id", name = "metodo_de_pagamento_id")
	private MetodoDePagamento metodoDePagamento;

	@Column(length = 30)
	private String bandeira;

	public Credito(String bandeira, String nomeDoTitular) {
		super("jonas f barros", "02/2023");
	}

	public Credito(
		String numeroDoCartao,
		String nomeDoTitular,
		String dataDeVencimento,
		String codigoDeSeguranca,
		String bandeira) {
		super(numeroDoCartao, nomeDoTitular, dataDeVencimento, codigoDeSeguranca);
		this.bandeira = bandeira;
	}


	public Credito() {
		super();
	}

	@Override
	public Credito criarPagamento() {
		return this;
	}

	public MetodoDePagamento getMetodoDePagamento() {
		return metodoDePagamento;
	}

	public void setMetodoDePagamento(MetodoDePagamento metodoDePagamento) {
		this.metodoDePagamento = metodoDePagamento;
	}

	public String getBandeira() {
		return bandeira;
	}
}
