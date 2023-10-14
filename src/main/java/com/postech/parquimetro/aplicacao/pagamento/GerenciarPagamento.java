package com.postech.parquimetro.aplicacao.pagamento;

import com.postech.parquimetro.aplicacao.DTO.pagamentos.CreditoDTO;
import com.postech.parquimetro.aplicacao.DTO.pagamentos.DebitoDTO;
import com.postech.parquimetro.aplicacao.DTO.pagamentos.PixDTO;
import com.postech.parquimetro.aplicacao.Exceptions.ConteudoNaoEncontrado;
import com.postech.parquimetro.dominio.entities.enums.TiposDePagamento;
import com.postech.parquimetro.dominio.entities.pagamento.Credito;
import com.postech.parquimetro.dominio.entities.pagamento.Debito;
import com.postech.parquimetro.dominio.entities.pagamento.MetodoDePagamento;
import com.postech.parquimetro.dominio.entities.pagamento.Pix;
import com.postech.parquimetro.infra.repository.CondutorRepository;
import com.postech.parquimetro.infra.repository.PagamentoRepository;
import com.postech.parquimetro.view.form.pagamentos.CreditoForm;
import com.postech.parquimetro.view.form.pagamentos.DebitoForm;
import com.postech.parquimetro.view.form.pagamentos.PixForm;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class GerenciarPagamento {

	PagamentoRepository pagamentoRepository;
	CondutorRepository condutorRepository;

	GerenciarPagamento(
		PagamentoRepository pagamentoRepository,
		CondutorRepository condutorRepository) {
		this.pagamentoRepository = pagamentoRepository;
		this.condutorRepository = condutorRepository;
	}

	@Transactional
	public CreditoDTO cadastrarNovoMetodoDePagamento(CreditoForm creditoForm) {
		CreditoDTO creditoDTO = CreditoDTO.converterDeCreditoFormParaCreditoDTO(creditoForm);

		var condutor = this.condutorRepository.findById(creditoDTO.condutor()).orElseThrow(() -> {
			throw new ConteudoNaoEncontrado("Condutor nao encontrado");
		});

		var credito = new Credito(
			creditoDTO.numeroDoCartao(),
			creditoDTO.nomeDoTitular(),
			creditoDTO.dataDeValidade(),
			creditoDTO.codigoDeSeguranca(),
			creditoDTO.bandeira());

		var metodoDePagamento = new MetodoDePagamento(condutor, TiposDePagamento.CREDITO, credito);
		credito.setMetodoDePagamento(metodoDePagamento);

		this.pagamentoRepository.save(metodoDePagamento);
		return creditoDTO;
	}

	@Transactional
	public DebitoDTO cadastrarNovoMetodoDePagamento(DebitoForm debitoForm) {
		DebitoDTO debitoDTO = DebitoDTO.converterDeDebitoFormParaDebitoDTO(debitoForm);

		var condutor = this.condutorRepository.findById(debitoDTO.condutor()).orElseThrow(() -> {
			throw new ConteudoNaoEncontrado("Condutor nao encontrado");
		});

		var debito = new Debito(
			debitoDTO.numeroDoCartao(),
			debitoDTO.nomeDoTitular(),
			debitoDTO.dataDeValidade(),
			debitoDTO.codigoDeSeguranca(),
			debitoDTO.nomeDaInstituicaoFinanceira());

		var metodoDePagamento = new MetodoDePagamento(condutor, TiposDePagamento.DEBITO, debito);
		debito.setMetodoDePagamento(metodoDePagamento);

		this.pagamentoRepository.save(metodoDePagamento);
		return debitoDTO;
	}

	@Transactional
	public PixDTO cadastrarNovoMetodoDePagamento(PixForm pixForm) {
		PixDTO pixDTO = PixDTO.converterDePixFormParaPixDTO(pixForm);

		var condutor = this.condutorRepository.findById(pixDTO.condutor()).orElseThrow(() -> {
			throw new ConteudoNaoEncontrado("Condutor nao encontrado");
		});

		var pix = new Pix(pixDTO.chavePix());

		var metodoDePagamento = new MetodoDePagamento(
			condutor,
			TiposDePagamento.PIX,
			pix
		);

		pix.setMetodoDePagamento(metodoDePagamento);
		this.pagamentoRepository.save(metodoDePagamento);
		return pixDTO;
	}
}
