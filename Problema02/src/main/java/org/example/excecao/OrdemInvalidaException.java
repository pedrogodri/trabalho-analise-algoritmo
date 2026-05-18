package org.example.excecao;

public class OrdemInvalidaException extends RuntimeException {
    public OrdemInvalidaException(String mensagem) {
        super(mensagem);
    }
}