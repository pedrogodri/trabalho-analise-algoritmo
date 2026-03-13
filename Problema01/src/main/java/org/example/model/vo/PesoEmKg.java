package org.example.model.vo;

import org.example.exceptions.DadoInvalidoException;

public class PesoEmKg {

    private final float valor;

    public PesoEmKg(float valor) {
        if (valor <= 0) {
            throw new DadoInvalidoException("Peso do produto deve ser maior que zero");
        }
        this.valor = valor;
    }

    public float valor() {
        return valor;
    }

    @Override
    public String toString() {
        return valor + "kg";
    }
}
