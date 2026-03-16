package org.example.model.vo;

import org.example.exceptions.DadoInvalidoException;

public class Logradouro {

    private final String valor;

    public Logradouro(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new DadoInvalidoException("Rua/Logradouro nao pode ser vazio");
        }
        this.valor = valor.trim();
    }

    @Override
    public String toString() {
        return valor;
    }
}
