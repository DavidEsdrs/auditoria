package org.example.auditoria.dto;

import java.time.LocalDateTime;

import org.example.auditoria.model.Departamento;
import org.example.auditoria.model.Status;
import org.example.auditoria.model.Tipo;

public record MinhaSolicitacaoDTO(
    Long id,
    Tipo tipo,
    Status status,
    String descricao,
    Departamento departamento,
    LocalDateTime criacao,
    LocalDateTime ultimaEdicao
) {
}
