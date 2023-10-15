package com.postech.parquimetro.infra.repository;

import com.postech.parquimetro.dominio.entities.pagamento.Credito;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.*;

public non-sealed interface CartaoDeCreditoRepository extends Repository<Credito, UUID>, CreditoNoRepository {

	Optional<Credito> findById(UUID id);

	@Transactional
	@Modifying
	@Query(value = "update Credito credito set " +
		"credito.dataDeVencimento = :#{#credito.dataDeVencimento}, " +
		"credito.nomeDoTitular = :#{#credito.nomeDoTitular} " +
		"where credito.id = :id")
	void editarCartaoDeCredito(Credito credito, UUID id);
}
