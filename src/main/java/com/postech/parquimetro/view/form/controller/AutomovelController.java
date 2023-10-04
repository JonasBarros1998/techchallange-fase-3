package com.postech.parquimetro.view.form.controller;

import com.postech.parquimetro.aplicacao.DTO.AutomovelDTO;
import com.postech.parquimetro.aplicacao.GerenciarAutomoveis;
import com.postech.parquimetro.view.form.AutomovelForm;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/automoveis")
public class AutomovelController {

	GerenciarAutomoveis gerenciarAutomoveis;

	@Autowired
	AutomovelController(GerenciarAutomoveis gerenciarAutomoveis) {
		this.gerenciarAutomoveis = gerenciarAutomoveis;
	}

	@PostMapping
	public ResponseEntity<AutomovelDTO> salvar(@RequestBody @Valid AutomovelForm automovelForm) {
		var automoveis = this.gerenciarAutomoveis.salvar(automovelForm);
		return ResponseEntity.status(HttpStatus.CREATED).body(automoveis);

	}

}
