package com.postech.parquimetro.dominio.entities.pagamento;

import com.postech.parquimetro.dominio.entities.Condutor;
import jakarta.persistence.*;
//import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "metodo_de_pagamento")
public class MetodoDePagamento {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID uuid;

	@ManyToOne
	private Condutor condutor;

	@Column(nullable = false, updatable = false)
	private LocalDateTime dataCadastro = LocalDateTime.now();

	@Column(nullable = false)
	private LocalDateTime ultimaEdicao = LocalDateTime.now();

	private String metodoDePagamento;

	/*
	@Transient
	private IPagamento pagamento;*/

	@OneToOne
	private Credito credito;

	@OneToOne
	private Debito debito;

	@OneToOne
	private Pix pix;

	public MetodoDePagamento() {}

	/*
	public MetodoDePagamento(Condutor condutor, LocalDateTime dataCadastro, String metodoDePagamento, IPagamento pagamento) {
		this.condutor = condutor;
		this.dataCadastro = dataCadastro;
		this.metodoDePagamento = metodoDePagamento;
		this.pagamento = pagamento;
	}*/

	public MetodoDePagamento(Condutor condutor, LocalDateTime dataCadastro, String metodoDePagamento, Credito credito) {
		this.condutor = condutor;
		this.dataCadastro = dataCadastro;
		this.metodoDePagamento = metodoDePagamento;
		this.credito = credito;
	}

	public MetodoDePagamento(Condutor condutor, LocalDateTime dataCadastro, String metodoDePagamento, Debito debito) {
		this.condutor = condutor;
		this.dataCadastro = dataCadastro;
		this.metodoDePagamento = metodoDePagamento;
		this.debito = debito;
	}

	public MetodoDePagamento(Condutor condutor, LocalDateTime dataCadastro, String metodoDePagamento, Pix pix) {
		this.condutor = condutor;
		this.dataCadastro = dataCadastro;
		this.metodoDePagamento = metodoDePagamento;
		this.pix = pix;
	}

	public UUID getUuid() {
		return uuid;
	}

	public Condutor getCondutor() {
		return condutor;
	}

	public LocalDateTime getDataCadastro() {
		return dataCadastro;
	}

	public String getMetodoDePagamento() {
		return metodoDePagamento;
	}


	public Credito getCredito() {
		return credito;
	}

	public Debito getDebito() {
		return debito;
	}

	public Pix getPix() {
		return pix;
	}
}
