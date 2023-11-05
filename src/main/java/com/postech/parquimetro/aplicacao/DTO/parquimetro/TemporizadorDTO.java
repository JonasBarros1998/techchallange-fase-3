package com.postech.parquimetro.aplicacao.DTO.parquimetro;

import java.time.LocalDateTime;

public record TemporizadorDTO(
	LocalDateTime tempoInicial,
	LocalDateTime tempoFinal
) {
}
