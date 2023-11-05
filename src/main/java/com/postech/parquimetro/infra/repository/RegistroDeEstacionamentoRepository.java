package com.postech.parquimetro.infra.repository;

import com.postech.parquimetro.dominio.entities.parquimetro.RegistroDeEstacionamento;
import com.postech.parquimetro.dominio.entities.projections.ConsultaRegistrosDeEstacionamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RegistroDeEstacionamentoRepository extends JpaRepository<RegistroDeEstacionamento, UUID> {

	List<ConsultaRegistrosDeEstacionamento> findByCondutorIdAndStatusIsTrue(UUID condutorID);
}
