package org.example.model.vo;

import org.example.exceptions.DadoInvalidoException;

public class NumeroEndereco {

    private final String valor;

    public NumeroEndereco(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new DadoInvalidoException("Numero do endereco nao pode ser vazio (use S/N se sem numero)");
        }
        this.valor = valor.trim();
    }

    @Override
    public String toString() {
        return valor;
    }
}
