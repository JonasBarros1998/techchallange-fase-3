package com.postech.parquimetro.infra.repository;

import com.postech.parquimetro.dominio.entities.Condutor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CondutorRepository extends JpaRepository<Condutor, String> {

	public Optional<Condutor> findCondutorById(UUID id);
}
