package com.postech.parquimetro.view.controller;

import com.postech.parquimetro.aplicacao.DTO.EnderecoDTO;
import com.postech.parquimetro.aplicacao.GerenciarEnderecosDosCondutores;
import com.postech.parquimetro.infra.repository.EnderecoRepository;
import com.postech.parquimetro.view.form.EnderecoForm;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/api/enderecos")
public class EnderecoDoCondutorController {

	GerenciarEnderecosDosCondutores enderecosDosCondutores;

	@Autowired
	EnderecoDoCondutorController(GerenciarEnderecosDosCondutores enderecosDosCondutores) {
		this.enderecosDosCondutores = enderecosDosCondutores;
	}

	@PutMapping("/{id}")
	public ResponseEntity<EnderecoDTO> editar(@Valid @RequestBody EnderecoForm enderecoForm, @PathVariable UUID id) {

		var endereco = this.enderecosDosCondutores.editar(enderecoForm, id);
		return ResponseEntity.status(HttpStatus.OK).body(endereco);
	}
}
