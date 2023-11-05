package com.postech.parquimetro.infra.repository;

import com.postech.parquimetro.dominio.entities.Condutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CondutorRepository extends JpaRepository<Condutor, UUID> {

	Optional<Condutor> findCondutorByIdAndStatusIsTrue(UUID id);

	@Query(value = "select condutor " +
		"from Condutor condutor " +
		"left join condutor.enderecoDoCondutor endereco " +
		"left join condutor.automovel automovel " +
		"where condutor.status = true")
	List<Condutor> pesquisarPorTodosCondutoresAtivos();

}
