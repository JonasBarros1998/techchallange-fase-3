package com.postech.parquimetro.aplicacao.DTO.pagamentos;

import com.postech.parquimetro.dominio.entities.pagamento.Credito;
import com.postech.parquimetro.dominio.entities.pagamento.Debito;
import com.postech.parquimetro.dominio.entities.pagamento.MetodoDePagamento;
import com.postech.parquimetro.dominio.entities.pagamento.Pix;

import java.util.ArrayList;
import java.util.List;

public class ListarTodosOsMetodosDePagamentoDTO {
	MetodoDePagamento metodoDePagamento;

	public List<ListarCartaoDeDebitoDTO> debitos = new ArrayList<>();
	public List<ListarCartaoDeCreditoDTO> creditos = new ArrayList<>();
	public List<ListarPixDTO> listarPix = new ArrayList<>();

	/*
	public ListarTodosOsMetodosDePagamentoDTO(MetodoDePagamento metodoDePagamento) {
		this.metodoDePagamento = metodoDePagamento;
	}*/

	public ListarTodosOsMetodosDePagamentoDTO() {}


	private ListarTodosOsMetodosDePagamentoDTO(
		List<ListarCartaoDeDebitoDTO> debitos,
		List<ListarCartaoDeCreditoDTO> creditos,
		List<ListarPixDTO> listarPix
	) {
		this.debitos = debitos;
		this.creditos = creditos;
		this.listarPix = listarPix;
	}

	public ListarTodosOsMetodosDePagamentoDTO converterMetodosDePagamentoDoTipo(Credito credito) {

		if(credito != null) {
			this.creditos.add(new ListarCartaoDeCreditoDTO(
				credito.getId(),
				credito.getNomeDoTitular(),
				credito.getDataDeVencimento(),
				this.metodoDePagamento.getTiposDePagamento()
			));

		}
		return this;
	}

	public ListarTodosOsMetodosDePagamentoDTO converterMetodosDePagamentoDoTipo(Debito debito) {

		if (debito != null) {
			this.debitos.add(new ListarCartaoDeDebitoDTO(
				debito.getId(),
				debito.getNomeDoTitular(),
				debito.getDataDeVencimento(),
				this.metodoDePagamento.getTiposDePagamento()
			));
		}

		return this;
	}

	public ListarTodosOsMetodosDePagamentoDTO converterMetodosDePagamentoDoTipo(Pix pix) {

		if(pix != null) {
			this.listarPix.add(new ListarPixDTO(
				pix.getId(),
				this.metodoDePagamento.getTiposDePagamento(),
				pix.getChavePix().substring(0, 7) + "..."
			));
		}

		return this;
	}

	public ListarTodosOsMetodosDePagamentoDTO carregar() {
		return new ListarTodosOsMetodosDePagamentoDTO(
			this.debitos,
			this.creditos,
			this.listarPix
		);
	}

	public void setMetodoDePagamento(MetodoDePagamento metodoDePagamento) {
		this.metodoDePagamento = metodoDePagamento;
	}

}
