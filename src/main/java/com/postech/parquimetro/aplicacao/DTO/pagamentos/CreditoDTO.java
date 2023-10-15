package com.postech.parquimetro.aplicacao.DTO.pagamentos;

import com.postech.parquimetro.dominio.entities.enums.TiposDePagamento;
import com.postech.parquimetro.view.form.pagamentos.CreditoForm;
import com.postech.parquimetro.view.form.pagamentos.EditarCreditoForm;

public record CreditoDTO(
	String condutor,
	String numeroDoCartao,
	String nomeDoTitular,
	String bandeira,
	String codigoDeSeguranca,
	String dataDeVencimento,
	TiposDePagamento metodoDePagamento
) {

	public static CreditoDTO converterDeCreditoFormParaCreditoDTO(CreditoForm creditoForm) {
		return new CreditoDTO(
			creditoForm.condutor(),
			creditoForm.numeroDoCartao(),
			creditoForm.nomeDoTitular(),
			creditoForm.bandeira(),
			creditoForm.codigoDeSeguranca(),
			creditoForm.dataDeValidade(),
			creditoForm.metodoDePagamento());
	}

	public static CreditoDTO converterDeEditarCreditoFormParaCreditoDTO(EditarCreditoForm creditoForm) {
		return new CreditoDTO(
			null,
			null,
			creditoForm.nomeDoTitular(),
			null,
			null,
			creditoForm.dataDeValidade(),
			null);
	}
}
