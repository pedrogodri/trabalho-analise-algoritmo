package org.example.model.vo;

import org.example.exceptions.DadoInvalidoException;

public class Quantidade {

    private final int valor;

    public Quantidade(int valor) {
        if (valor <= 0) {
            throw new DadoInvalidoException("Quantidade deve ser maior que zero");
        }
        this.valor = valor;
    }

    public int valor() {
        return valor;
    }

    @Override
    public String toString() {
        return String.valueOf(valor);
    }
}
