package com.postech.parquimetro.dominio.entities.pagamento;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "debito")
public final class Debito extends Cartao implements IPagamento {

	@Column(nullable = false, length = 50)
	String nomeDaInstituicaoFinanceira;

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

	public String getNomeDaInstituicaoFinanceira() {
		return nomeDaInstituicaoFinanceira;
	}


	@Override
	public IPagamento criarPagamento() {
		return this;
	}
}
