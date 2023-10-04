package com.postech.parquimetro.infra.repository;

import com.postech.parquimetro.dominio.entities.Automovel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AutomovelRepository extends JpaRepository<Automovel, UUID> {

}
