package com.postech.parquimetro.dominio.entities.pagamento;

import jakarta.persistence.*;
import java.util.UUID;


@Entity
@Table(name = "pix")
public final class Pix implements IPagamento<Pix> {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false, length = 70)
	private String chavePix;

	@OneToOne(orphanRemoval = true, optional = false)
	@JoinColumn(referencedColumnName = "id", name = "metodo_de_pagamento_id")
	MetodoDePagamento metodoDePagamento;

	public Pix() {}

	public Pix(String chavePix) {
		this.chavePix = chavePix;
	}

	public String getChavePix() {
		return chavePix;
	}

	@Override
	public Pix criarPagamento() {
		return this;
	}

	public void setMetodoDePagamento(MetodoDePagamento metodoDePagamento) {
		this.metodoDePagamento = metodoDePagamento;
	}
}

