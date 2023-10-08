package com.postech.parquimetro.infra.repository;

import com.postech.parquimetro.dominio.entities.EnderecoDoCondutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@NoRepositoryBean
public interface EnderecoRepository {

	void editar(EnderecoDoCondutor enderecoDoCondutor, UUID id);

	Optional<EnderecoDoCondutor> pesquisarPorID(UUID id);
}
