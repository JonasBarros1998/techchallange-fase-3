package com.postech.parquimetro.dominio.entities.projections;

import com.postech.parquimetro.dominio.enums.TiposDePagamento;

import java.time.LocalDateTime;
import java.util.UUID;

public interface ConsultaTodosOsTiposDePagamentoDoCondutor {
	TiposDePagamento getTiposDePagamento();
	LocalDateTime getDataCadastro();
	UUID getId();
}
