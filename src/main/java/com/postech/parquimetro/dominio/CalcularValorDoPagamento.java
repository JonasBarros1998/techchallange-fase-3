package com.postech.parquimetro.dominio;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;

public class CalcularValorDoPagamento {

	private BigDecimal valorDaHoraDoEstacionamento = new BigDecimal("6.08");

	private BigDecimal totalDeMinutosDentroDeUmaHora = BigDecimal.valueOf(60);

	public BigDecimal calcular(LocalDateTime tempoInicial, LocalDateTime tempoFinal) {

		Duration duracao = Duration.between(tempoInicial, tempoFinal);

		Long minutosEstacionado = duracao.toMinutes();

		BigDecimal calcular = BigDecimal.valueOf(minutosEstacionado)
			.multiply(valorDaHoraDoEstacionamento)
			.divide(totalDeMinutosDentroDeUmaHora, RoundingMode.HALF_UP);

		return calcular;

	}
}

/*

Eu sei que se o cliente ficar estacionado por exatos 60 minutos ele pagara 6.08.
Mas se ele ficar estacionado por 16 minutos? Quanto ele pagara

Minutos     Dinheiro
60           6.08
16   propor  x

60x = 6.08 * 16
60x = 97.28
x = 97.28 / 60 = R$ 1,62
*/