package com.postech.parquimetro.dominio.entities.projections;

import java.time.LocalDateTime;

public interface ConsultaTodosOsMetodosDePagamentoDoCondutor {

	LocalDateTime getDataDeCadastro();
	String getNomeDoTitular();
	String getDataDeValidade();
	String getBandeira();
}
