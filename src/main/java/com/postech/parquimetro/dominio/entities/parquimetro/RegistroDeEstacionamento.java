package com.postech.parquimetro.dominio.entities.parquimetro;

import com.postech.parquimetro.dominio.entities.Condutor;
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

	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REMOVE, CascadeType.PERSIST})
	private Condutor condutor;

	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REMOVE, CascadeType.PERSIST})
	@JoinColumn(referencedColumnName = "id", updatable = false)
	private ExtratoDePagamento extratoDePagamento;

	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REMOVE, CascadeType.PERSIST})
	@JoinColumn(referencedColumnName = "id", updatable = false)
	private EnderecoDoEstacionamento enderecoDoEstacionamento;

	@Column()
	private LocalDateTime tempoInicial;

	@Column()
	private LocalDateTime tempoFinal;

	@Column(length = 70, nullable = false)
	private String nomeDoEventoParaAlertas;

	@Column(length = 70, nullable = false)
	private String nomeDoEventoParaAgendamento;

	@Column(nullable = false)
	private Boolean status = Boolean.TRUE;

	public RegistroDeEstacionamento() {}

	public RegistroDeEstacionamento(
		Condutor condutor,
		ExtratoDePagamento extratoDePagamento,
		EnderecoDoEstacionamento endercoDoEstacionamento,
		LocalDateTime dataDeEntrada,
		LocalDateTime dataDeSaida,
		String nomeDoEventoParaAlertas,
		String nomeDoEventoParaAgendamento
		) {
		this.condutor = condutor;
		this.extratoDePagamento = extratoDePagamento;
		this.enderecoDoEstacionamento = endercoDoEstacionamento;
		this.tempoInicial = dataDeEntrada;
		this.tempoFinal = dataDeSaida;
		this.nomeDoEventoParaAlertas = nomeDoEventoParaAlertas;
		this.nomeDoEventoParaAgendamento = nomeDoEventoParaAgendamento;
	}

	public RegistroDeEstacionamento(
		Condutor condutor,
		EnderecoDoEstacionamento endercoDoEstacionamento,
		ExtratoDePagamento extratoDePagamento,
		LocalDateTime dataDeEntrada,
		String nomeDoEventoParaAlertas,
		String nomeDoEventoParaAgendamento
	) {
		this.condutor = condutor;
		this.enderecoDoEstacionamento = endercoDoEstacionamento;
		this.tempoInicial = dataDeEntrada;
		this.extratoDePagamento = extratoDePagamento;
		this.nomeDoEventoParaAlertas = nomeDoEventoParaAlertas;
		this.nomeDoEventoParaAgendamento = nomeDoEventoParaAgendamento;
	}


	public Condutor getCondutor() {
		return condutor;
	}

	public UUID getId() {
		return id;
	}

	public ExtratoDePagamento getExtratoDePagamento() {
		return extratoDePagamento;
	}

	public EnderecoDoEstacionamento getEnderecoDoEstacionamento() {
		return enderecoDoEstacionamento;
	}

	public LocalDateTime getDataDeEntrada() {
		return tempoInicial;
	}

	public LocalDateTime getDataDeSaida() {
		return tempoFinal;
	}

	public String getNomeDoEventoParaAlertas() {
		return nomeDoEventoParaAlertas;
	}
	public String getNomeDoEventoParaAgendamento() {
		return nomeDoEventoParaAgendamento;
	}

	public LocalDateTime getTempoInicial() {
		return tempoInicial;
	}

	public LocalDateTime getTempoFinal() {
		return tempoFinal;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setExtratoDePagamento(ExtratoDePagamento extratoDePagamento) {
		this.extratoDePagamento = extratoDePagamento;
	}

	public void setTempoFinal(LocalDateTime tempoFinal) {
		this.tempoFinal = tempoFinal;
	}

	public void finalizar() {
		this.status = Boolean.FALSE;
	}
}
