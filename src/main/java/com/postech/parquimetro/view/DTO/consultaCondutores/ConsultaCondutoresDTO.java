package com.postech.parquimetro.view.DTO.consultaCondutores;

import com.postech.parquimetro.aplicacao.DTO.EnderecoDTO;

import java.util.List;
import java.util.UUID;


public record ConsultaCondutoresDTO(

	UUID id,

	String email,

	String nome,

	String telefone,

	EnderecoDTO endereco,

	List<ConsultaAutomoveisDoCondutorDTO> automoveis
) {
}
