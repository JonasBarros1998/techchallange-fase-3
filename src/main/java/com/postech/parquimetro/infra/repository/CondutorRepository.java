package com.postech.parquimetro.infra.repository;

import com.postech.parquimetro.dominio.entities.Condutor;
import com.postech.parquimetro.view.form.controller.CondutorController;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface CondutorRepository extends JpaRepository<Condutor, UUID> {
}
