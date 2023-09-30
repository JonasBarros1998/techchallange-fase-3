package com.postech.parquimetro.dominio.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "endereco_do_estacionamento")
public class EnderecoDoEstacionamento {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	private String rua;

	private String cidade;

	private String Estado;

}
