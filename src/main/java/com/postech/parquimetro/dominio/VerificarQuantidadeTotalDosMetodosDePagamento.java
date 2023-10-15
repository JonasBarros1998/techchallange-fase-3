package com.postech.parquimetro.dominio;

import com.postech.parquimetro.dominio.entities.enums.TiposDePagamento;
import com.postech.parquimetro.dominio.entities.projections.ConsultaTodosOsMetodosDePagamentoDoCondutor;

import java.util.List;

public class VerificarQuantidadeTotalDosMetodosDePagamento {

	private Boolean podeRemover;

	/**
	 * Regra de negocio para verificar a quantidade de metodos de pagamentos do tipo Debito e Credito
	 * cadastrados no perfil do condutor.
	 *
	 * <p>Caso queira remover 1 metodo de pagamento, ele devera ter ao menos dois metodos de pagamento cadastrado
	 * Nao sera possivel a remoçao de todos os metodos de pagamento.</p>
	 *
	 * <p>Devera haver ao menos 1 metodo de pagamento do tipo debito ou credito cadastrado no banco.</p>
	 * @param metodoDePagamento
	 * @return Boolean
	 * */
	public Boolean podeRemoverMetodoDePagamento(List<ConsultaTodosOsMetodosDePagamentoDoCondutor> metodoDePagamento, TiposDePagamento tipoDePagamento) {

		if (TiposDePagamento.PIX.name().equals(tipoDePagamento.name())) {
			return true;
		}

		List<ConsultaTodosOsMetodosDePagamentoDoCondutor> metodosDePagamentoCadastrados =
			metodoDePagamento.stream().filter(metodo -> {
				return metodo.getTiposDePagamento().equals(TiposDePagamento.CREDITO) ||
					metodo.getTiposDePagamento().equals(TiposDePagamento.DEBITO);
			}).toList();

		if(metodosDePagamentoCadastrados.size() > 1) {
			return true;
		}

		return false;


	}
}
