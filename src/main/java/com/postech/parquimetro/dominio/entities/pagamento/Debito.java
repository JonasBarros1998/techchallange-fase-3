package com.postech.parquimetro.dominio.entities.pagamento;

import com.postech.parquimetro.infra.criptografia.Criptografia;
import jakarta.persistence.*;

@Entity
@Table(name = "debito")
public final class Debito extends Cartao implements IPagamento<Debito> {

	@Convert(converter = Criptografia.class)
	@Column(nullable = false, length = 50)
	private String nomeDaInstituicaoFinanceira;

	@Convert(converter = Criptografia.class)
	@OneToOne(orphanRemoval = true, optional = false)
	@JoinColumn(referencedColumnName = "id", name = "metodo_de_pagamento_id")
	private MetodoDePagamento metodoDePagamento;

	public Debito(
		String numeroDoCartao,
		String nomeDoTitularDoCartao,
		String dataDeVencimento,
		String codigoDeSeguranca,
		String nomeDaInstituicaoFinanceira) {
		super(numeroDoCartao, nomeDoTitularDoCartao, dataDeVencimento, codigoDeSeguranca);
		this.nomeDaInstituicaoFinanceira = nomeDaInstituicaoFinanceira;
	}

	public Debito() {
		super();
	}

	public String getnomeDaInstituicaoFinanceira() {
		return nomeDaInstituicaoFinanceira;
	}

	public MetodoDePagamento getMetodoDePagamento() {
		return metodoDePagamento;
	}

	@Override
	public Debito criarPagamento() {
		return this;
	}

	public void setMetodoDePagamento(MetodoDePagamento metodoDePagamento) {
		this.metodoDePagamento = metodoDePagamento;
	}

}
