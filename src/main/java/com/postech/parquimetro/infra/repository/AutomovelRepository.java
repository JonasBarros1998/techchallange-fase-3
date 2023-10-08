package com.postech.parquimetro.infra.repository;

import com.postech.parquimetro.dominio.entities.Automovel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AutomovelRepository extends JpaRepository<Automovel, UUID> {


}
