package com.postech.parquimetro.dominio;

import com.postech.parquimetro.dominio.entities.pagamento.IPagamento;

public class IdentificarTipoDePagamento {

	public IPagamento identificar(IPagamento IPagamento) {
		return IPagamento.criarPagamento();
	}

}
