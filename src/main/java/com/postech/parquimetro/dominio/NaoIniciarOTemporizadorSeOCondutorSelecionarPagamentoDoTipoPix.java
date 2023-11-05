package com.postech.parquimetro.dominio;

import com.postech.parquimetro.dominio.enums.TiposDePagamento;
import com.postech.parquimetro.dominio.entities.pagamento.MetodoDePagamento;
import com.postech.parquimetro.dominio.exceptions.MetodoDePagamentoInvalido;

import java.util.Optional;

public class NaoIniciarOTemporizadorSeOCondutorSelecionarPagamentoDoTipoPix {

	public static MetodoDePagamento  verificar(Optional<MetodoDePagamento> pagamento) {

		MetodoDePagamento pagamentoSelecionado = pagamento.orElse(null);

		if(pagamentoSelecionado.getTiposDePagamento().name().equals(TiposDePagamento.PIX.name())) {
			throw new MetodoDePagamentoInvalido(
				"Nao e permitido selecionar metodo de pagamento do tipo PIX para estacionamentos por tempo variavel"
			);
		}

		return pagamentoSelecionado;

	}
}
