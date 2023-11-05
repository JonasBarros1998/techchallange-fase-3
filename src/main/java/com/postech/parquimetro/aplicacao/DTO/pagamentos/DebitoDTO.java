package com.postech.parquimetro.aplicacao.DTO.pagamentos;

import com.postech.parquimetro.view.form.pagamentos.DebitoForm;
import com.postech.parquimetro.view.form.pagamentos.EditarDebitoForm;

import java.util.UUID;

public record DebitoDTO(
	UUID condutor,
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

	public static DebitoDTO converterDeDebitoFormParaDebitoDTO(EditarDebitoForm debitoForm) {
		return new DebitoDTO(
			null,
			null,
			debitoForm.nomeDoTitular(),
			null,
			debitoForm.dataDeValidade(),
			null,
			null);
	}
}
