package com.postech.parquimetro.dominio;

import com.postech.parquimetro.dominio.entities.Automovel;
import com.postech.parquimetro.dominio.exceptions.VeiculosNaoEncontradoException;

import java.util.List;

public class NaoIniciarOTemporizadorSeOCondutorNaoTiverAutomveisRegistrado {
	public static void verificar(List<Automovel> automoveisDoCondutor) {
		if(automoveisDoCondutor.isEmpty()) {
			throw new VeiculosNaoEncontradoException("Nao foi possivel encontrar os veiculos desse condutor");
		}
	}
}
