package com.postech.parquimetro.infra.repository;

import com.postech.parquimetro.dominio.entities.pagamento.Debito;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.Optional;
import java.util.UUID;

public non-sealed interface PagamentoComDebitoRepository extends Repository<Debito, UUID>, DebitoNoRepository {

	Optional<Debito> findById(UUID id);

	@Transactional
	@Modifying
	@Query(value = "update Debito debito set " +
		"debito.dataDeVencimento = :#{#debito.dataDeVencimento}, " +
		"debito.nomeDoTitular = :#{#debito.nomeDoTitular} " +
		"where debito.id = :id")
	@Override
	void editarDebito(UUID id, Debito debito);
}
