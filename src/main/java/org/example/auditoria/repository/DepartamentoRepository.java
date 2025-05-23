package org.example.auditoria.repository;

import java.util.Optional;

import org.example.auditoria.model.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {
  Optional<Departamento> findByNome(String nome);
}
