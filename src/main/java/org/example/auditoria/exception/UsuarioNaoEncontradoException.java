package org.example.auditoria.exception;

public class UsuarioNaoEncontradoException extends RuntimeException {
    public UsuarioNaoEncontradoException(Long id) {
        super("Usuário com id " + id + " não encontrado");
    }
}
