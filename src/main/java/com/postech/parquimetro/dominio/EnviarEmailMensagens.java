package com.postech.parquimetro.dominio;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public record EnviarEmailMensagens() {

	public static String paraFinalizacao(BigDecimal valorTotal) {

		var formatarValor = NumberFormat.getCurrencyInstance(
			new Locale("pt", "BR"))
				.format(valorTotal);

		return String
			.format(
				"Estacionamento finalizado e pagamento no valor de %s confirmado com sucesso",
				formatarValor);
	}

	public static String paraAlerta() {
		return "Falta pouco para se encerrar o tempo do seu estacionamento";
	}

	public static String paraRenovacao() {
		return "Acrescimo de 60 minutos no tempo do seu estacionamento";
	}





}
