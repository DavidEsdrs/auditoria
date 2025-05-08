package org.example.auditoria.repository;

import org.example.auditoria.model.Aprovacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AprovacaoRepository extends JpaRepository<Aprovacao, Long> {
}
