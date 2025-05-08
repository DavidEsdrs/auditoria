package org.example.auditoria.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.example.auditoria.model.Papel;

public record CreateUsuarioDTO(
        @NotBlank String nome,
        @NotBlank @Email String email,
        @NotBlank String senha,
        Papel papel,
        @NotNull Long departamentoId) {
}
