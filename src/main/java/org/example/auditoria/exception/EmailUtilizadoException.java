package org.example.auditoria.exception;

public class EmailUtilizadoException extends RuntimeException {
    public EmailUtilizadoException(String email) {
        super("Email " + email + " já utilizado");
    }
}
