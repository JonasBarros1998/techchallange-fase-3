package com.postech.parquimetro.aplicacao.pagamento;

import com.postech.parquimetro.aplicacao.DTO.pagamentos.CreditoDTO;
import com.postech.parquimetro.aplicacao.DTO.pagamentos.DebitoDTO;
import com.postech.parquimetro.aplicacao.DTO.pagamentos.ListarTodosOsMetodosDePagamentoDTO;
import com.postech.parquimetro.aplicacao.DTO.pagamentos.PixDTO;
import com.postech.parquimetro.aplicacao.exceptions.CondutorDeveTerPeloMenosUmMetodoDePatamentoExcpetion;
import com.postech.parquimetro.aplicacao.exceptions.ConteudoNaoEncontrado;
import com.postech.parquimetro.dominio.VerificarQuantidadeTotalDosMetodosDePagamento;
import com.postech.parquimetro.dominio.enums.TiposDePagamento;
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

import java.util.List;
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

		var condutor = this.condutorRepository.findCondutorByIdAndStatusIsTrue(creditoDTO.condutor()).orElseThrow(() -> {
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

		var condutor = this.condutorRepository.findCondutorByIdAndStatusIsTrue(debitoDTO.condutor()).orElseThrow(() -> {
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

		var condutor = this.condutorRepository.findCondutorByIdAndStatusIsTrue(pixDTO.condutor()).orElseThrow(() -> {
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

	public ListarTodosOsMetodosDePagamentoDTO pesquisarTodosOsMetodosDePagamento(UUID condutorID) {
		List<MetodoDePagamento> metodosDePagamento = this.pagamentoRepository.findByCondutorId(condutorID)
			.orElseThrow(() -> new ConteudoNaoEncontrado("condutor nao encontrado"));

		ListarTodosOsMetodosDePagamentoDTO listarTodosOsMetodosDePagamentoDTO = new ListarTodosOsMetodosDePagamentoDTO();

		metodosDePagamento.stream().map((metodoDePagamento) -> {
			listarTodosOsMetodosDePagamentoDTO.setMetodoDePagamento(metodoDePagamento);

			return listarTodosOsMetodosDePagamentoDTO
				.converterMetodosDePagamentoDoTipo(metodoDePagamento.getPix())
				.converterMetodosDePagamentoDoTipo(metodoDePagamento.getCredito())
				.converterMetodosDePagamentoDoTipo(metodoDePagamento.getDebito());

		}).toList();

		return listarTodosOsMetodosDePagamentoDTO;
	}
}
