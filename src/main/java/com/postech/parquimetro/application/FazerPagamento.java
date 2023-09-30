package com.postech.parquimetro.application;

import com.postech.parquimetro.dominio.entities.Condutor;
import com.postech.parquimetro.dominio.entities.pagamento.Credito;
import com.postech.parquimetro.dominio.entities.pagamento.MetodoDePagamento;

import java.time.LocalDateTime;

public class FazerPagamento {

	public void criar() {

		var credito = new Credito(
			"1234567",
			"Jonas",
			"02/2023",
			"123"
		);

		new MetodoDePagamento(
			new Condutor(),
			LocalDateTime.now(),
			"credito",
			credito
		);

	}
}
