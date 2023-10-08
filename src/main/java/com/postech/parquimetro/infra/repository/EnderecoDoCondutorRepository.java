package com.postech.parquimetro.infra.repository;

import com.postech.parquimetro.dominio.entities.EnderecoDoCondutor;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public interface EnderecoDoCondutorRepository extends Repository<EnderecoDoCondutor, UUID>, EnderecoRepository {

	@Transactional
	@Modifying
	@Query(value = "update EnderecoDoCondutor endereco set " +
		"endereco.cidade = :#{#endereco.cidade}, " +
		"endereco.cep = :#{#endereco.cep}, " +
		"endereco.complemento = :#{#endereco.complemento}, " +
		"endereco.estado = :#{#endereco.estado}, " +
		"endereco.rua = :#{#endereco.rua}, " +
		"endereco.numero = :#{#endereco.numero} " +
		"where endereco.id = :id")
	@Override
	void editar(@Param("endereco") EnderecoDoCondutor endereco, UUID id);

	@Query(value = "select endereco from EnderecoDoCondutor endereco where endereco.id = :id")
	@Override
	Optional<EnderecoDoCondutor> pesquisarPorID(UUID id);

}
