package com.postech.parquimetro.infra.repository;

import com.postech.parquimetro.dominio.entities.Condutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CondutorRepository extends JpaRepository<Condutor, String> {

	Optional<Condutor> findCondutorById(UUID id);

	@Query(value = "select condutor, automovel, endereco " +
		"from Condutor condutor, EnderecoDoCondutor endereco, Automovel automovel")
	List<Condutor> pesquisarTodosCondutores();

}
