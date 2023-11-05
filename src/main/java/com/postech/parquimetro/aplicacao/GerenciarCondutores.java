package com.postech.parquimetro.aplicacao;

import com.postech.parquimetro.aplicacao.DTO.CondutorDTO;
import com.postech.parquimetro.aplicacao.exceptions.ConteudoDuplicado;
import com.postech.parquimetro.aplicacao.exceptions.ConteudoNaoEncontrado;
import com.postech.parquimetro.dominio.entities.Condutor;
import com.postech.parquimetro.dominio.entities.EnderecoDoCondutor;
import com.postech.parquimetro.dominio.entities.projections.ConsultaRegistrosDeEstacionamento;
import com.postech.parquimetro.infra.repository.CondutorRepository;
import com.postech.parquimetro.infra.repository.RegistroDeEstacionamentoRepository;
import com.postech.parquimetro.view.DTO.consultaCondutores.ConsultaCondutoresDTO;
import com.postech.parquimetro.view.form.CondutorForm;
import com.postech.parquimetro.view.form.EditarCondutorForm;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class GerenciarCondutores {

	CondutorRepository condutorRepository;

	RegistroDeEstacionamentoRepository registroDeEstacionamentoRepository;

	GerenciarCondutores() {}
	@Autowired
	GerenciarCondutores(CondutorRepository condutorRepository,
	                    RegistroDeEstacionamentoRepository registroDeEstacionamentoRepository) {
		this.condutorRepository = condutorRepository;
		this.registroDeEstacionamentoRepository = registroDeEstacionamentoRepository;
	}

	public CondutorDTO salvar(CondutorForm condutorForm) {
		var condutorDTO = CondutorDTO.converterCondutorFormParaCondutorDTO(condutorForm);

		var enderecoDoCondutor = new EnderecoDoCondutor(
			condutorDTO.getEndereco().rua(),
			condutorDTO.getEndereco().numero(),
			condutorDTO.getEndereco().cep(),
			condutorDTO.getEndereco().cidade(),
			condutorDTO.getEndereco().estado(),
			condutorDTO.getEndereco().complemento());

		var condutor = new Condutor(
			condutorDTO.getNome(),
			condutorDTO.getCpf(),
			condutorDTO.getTelefone(),
			condutorDTO.getEmail(),
			enderecoDoCondutor
		);

		try {
			this.condutorRepository.save(condutor);
			return CondutorDTO.converterCondutorFormParaCondutorDTO(condutorForm);
		} catch (DataIntegrityViolationException ex) {
			throw new ConteudoDuplicado("Condutor duplicado");
		} catch (Exception ex) {
			throw ex;
		}
	}

	@Transactional
	public EditarCondutorForm editar(EditarCondutorForm condutorForm, UUID id) {
		var condutorDTO = CondutorDTO.converterCondutorFormParaCondutorDTO(condutorForm);

		Condutor condutor = this.condutorRepository.findCondutorByIdAndStatusIsTrue(id)
			.orElseThrow(() -> new ConteudoNaoEncontrado("Nao foi possivel encontrar o condutor"));

		condutor.setNome(condutorDTO.getNome());
		condutor.setEmail(condutorDTO.getEmail());
		condutor.setTelefone(condutorDTO.getTelefone());

		this.condutorRepository.save(condutor);

		return condutorForm;
	}

	public List<ConsultaCondutoresDTO> consultarTodosCondutores() {
		List<Condutor> condutores = this.condutorRepository.pesquisarPorTodosCondutoresAtivos();
		return CondutorDTO.converterCondutorParaConsultaCondutoresDTO(condutores);
	}

	@Transactional
	public void remover(UUID condutorID) {
		Condutor condutor = this.condutorRepository.findCondutorByIdAndStatusIsTrue(condutorID)
			.orElseThrow(() -> new ConteudoNaoEncontrado("condutor nao encontrado"));

		condutor.desativarCondutor();
		this.condutorRepository.save(condutor);
	}

	public List<ConsultaRegistrosDeEstacionamento> pesquisarTodosOsRegistrosDeEstacionamento(UUID condutorID) {
		var registros = this.registroDeEstacionamentoRepository.findByCondutorIdAndStatusIsTrue(condutorID);
		Collections.sort(registros, Comparator.comparing(ConsultaRegistrosDeEstacionamento::getTempoInicial).reversed());
		return registros;
	}
}
