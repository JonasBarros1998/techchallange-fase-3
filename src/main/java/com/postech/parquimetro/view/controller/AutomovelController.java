package com.postech.parquimetro.view.controller;

import com.postech.parquimetro.aplicacao.DTO.AutomovelDTO;
import com.postech.parquimetro.aplicacao.GerenciarAutomoveis;
import com.postech.parquimetro.view.form.AutomovelForm;
import com.postech.parquimetro.view.form.EditarAutomovelForm;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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

	@PutMapping("/{id}")
	public ResponseEntity<EditarAutomovelForm> editar(@RequestBody @Valid EditarAutomovelForm automovelForm, @PathVariable UUID id) {
		var automovel = this.gerenciarAutomoveis.editar(automovelForm, id);
		return ResponseEntity.status(HttpStatus.OK).body(automovel);
	}

}
