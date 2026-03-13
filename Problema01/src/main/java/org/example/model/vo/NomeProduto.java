package org.example.model.vo;

import org.example.exceptions.DadoInvalidoException;

public class NomeProduto {

    private static final int TAMANHO_MAXIMO = 100;

    private final String valor;

    public NomeProduto(String valor) {
        validar(valor);
        this.valor = valor.trim();
    }

    private static void validar(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new DadoInvalidoException("Nome do produto não pode ser vazio");
        }
        if (valor.trim().length() > TAMANHO_MAXIMO) {
            throw new DadoInvalidoException("Nome do produto não pode ter mais de " + TAMANHO_MAXIMO + " caracteres");
        }
    }

    @Override
    public String toString() {
        return valor;
    }
}
