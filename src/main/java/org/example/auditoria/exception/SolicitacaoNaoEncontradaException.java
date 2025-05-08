package org.example.auditoria.exception;

public class SolicitacaoNaoEncontradaException extends RuntimeException {
    public SolicitacaoNaoEncontradaException(Long id) {
        super("Solicitação de ID " + id + " não encontrada");
    }
}
