package com.postech.parquimetro.infra.repository;

import com.postech.parquimetro.dominio.entities.pagamento.Debito;
import com.postech.parquimetro.dominio.entities.pagamento.MetodoDePagamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PagamentoRepository extends JpaRepository<MetodoDePagamento, UUID> {
}
