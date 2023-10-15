package com.postech.parquimetro.dominio.entities.projections;

import com.postech.parquimetro.dominio.entities.enums.TiposDePagamento;

import java.util.UUID;

public interface PesquisarPorUmMetodoDePagamentoDoCondutor {

	UUID getCondutorId();
	TiposDePagamento getTipoDePagamento();


}
