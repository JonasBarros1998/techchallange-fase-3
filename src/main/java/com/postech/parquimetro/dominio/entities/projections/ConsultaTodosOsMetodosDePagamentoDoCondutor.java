package com.postech.parquimetro.dominio.entities.projections;

import com.postech.parquimetro.dominio.entities.enums.TiposDePagamento;

import java.time.LocalDateTime;
import java.util.UUID;

public interface ConsultaTodosOsMetodosDePagamentoDoCondutor {
	TiposDePagamento getTiposDePagamento();
	LocalDateTime getDataCadastro();
	UUID getId();
}
