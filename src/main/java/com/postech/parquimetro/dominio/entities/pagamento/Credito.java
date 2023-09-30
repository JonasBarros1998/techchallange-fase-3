package com.postech.parquimetro.dominio.entities.pagamento;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "credito")
public class Credito extends Cartao implements IPagamento {

	public Credito(String numeroDoCartao, String nomeDoTitularDoCartao, String dataDeVencimento, String codigoDeSeguranca) {
		super(numeroDoCartao, nomeDoTitularDoCartao, dataDeVencimento, codigoDeSeguranca);
	}

	public Credito() {
		super();
	}


	@Override
	public IPagamento criarPagamento() {
		return this;
	}
}
