package com.postech.parquimetro.aplicacao;

import com.postech.parquimetro.aplicacao.DTO.AutomovelDTO;
import com.postech.parquimetro.aplicacao.Exceptions.ConteudoNaoEncontrado;
import com.postech.parquimetro.dominio.entities.Automovel;
import com.postech.parquimetro.infra.repository.AutomovelRepository;
import com.postech.parquimetro.infra.repository.CondutorRepository;
import com.postech.parquimetro.view.form.AutomovelForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GerenciarAutomoveis {

	AutomovelRepository automovelRepository;

	CondutorRepository condutorRepository;

	@Autowired
	GerenciarAutomoveis(AutomovelRepository automovelRepository, CondutorRepository condutorRepository) {
		this.automovelRepository = automovelRepository;
		this.condutorRepository = condutorRepository;

	}

	public AutomovelDTO salvar(AutomovelForm automovelForm) {
		var automovelDTO = AutomovelDTO.converterAutomovelFormParaAutomovelDTO(automovelForm);

		var condutor = this.condutorRepository.findById(automovelDTO.condutor())
			.orElseThrow(() -> {
				throw new ConteudoNaoEncontrado("Nao foi possivel encontrar o condutor");
			});

		var automovel = new Automovel(
			automovelDTO.placa(),
			automovelDTO.modelo(),
			automovelDTO.tipoAutomovel(),
			condutor
		);

		this.automovelRepository.save(automovel);

		return automovelDTO;
	}

}
