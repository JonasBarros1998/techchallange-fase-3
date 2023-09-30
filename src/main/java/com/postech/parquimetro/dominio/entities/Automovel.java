package com.postech.parquimetro.dominio.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "automovel")
public class Automovel {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(length = 7, columnDefinition = "char")
	private String placa;

	@Column(length = 30)
	private String modelo;

	@Column(length = 10)
	private String tipoDeVeiculo;

	@ManyToOne
	private Condutor condutor;

	public Automovel(String placa, String modelo, String tipoDeVeiculo, Condutor condutor) {
		this.placa = placa;
		this.modelo = modelo;
		this.tipoDeVeiculo = tipoDeVeiculo;
		this.condutor = condutor;
	}

	public Automovel() {}

	public UUID getId() {
		return id;
	}

	public String getPlaca() {
		return placa;
	}

	public String getModelo() {
		return modelo;
	}

	public String getTipoDeVeiculo() {
		return tipoDeVeiculo;
	}

	public Condutor getCondutor() {
		return condutor;
	}
}
