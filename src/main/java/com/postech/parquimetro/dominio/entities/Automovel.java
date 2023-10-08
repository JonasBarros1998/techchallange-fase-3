package com.postech.parquimetro.dominio.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "automovel")
public class Automovel {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(length = 7)
	private String placa;

	@Column(length = 30)
	private String modelo;

	@Column(length = 20)
	private String tipoDoAutomovel;

	@ManyToOne
	private Condutor condutor;

	public Automovel(String placa, String modelo, String tipoDoAutomovel, Condutor condutor) {
		this.placa = placa;
		this.modelo = modelo;
		this.tipoDoAutomovel = tipoDoAutomovel;
		this.condutor = condutor;
	}

	public Automovel(String placa, String modelo, String tipoDoAutomovel) {
		this.placa = placa;
		this.modelo = modelo;
		this.tipoDoAutomovel = tipoDoAutomovel;
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

	public String getTipoDoAutomovel() {
		return tipoDoAutomovel;
	}

	public Condutor getCondutor() {
		return condutor;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public void setTipoDoAutomovel(String tipoDoAutomovel) {
		this.tipoDoAutomovel = tipoDoAutomovel;
	}
}
