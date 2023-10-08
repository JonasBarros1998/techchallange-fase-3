package com.postech.parquimetro.aplicacao;

import com.postech.parquimetro.aplicacao.DTO.EnderecoDTO;
import com.postech.parquimetro.aplicacao.Exceptions.ConteudoNaoEncontrado;
import com.postech.parquimetro.dominio.entities.EnderecoDoCondutor;
import com.postech.parquimetro.infra.repository.EnderecoDoCondutorRepository;
import com.postech.parquimetro.infra.repository.EnderecoRepository;
import com.postech.parquimetro.view.form.EnderecoForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class GerenciarEnderecosDosCondutores {

	EnderecoRepository enderecoRepository;

	EnderecoDoCondutorRepository enderecoDoCondutorRepository;

	@Autowired
	GerenciarEnderecosDosCondutores(EnderecoRepository enderecoRepository, EnderecoDoCondutorRepository enderecoDoCondutorRepository) {
		this.enderecoRepository = enderecoRepository;
		this.enderecoDoCondutorRepository = enderecoDoCondutorRepository;
	}

	GerenciarEnderecosDosCondutores() {}

	public EnderecoDTO editar(EnderecoForm enderecoForm, UUID enderecoID) {
		var enderecoDTO = EnderecoDTO.converterEnderecoFormParaEnderecoDTO(enderecoForm);

		EnderecoDoCondutor enderecoDoCondutor = this.enderecoDoCondutorRepository.pesquisarPorID(enderecoID)
			.orElseThrow(() -> new ConteudoNaoEncontrado("Enderedo nao encontrado"));


		enderecoDoCondutor.setEstado(enderecoDTO.estado());
		enderecoDoCondutor.setCep(enderecoDTO.cep());
		enderecoDoCondutor.setCidade(enderecoDTO.cidade());
		enderecoDoCondutor.setRua(enderecoDTO.rua());
		enderecoDoCondutor.setComplemento(enderecoDTO.complemento());

		this.enderecoDoCondutorRepository.editar(enderecoDoCondutor, enderecoID);

		return enderecoDTO;
	}

}

