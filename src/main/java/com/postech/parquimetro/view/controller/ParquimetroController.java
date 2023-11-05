package com.postech.parquimetro.view.controller;

import com.postech.parquimetro.aplicacao.DTO.ComprovanteDePagamentoDTO;
import com.postech.parquimetro.aplicacao.parquimetro.EstacionamentoPorTempoFixo;
import com.postech.parquimetro.aplicacao.parquimetro.EstacionamentoPorTempoVariavel;
import com.postech.parquimetro.aplicacao.parquimetro.FinalizarAgendamentoPorTempoVariavel;
import com.postech.parquimetro.view.form.parquimetro.EstacionamentoPorTempoFixoForm;
import com.postech.parquimetro.view.form.parquimetro.EstacionamentoPorTempoVariavelForm;
import com.postech.parquimetro.view.form.parquimetro.FinalizarAgendamentoForm;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/parquimetro")
public class ParquimetroController {

	EstacionamentoPorTempoFixo estacionamentoPorTempoFixo;
	EstacionamentoPorTempoVariavel estacionamentoPorTempoVariavel;
	FinalizarAgendamentoPorTempoVariavel finalizarAgendamentoPorTempoVariavel;

	@Autowired
	ParquimetroController(
		EstacionamentoPorTempoFixo estacionamentoPorTempoFixo,
		EstacionamentoPorTempoVariavel estacionamentoPorTempoVariavel,
		FinalizarAgendamentoPorTempoVariavel finalizarAgendamentoPorTempoVariavel) {
		this.estacionamentoPorTempoFixo = estacionamentoPorTempoFixo;
		this.estacionamentoPorTempoVariavel = estacionamentoPorTempoVariavel;
		this.finalizarAgendamentoPorTempoVariavel = finalizarAgendamentoPorTempoVariavel;
	}

	@PostMapping("/tempoFixo")
	public ResponseEntity<EstacionamentoPorTempoFixoForm> tempoFixo(@RequestBody @Valid EstacionamentoPorTempoFixoForm tempoFixo) {
		this.estacionamentoPorTempoFixo.iniciarEstacionamentoPorTempoFixo(tempoFixo);
		return ResponseEntity.status(HttpStatus.CREATED).body(tempoFixo);
	}

	@PostMapping("/tempoVariavel/iniciar")
	public ResponseEntity<EstacionamentoPorTempoVariavelForm> tempoVariavel(
		@RequestBody @Valid EstacionamentoPorTempoVariavelForm tempoVariavel
	) {
		this.estacionamentoPorTempoVariavel.iniciarEstacionamentoPorTempoVariavel(tempoVariavel);
		return ResponseEntity.status(HttpStatus.CREATED).body(tempoVariavel);
	}

	@PostMapping("/tempoVariavel/finalizar")
	public ResponseEntity<ComprovanteDePagamentoDTO> tempoVariavel(
		@RequestBody @Valid FinalizarAgendamentoForm finalizarAgendamentoForm
	) {
		ComprovanteDePagamentoDTO registro = this.finalizarAgendamentoPorTempoVariavel.finalizar(finalizarAgendamentoForm);
		return ResponseEntity.status(HttpStatus.CREATED).body(registro);
	}
}
