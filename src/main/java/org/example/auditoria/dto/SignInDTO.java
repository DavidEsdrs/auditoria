package org.example.auditoria.dto;

import jakarta.validation.constraints.NotBlank;

public record SignInDTO(
  @NotBlank String email,
  @NotBlank String senha
) {
}
