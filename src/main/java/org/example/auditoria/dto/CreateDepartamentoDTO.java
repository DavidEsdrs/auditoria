package org.example.auditoria.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateDepartamentoDTO(
        @NotBlank String nome
) {
}
