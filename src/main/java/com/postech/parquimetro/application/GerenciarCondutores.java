package com.postech.parquimetro.application;

import com.postech.parquimetro.application.DTO.CondutorDTO;
import com.postech.parquimetro.application.Exceptions.NaoFoiPossivelSalvarOConteudo;
import com.postech.parquimetro.dominio.entities.Condutor;
import com.postech.parquimetro.dominio.entities.EnderecoDoCondutor;
import com.postech.parquimetro.infra.repository.CondutorRepository;
import com.postech.parquimetro.view.form.CondutorForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

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
			condutorDTO.endereco().rua(),
			condutorDTO.endereco().numero(),
			condutorDTO.endereco().cep(),
			condutorDTO.endereco().cidade(),
			condutorDTO.endereco().estado(),
			condutorDTO.endereco().complemento());

		var condutor = new Condutor(
			condutorDTO.nome(),
			condutorDTO.cpf(),
			condutorDTO.telefone(),
			condutorDTO.email(),
			enderecoDoCondutor
		);

		try {
			this.condutorRepository.save(condutor);
			return CondutorDTO.converterCondutorFormParaCondutorDTO(condutorForm);
		} catch (DataIntegrityViolationException ex) {
			throw new NaoFoiPossivelSalvarOConteudo("Nao foi possivel salvar o condutor. Verifique se o condutor ja foi salvo em nosso banco de dados");
		} catch (Exception ex) {
			throw ex;
		}

	}
}
