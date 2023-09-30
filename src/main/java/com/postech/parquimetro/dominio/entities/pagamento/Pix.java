package com.postech.parquimetro.dominio.entities.pagamento;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "pix")
public class Pix implements IPagamento {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false, length = 70)
	private String chavePix;

	public Pix() {}

	public String getChavePix() {
		return chavePix;
	}

	@Override
	public IPagamento criarPagamento() {
		return this;
	}
}

