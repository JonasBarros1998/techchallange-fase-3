package com.postech.parquimetro.infra.repository;

import com.postech.parquimetro.dominio.entities.pagamento.MetodoDePagamento;
import com.postech.parquimetro.dominio.entities.projections.ConsultaTodosOsMetodosDePagamentoDoCondutor;
import com.postech.parquimetro.dominio.entities.projections.PesquisarPorUmMetodoDePagamentoDoCondutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PagamentoRepository extends JpaRepository<MetodoDePagamento, UUID> {

	@Query(value = "select  metodoDePagamento.id as id," +
		"metodoDePagamento.tiposDePagamento as tiposDePagamento, " +
		"metodoDePagamento.dataCadastro as dataCadastro " +
		"from MetodoDePagamento as metodoDePagamento " +
		"left join Condutor condutor on condutor.cpf = metodoDePagamento.condutor.cpf " +
		"where condutor.id = :id")
	List<ConsultaTodosOsMetodosDePagamentoDoCondutor> pesquisarTodosOsMetodosDePagamentoPorIdDoCondutor(UUID id);

	@Query(value = "select metodoDePagamento.tiposDePagamento as tipoDePagamento, metodoDePagamento.condutor.id as condutorId " +
		"from MetodoDePagamento metodoDePagamento " +
		"where metodoDePagamento.id = :id")
	Optional<PesquisarPorUmMetodoDePagamentoDoCondutor> pesquisarMetodoDePagamento(UUID id);
}
