package com.postech.parquimetro.view.controller;

import com.postech.parquimetro.aplicacao.DTO.CondutorDTO;
import com.postech.parquimetro.aplicacao.GerenciarCondutores;
import com.postech.parquimetro.view.form.CondutorForm;
import com.postech.parquimetro.view.form.EditarCondutorForm;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
	public ResponseEntity<CondutorForm> registrarNovoCondutor(@RequestBody @Valid CondutorForm condutorForm) {
		this.gerenciarCondutores.salvar(condutorForm);
		return ResponseEntity.status(200).body(condutorForm);
	}

	@PutMapping("/{id}")
	public ResponseEntity<EditarCondutorForm> editar(@RequestBody @Valid EditarCondutorForm condutorForm, @PathVariable UUID id) {
		EditarCondutorForm condutor = this.gerenciarCondutores.editar(condutorForm, id);
		return ResponseEntity.status(200).body(condutor);
	}

}
