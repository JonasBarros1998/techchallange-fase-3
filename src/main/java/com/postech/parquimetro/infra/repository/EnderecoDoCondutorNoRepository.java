package com.postech.parquimetro.infra.repository;

import com.postech.parquimetro.dominio.entities.EnderecoDoCondutor;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public non-sealed interface EnderecoDoCondutorNoRepository extends Repository<EnderecoDoCondutor, UUID>, EnderecoNoRepository {

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

	@Query(value = "select endereco from EnderecoDoCondutor endereco " +
		"left join Condutor condutor on condutor.enderecoDoCondutor.id = :id")
	@Override
	Optional<EnderecoDoCondutor> pesquisarPorID(UUID id);

}
