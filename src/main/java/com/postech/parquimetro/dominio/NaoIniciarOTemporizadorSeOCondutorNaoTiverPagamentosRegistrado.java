package com.postech.parquimetro.dominio;

import com.postech.parquimetro.dominio.entities.pagamento.MetodoDePagamento;
import com.postech.parquimetro.dominio.entities.projections.ConsultaTodosOsTiposDePagamentoDoCondutor;
import com.postech.parquimetro.dominio.exceptions.PagamentosNaoEncontradoException;

import java.util.List;
import java.util.Optional;

public class NaoIniciarOTemporizadorSeOCondutorNaoTiverPagamentosRegistrado {

	public static MetodoDePagamento verificar(Optional<MetodoDePagamento> pagamento) {

		return pagamento.orElseThrow(() ->
			new PagamentosNaoEncontradoException("Nao foram encontrados pagamentos para esse condutor"));
	}
}
