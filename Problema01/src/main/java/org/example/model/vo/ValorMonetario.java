package org.example.model.vo;

import org.example.exceptions.DadoInvalidoException;

public class ValorMonetario {

    private final float valor;

    public ValorMonetario(float valor) {
        if (valor <= 0) {
            throw new DadoInvalidoException("Valor do produto deve ser maior que zero");
        }
        this.valor = valor;
    }

    public float valor() {
        return valor;
    }

    @Override
    public String toString() {
        return String.format("R$ %.2f", valor);
    }
}
