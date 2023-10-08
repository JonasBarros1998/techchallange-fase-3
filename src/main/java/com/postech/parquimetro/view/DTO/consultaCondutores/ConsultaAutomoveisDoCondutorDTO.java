package com.postech.parquimetro.view.DTO.consultaCondutores;

import java.util.UUID;

public record ConsultaAutomoveisDoCondutorDTO(
	String placa,
	String modelo,
	String tipoDoAutomovel,

	UUID id
) {
}
