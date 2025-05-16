package org.example.auditoria.repository;

import java.util.List;

import org.example.auditoria.model.Solicitacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolicitacaoRepository extends JpaRepository<Solicitacao, Long> {
    List<Solicitacao> findByCriadorId(Long id);
    List<Solicitacao> findByIdIn(List<Long> ids);
}
