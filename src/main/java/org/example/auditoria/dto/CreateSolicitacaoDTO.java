package org.example.auditoria.dto;

import org.example.auditoria.model.Tipo;

public record CreateSolicitacaoDTO(
        Tipo tipo,
        String descricao,
        Long criadorId,
        Long departamentoId,
        Long gestorId,
        Long financeiroId
) {
}
