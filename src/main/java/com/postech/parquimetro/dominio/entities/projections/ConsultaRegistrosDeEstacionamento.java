package com.postech.parquimetro.dominio.entities.projections;

import java.time.LocalDateTime;
import java.util.UUID;

public interface ConsultaRegistrosDeEstacionamento {

	UUID getId();

	LocalDateTime getTempoInicial();

	LocalDateTime getTempoFinal();

	String getNomeDoEventoParaAlertas();

	String getNomeDoEventoParaAgendamento();

}
