package com.postech.parquimetro.dominio.entities.pagamento;

import com.postech.parquimetro.dominio.entities.Condutor;
import com.postech.parquimetro.dominio.entities.enums.TiposDePagamento;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "metodo_de_pagamento")
public class MetodoDePagamento {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@ManyToOne()
	private Condutor condutor;

	@Column(nullable = false, updatable = false)
	private LocalDateTime dataCadastro = LocalDateTime.now();

	@Column(nullable = false)
	private LocalDateTime ultimaEdicao = LocalDateTime.now();

	@Column(nullable = false, length = 20, name = "tipo_de_pagamento")
	@Enumerated(EnumType.STRING)
	private TiposDePagamento tiposDePagamento;

	@OneToOne(mappedBy = "metodoDePagamento", cascade = {CascadeType.MERGE, CascadeType.REMOVE, CascadeType.PERSIST})
	private Debito debito;

	@OneToOne(mappedBy = "metodoDePagamento", cascade = {CascadeType.MERGE, CascadeType.REMOVE, CascadeType.PERSIST})
	private Credito credito;

	@OneToOne(mappedBy = "metodoDePagamento", cascade = {CascadeType.MERGE, CascadeType.REMOVE, CascadeType.PERSIST})
	private Pix pix;

	@Transient
	private IPagamento pagamento;

	protected MetodoDePagamento() {}

	public MetodoDePagamento(Condutor condutor, TiposDePagamento tiposDePagamento, IPagamento pagamento) {
		this.condutor = condutor;
		this.tiposDePagamento = tiposDePagamento;
		this.pagamento = pagamento;
		this.identificarTipoDePagamento();
	}

	public Condutor getCondutor() {
		return condutor;
	}

	public LocalDateTime getDataCadastro() {
		return dataCadastro;
	}

	public TiposDePagamento getTiposDePagamento() {
		return tiposDePagamento;
	}

	public UUID getId() {
		return id;
	}

	private void identificarTipoDePagamento() {
		Map<Class<?>, Runnable> mappper = new HashMap<>();

		mappper.put(Credito.class, () -> {
			this.credito = (Credito) this.pagamento.criarPagamento();
		});

		mappper.put(Debito.class, () -> {
			this.debito = (Debito) this.pagamento.criarPagamento();
		});

		mappper.put(Pix.class, () -> {
			this.pix = (Pix) this.pagamento.criarPagamento();
		});

		mappper.get(this.pagamento.criarPagamento().getClass()).run();
	}

	public Pix getPix() {
		return pix;
	}

	public Debito getDebito() {
		return debito;
	}

	public Credito getCredito() {
		return credito;
	}
}
