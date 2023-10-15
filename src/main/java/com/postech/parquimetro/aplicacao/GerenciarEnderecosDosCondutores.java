package com.postech.parquimetro.aplicacao;

import com.postech.parquimetro.aplicacao.DTO.EnderecoDTO;
import com.postech.parquimetro.aplicacao.Exceptions.ConteudoNaoEncontrado;
import com.postech.parquimetro.dominio.entities.EnderecoDoCondutor;
import com.postech.parquimetro.infra.repository.EnderecoDoCondutorNoRepository;
import com.postech.parquimetro.infra.repository.EnderecoNoRepository;
import com.postech.parquimetro.view.form.EnderecoForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GerenciarEnderecosDosCondutores {

	EnderecoNoRepository enderecoNoRepository;

	EnderecoDoCondutorNoRepository enderecoDoCondutorRepository;

	@Autowired
	GerenciarEnderecosDosCondutores(EnderecoNoRepository enderecoNoRepository, EnderecoDoCondutorNoRepository enderecoDoCondutorRepository) {
		this.enderecoNoRepository = enderecoNoRepository;
		this.enderecoDoCondutorRepository = enderecoDoCondutorRepository;
	}

	GerenciarEnderecosDosCondutores() {}

	public EnderecoDTO editar(EnderecoForm enderecoForm, UUID enderecoID) {
		var enderecoDTO = EnderecoDTO.converterEnderecoFormParaEnderecoDTO(enderecoForm);

		EnderecoDoCondutor enderecoDoCondutor = this.enderecoDoCondutorRepository.pesquisarPorID(enderecoID)
			.orElseThrow(() -> new ConteudoNaoEncontrado("Endereco nao encontrado"));


		enderecoDoCondutor.setEstado(enderecoDTO.estado());
		enderecoDoCondutor.setCep(enderecoDTO.cep());
		enderecoDoCondutor.setCidade(enderecoDTO.cidade());
		enderecoDoCondutor.setRua(enderecoDTO.rua());
		enderecoDoCondutor.setComplemento(enderecoDTO.complemento());

		this.enderecoDoCondutorRepository.editar(enderecoDoCondutor, enderecoID);

		return enderecoDTO;
	}

}

