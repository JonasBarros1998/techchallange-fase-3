package com.postech.parquimetro.infra.repository;

import com.postech.parquimetro.dominio.entities.pagamento.Debito;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;
import java.util.UUID;

@NoRepositoryBean
public sealed interface DebitoNoRepository permits PagamentoComDebitoRepository {

	void editarDebito(UUID id, Debito debito);
}
