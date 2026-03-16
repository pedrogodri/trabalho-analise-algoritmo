package org.example.model.vo;

import org.example.exceptions.DadoInvalidoException;

public class Cidade {

    private final String valor;

    public Cidade(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new DadoInvalidoException("Cidade nao pode ser vazia");
        }
        this.valor = valor.trim();
    }

    @Override
    public String toString() {
        return valor;
    }
}
