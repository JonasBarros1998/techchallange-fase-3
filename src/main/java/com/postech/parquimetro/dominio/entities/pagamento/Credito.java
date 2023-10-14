package com.postech.parquimetro.dominio.entities.pagamento;
import jakarta.persistence.*;

@Entity
@Table(name = "credito")
public final class Credito extends Cartao implements IPagamento<Credito> {

	@OneToOne(orphanRemoval = true, optional = false)
	@JoinColumn(referencedColumnName = "id", name = "metodo_de_pagamento_id")
	MetodoDePagamento metodoDePagamento;

	@Column(length = 30)
	String bandeira;

	public Credito(
		String numeroDoCartao,
		String nomeDoTitularDoCartao,
		String dataDeVencimento,
		String codigoDeSeguranca,
		String bandeira) {
		super(numeroDoCartao, nomeDoTitularDoCartao, dataDeVencimento, codigoDeSeguranca);
		this.bandeira = bandeira;
	}

	public Credito() {
		super();
	}

	@Override
	public Credito criarPagamento() {
		return this;
	}

	public void setMetodoDePagamento(MetodoDePagamento metodoDePagamento) {
		this.metodoDePagamento = metodoDePagamento;
	}

	public String getBandeira() {
		return bandeira;
	}
}
