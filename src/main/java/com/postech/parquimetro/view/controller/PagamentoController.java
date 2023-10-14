package com.postech.parquimetro.view.controller;

import com.postech.parquimetro.aplicacao.DTO.pagamentos.CreditoDTO;
import com.postech.parquimetro.aplicacao.DTO.pagamentos.DebitoDTO;
import com.postech.parquimetro.aplicacao.DTO.pagamentos.PixDTO;
import com.postech.parquimetro.aplicacao.pagamento.GerenciarPagamento;
import com.postech.parquimetro.view.form.pagamentos.CreditoForm;
import com.postech.parquimetro.view.form.pagamentos.DebitoForm;
import com.postech.parquimetro.view.form.pagamentos.PixForm;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

	@PostMapping("/credito")
	public ResponseEntity<CreditoDTO> cadastrarCartaoDeCreditoComoMetodoDePagamento(@RequestBody @Valid CreditoForm creditoForm) {
		this.gerenciarPagamento.cadastrarNovoMetodoDePagamento(creditoForm);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

}
