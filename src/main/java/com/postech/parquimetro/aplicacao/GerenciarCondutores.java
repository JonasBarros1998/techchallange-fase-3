package com.postech.parquimetro.aplicacao;

import com.postech.parquimetro.aplicacao.DTO.CondutorDTO;
import com.postech.parquimetro.aplicacao.Exceptions.ConteudoDuplicado;
import com.postech.parquimetro.aplicacao.Exceptions.ConteudoNaoEncontrado;
import com.postech.parquimetro.dominio.entities.Condutor;
import com.postech.parquimetro.dominio.entities.EnderecoDoCondutor;
import com.postech.parquimetro.infra.repository.CondutorRepository;
import com.postech.parquimetro.view.form.CondutorForm;
import com.postech.parquimetro.view.form.EditarCondutorForm;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GerenciarCondutores {

	CondutorRepository condutorRepository;

	GerenciarCondutores() {}
	@Autowired
	GerenciarCondutores(CondutorRepository condutorRepository) {
		this.condutorRepository = condutorRepository;
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
			throw new ConteudoDuplicado("Nao foi possivel salvar o condutor. Verifique se o condutor ja foi salvo em nosso banco de dados");
		} catch (Exception ex) {
			throw ex;
		}
	}

	@Transactional
	public EditarCondutorForm editar(EditarCondutorForm condutorForm, UUID id) {
		var condutorDTO = CondutorDTO.converterCondutorFormParaCondutorDTO(condutorForm);

		Condutor condutor = this.condutorRepository.findCondutorById(id)
			.orElseThrow(() -> new ConteudoNaoEncontrado("Nao foi possivel encontrar o condutor"));

		condutor.setNome(condutorDTO.getNome());
		condutor.setEmail(condutorDTO.getEmail());
		condutor.setTelefone(condutorDTO.getTelefone());

		this.condutorRepository.save(condutor);

		return condutorForm;
	}
}
