package com.postech.parquimetro.aplicacao.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ComprovanteDePagamentoDTO(
	String valorAPagar,
	UUID agendamentoID,
	LocalDateTime horarioInicial,
	LocalDateTime horarioFinal

) {
}
