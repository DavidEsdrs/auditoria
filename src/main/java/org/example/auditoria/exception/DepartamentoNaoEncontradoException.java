package org.example.auditoria.exception;

public class DepartamentoNaoEncontradoException extends RuntimeException {
    public DepartamentoNaoEncontradoException(Long id) {
        super("Departamento de ID " + id + " não encontrado");
    }
}
