package com.postech.parquimetro.aplicacao.DTO.pagamentos;

import com.postech.parquimetro.dominio.entities.enums.TiposDePagamento;
import com.postech.parquimetro.view.form.pagamentos.CreditoForm;

public record CreditoDTO(
	String condutor,
	String numeroDoCartao,
	String nomeDoTitular,
	String bandeira,
	String codigoDeSeguranca,
	String dataDeValidade,
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
}
