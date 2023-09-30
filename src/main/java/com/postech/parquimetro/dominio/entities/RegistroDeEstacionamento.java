package com.postech.parquimetro.dominio.entities;

import com.postech.parquimetro.dominio.entities.pagamento.ExtratoDePagamento;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "registro_de_estacionamento")
public class RegistroDeEstacionamento {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@ManyToOne
	private Condutor condutor;

	@ManyToOne
	private ExtratoDePagamento extratoDePagamento;

	@ManyToOne
	private EnderecoDoEstacionamento endercoDoEstacionamento;

	@Column(nullable = false, updatable = false)
	private LocalDateTime dataDeEntrada;

	@Column()
	private LocalDateTime dataDeSaida;

	public RegistroDeEstacionamento() {}

	public RegistroDeEstacionamento(
		Condutor condutor,
		ExtratoDePagamento extratoDePagamento,
		EnderecoDoEstacionamento endercoDoEstacionamento,
		LocalDateTime dataDeEntrada,
		LocalDateTime dataDeSaida) {
		this.condutor = condutor;
		this.extratoDePagamento = extratoDePagamento;
		this.endercoDoEstacionamento = endercoDoEstacionamento;
		this.dataDeEntrada = dataDeEntrada;
		this.dataDeSaida = dataDeSaida;
	}

	public Condutor getCondutor() {
		return condutor;
	}

	public ExtratoDePagamento getExtratoDePagamento() {
		return extratoDePagamento;
	}

	public EnderecoDoEstacionamento getEndercoDoEstacionamento() {
		return endercoDoEstacionamento;
	}

	public LocalDateTime getDataDeEntrada() {
		return dataDeEntrada;
	}

	public LocalDateTime getDataDeSaida() {
		return dataDeSaida;
	}
}
