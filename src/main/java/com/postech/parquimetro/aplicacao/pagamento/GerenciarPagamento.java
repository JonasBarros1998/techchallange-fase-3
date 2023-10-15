package com.postech.parquimetro.aplicacao.pagamento;

import com.postech.parquimetro.aplicacao.DTO.pagamentos.CreditoDTO;
import com.postech.parquimetro.aplicacao.DTO.pagamentos.DebitoDTO;
import com.postech.parquimetro.aplicacao.DTO.pagamentos.PixDTO;
import com.postech.parquimetro.aplicacao.Exceptions.CondutorDeveTerPeloMenosUmMetodoDePatamentoExcpetion;
import com.postech.parquimetro.aplicacao.Exceptions.ConteudoNaoEncontrado;
import com.postech.parquimetro.dominio.VerificarQuantidadeTotalDosMetodosDePagamento;
import com.postech.parquimetro.dominio.entities.enums.TiposDePagamento;
import com.postech.parquimetro.dominio.entities.pagamento.Credito;
import com.postech.parquimetro.dominio.entities.pagamento.Debito;
import com.postech.parquimetro.dominio.entities.pagamento.MetodoDePagamento;
import com.postech.parquimetro.dominio.entities.pagamento.Pix;
import com.postech.parquimetro.dominio.entities.projections.PesquisarPorUmMetodoDePagamentoDoCondutor;
import com.postech.parquimetro.infra.repository.CartaoDeCreditoRepository;
import com.postech.parquimetro.infra.repository.CondutorRepository;
import com.postech.parquimetro.infra.repository.PagamentoComDebitoRepository;
import com.postech.parquimetro.infra.repository.PagamentoRepository;
import com.postech.parquimetro.view.form.pagamentos.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GerenciarPagamento {

	PagamentoRepository pagamentoRepository;
	CondutorRepository condutorRepository;
	CartaoDeCreditoRepository creditoRepository;

	PagamentoComDebitoRepository debitoRepository;

	GerenciarPagamento(
		PagamentoRepository pagamentoRepository,
		CondutorRepository condutorRepository,
		CartaoDeCreditoRepository creditoRepository,
		PagamentoComDebitoRepository debitoRepository) {
		this.pagamentoRepository = pagamentoRepository;
		this.condutorRepository = condutorRepository;
		this.creditoRepository = creditoRepository;
		this.debitoRepository = debitoRepository;
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
			creditoDTO.dataDeVencimento(),
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

	public EditarCreditoForm editarTipoDePagamento(UUID id, EditarCreditoForm creditoForm) {
		CreditoDTO creditoDTO = CreditoDTO.converterDeEditarCreditoFormParaCreditoDTO(creditoForm);

		Credito credito = this.creditoRepository.findById(id)
			.orElseThrow(() -> new ConteudoNaoEncontrado("cartao de credito nao foi encontrado"));

		credito.setNomeDoTitular(creditoDTO.nomeDoTitular());
		credito.setDataDeVencimento(creditoDTO.dataDeVencimento());
		this.creditoRepository.editarCartaoDeCredito(credito, id);

		return creditoForm;
	}

	public EditarDebitoForm editarTipoDePagamento(UUID id, EditarDebitoForm debitoForm) {
		DebitoDTO debitoDTO = DebitoDTO.converterDeDebitoFormParaDebitoDTO(debitoForm);

		Debito debito = this.debitoRepository.findById(id)
			.orElseThrow(() -> new ConteudoNaoEncontrado("cartao de debito nao foi encontrado"));

		debito.setDataDeVencimento(debitoDTO.dataDeValidade());
		debito.setNomeDoTitular(debitoDTO.nomeDoTitular());
		this.debitoRepository.editarDebito(id, debito);

		return debitoForm;
	}

	public void removerMetodoDePagamento(UUID metodoDePagamentoID) {

		PesquisarPorUmMetodoDePagamentoDoCondutor identificarQualOMetodoDePagamento = this.pagamentoRepository
				.pesquisarMetodoDePagamento(metodoDePagamentoID)
			.orElseThrow(() -> new ConteudoNaoEncontrado("metodo de pagamento nao encontrado"));

		var metodosDePagamentos = this.pagamentoRepository
			.pesquisarTodosOsMetodosDePagamentoPorIdDoCondutor(identificarQualOMetodoDePagamento.getCondutorId());

		var quantidadeTotalDosMetodosDePagamento = new VerificarQuantidadeTotalDosMetodosDePagamento();

		Boolean podeRemoverMetodoDePagamento = quantidadeTotalDosMetodosDePagamento
			.podeRemoverMetodoDePagamento(metodosDePagamentos, identificarQualOMetodoDePagamento.getTipoDePagamento());

		if(podeRemoverMetodoDePagamento == true) {
			this.pagamentoRepository.deleteById(metodoDePagamentoID);
			return;
		}

		throw new CondutorDeveTerPeloMenosUmMetodoDePatamentoExcpetion(
			"O condutor nao pode ficar sem nenhum metodo de pagamento do tipo Credito ou Debito ja cadsatrado " +
				"Adicione outro metodo de pagamento para que assim consiga remover este metodo"
		);
	}
}
