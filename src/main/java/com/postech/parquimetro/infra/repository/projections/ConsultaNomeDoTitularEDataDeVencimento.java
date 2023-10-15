package com.postech.parquimetro.infra.repository.projections;

import com.postech.parquimetro.dominio.entities.pagamento.Credito;

import java.util.UUID;


public interface ConsultaNomeDoTitularEDataDeVencimento {
	UUID getId();
	String getNomeDoTitular();
	String getDataDeVencimento();
}
