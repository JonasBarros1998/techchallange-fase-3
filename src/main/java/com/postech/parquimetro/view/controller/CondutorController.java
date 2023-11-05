package com.postech.parquimetro.view.controller;

import com.postech.parquimetro.aplicacao.GerenciarCondutores;
import com.postech.parquimetro.dominio.entities.parquimetro.RegistroDeEstacionamento;
import com.postech.parquimetro.dominio.entities.projections.ConsultaRegistrosDeEstacionamento;
import com.postech.parquimetro.view.DTO.consultaCondutores.ConsultaCondutoresDTO;
import com.postech.parquimetro.view.form.CondutorForm;
import com.postech.parquimetro.view.form.EditarCondutorForm;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/condutores")
public class CondutorController {

	GerenciarCondutores gerenciarCondutores;

	@Autowired
	CondutorController(GerenciarCondutores gerenciarCondutores) {
		this.gerenciarCondutores = gerenciarCondutores;
	}

	@PostMapping
	public ResponseEntity<CondutorForm> salvar(@RequestBody @Valid CondutorForm condutorForm) {
		this.gerenciarCondutores.salvar(condutorForm);
		return ResponseEntity.status(201).body(condutorForm);
	}

	@GetMapping
	public ResponseEntity<List<ConsultaCondutoresDTO>> pesquisarTodosCondutores() {
		List<ConsultaCondutoresDTO> condutores = this.gerenciarCondutores.consultarTodosCondutores();
		return ResponseEntity.status(HttpStatus.OK).body(condutores);
	}

	@PutMapping("/{id}")
	public ResponseEntity<EditarCondutorForm> editar(@RequestBody @Valid EditarCondutorForm condutorForm, @PathVariable UUID id) {
		EditarCondutorForm condutor = this.gerenciarCondutores.editar(condutorForm, id);
		return ResponseEntity.status(200).body(condutor);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remover(@PathVariable UUID id) {
		this.gerenciarCondutores.remover(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@GetMapping("/registroDeEstacionamento/{condutorID}")
	public ResponseEntity<List<ConsultaRegistrosDeEstacionamento>> pesquisarTodosOsRegistrosDePagamento(@PathVariable UUID condutorID) {
		List<ConsultaRegistrosDeEstacionamento> registros = this.gerenciarCondutores.pesquisarTodosOsRegistrosDeEstacionamento(condutorID);
		return ResponseEntity.status(HttpStatus.OK).body(registros);
	}

}
