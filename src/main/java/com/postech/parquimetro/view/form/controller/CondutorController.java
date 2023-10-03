package com.postech.parquimetro.view.form.controller;

import com.postech.parquimetro.aplicacao.GerenciarCondutores;
import com.postech.parquimetro.view.form.CondutorForm;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/condutores")
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

}
