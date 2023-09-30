package com.postech.parquimetro.dominio.entities.pagamento;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "extrato_de_pagamento")
public class ExtratoDePagamento {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false)
	private BigDecimal valor;

	private LocalDateTime dataDeEmisao = LocalDateTime.now();

	@ManyToOne
	private MetodoDePagamento metodoDePagamento;

	public ExtratoDePagamento(BigDecimal valor, LocalDateTime dataDeEmisao, MetodoDePagamento metodoDePagamento) {
		this.valor = valor;
		this.dataDeEmisao = dataDeEmisao;
		this.metodoDePagamento = metodoDePagamento;
	}

	public ExtratoDePagamento() {}

	public UUID getId() {
		return id;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public LocalDateTime getDataDeEmisao() {
		return dataDeEmisao;
	}

	public MetodoDePagamento getPagamento() {
		return metodoDePagamento;
	}
}
