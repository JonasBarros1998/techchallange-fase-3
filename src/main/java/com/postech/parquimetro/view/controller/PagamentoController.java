package com.postech.parquimetro.view.controller;

import com.postech.parquimetro.aplicacao.DTO.pagamentos.CreditoDTO;
import com.postech.parquimetro.aplicacao.DTO.pagamentos.DebitoDTO;
import com.postech.parquimetro.aplicacao.DTO.pagamentos.ListarTodosOsMetodosDePagamentoDTO;
import com.postech.parquimetro.aplicacao.DTO.pagamentos.PixDTO;
import com.postech.parquimetro.aplicacao.pagamento.GerenciarPagamento;
import com.postech.parquimetro.view.form.pagamentos.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.Repository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/pagamentos")
public class PagamentoController {

	GerenciarPagamento gerenciarPagamento;

	@Autowired
	public PagamentoController(GerenciarPagamento gerenciarPagamento) {
		this.gerenciarPagamento = gerenciarPagamento;
	}

	@PostMapping("/pix")
	public ResponseEntity<PixDTO> cadastrarPIXComoMetodoDePagamento(@RequestBody @Valid PixForm pixForm) {
		PixDTO pix = this.gerenciarPagamento.cadastrarNovoMetodoDePagamento(pixForm);
		return ResponseEntity.status(HttpStatus.CREATED).body(pix);
	}

	@PostMapping("/debito")
	public ResponseEntity<DebitoDTO> cadastrarCartaoDeDebitoComoMetodoDePagamento(@RequestBody @Valid DebitoForm debitoForm) {
		DebitoDTO debitoDTO = this.gerenciarPagamento.cadastrarNovoMetodoDePagamento(debitoForm);
		return ResponseEntity.status(HttpStatus.CREATED).body(debitoDTO);
	}

	@PutMapping("/debito/{id}")
	public ResponseEntity<EditarDebitoForm> editarCartaoDeDebito(@PathVariable UUID id, @RequestBody @Valid EditarDebitoForm debitoForm) {
		EditarDebitoForm debito = this.gerenciarPagamento.editarTipoDePagamento(id, debitoForm);
		return ResponseEntity.status(HttpStatus.OK).body(debito);
	}

	@PostMapping("/credito")
	public ResponseEntity<CreditoDTO> cadastrarCartaoDeCreditoComoMetodoDePagamento(@RequestBody @Valid CreditoForm creditoForm) {
		CreditoDTO creditoDTO = this.gerenciarPagamento.cadastrarNovoMetodoDePagamento(creditoForm);
		return ResponseEntity.status(HttpStatus.CREATED).body(creditoDTO);
	}

	@PutMapping("/credito/{id}")
	public ResponseEntity<EditarCreditoForm> editarCartaoDeCredito(@PathVariable UUID id, @RequestBody @Valid EditarCreditoForm creditoForm) {
		EditarCreditoForm credito = this.gerenciarPagamento.editarTipoDePagamento(id, creditoForm);
		return ResponseEntity.status(HttpStatus.OK).body(credito);
	}

	@GetMapping("/{condutorID}")
	public ResponseEntity<ListarTodosOsMetodosDePagamentoDTO> listarTodosOsMetodosDePagamento(@PathVariable UUID condutorID) {
		ListarTodosOsMetodosDePagamentoDTO listarTodosOsPagamentos  = this.gerenciarPagamento.pesquisarTodosOsMetodosDePagamento(condutorID);
		return ResponseEntity.status(HttpStatus.OK).body(listarTodosOsPagamentos);
	}

}
