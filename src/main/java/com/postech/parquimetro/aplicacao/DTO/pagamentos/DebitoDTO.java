package com.postech.parquimetro.aplicacao.DTO.pagamentos;

import com.postech.parquimetro.view.form.pagamentos.DebitoForm;

public record DebitoDTO(
	String condutor,
	String numeroDoCartao,
	String nomeDoTitular,
	String codigoDeSeguranca,
	String dataDeValidade,
	String nomeDaInstituicaoFinanceira,
	String bandeira
) {

	public static DebitoDTO converterDeDebitoFormParaDebitoDTO(DebitoForm debitoForm) {
		return new DebitoDTO(
			debitoForm.condutor(),
			debitoForm.numeroDoCartao(),
			debitoForm.nomeDoTitular(),
			debitoForm.codigoDeSeguranca(),
			debitoForm.dataDeValidade(),
			debitoForm.nomeDaInstituicaoFinanceira(),
			debitoForm.bandeira());
	}
}
