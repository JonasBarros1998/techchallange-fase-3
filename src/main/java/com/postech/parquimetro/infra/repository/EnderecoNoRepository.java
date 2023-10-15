package com.postech.parquimetro.infra.repository;

import com.postech.parquimetro.dominio.entities.EnderecoDoCondutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;
import java.util.UUID;

@NoRepositoryBean
public sealed interface EnderecoNoRepository permits EnderecoDoCondutorNoRepository {

	void editar(EnderecoDoCondutor enderecoDoCondutor, UUID id);

	Optional<EnderecoDoCondutor> pesquisarPorID(UUID id);
}
