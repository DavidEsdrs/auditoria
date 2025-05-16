package org.example.auditoria.repository;

import java.util.List;

import org.example.auditoria.model.Aprovacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AprovacaoRepository extends JpaRepository<Aprovacao, Long> {
    List<Aprovacao> findByAprovadorId(Long id);
}
