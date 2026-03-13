package org.example.model.vo;

import org.example.exceptions.DadoInvalidoException;

public class NomeCliente {

    private final String valor;

    public NomeCliente(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new DadoInvalidoException("Nome completo nao pode ser vazio");
        }
        String normalizado = valor.trim();
        if (!normalizado.contains(" ")) {
            throw new DadoInvalidoException("Informe o nome e sobrenome");
        }
        this.valor = normalizado;
    }

    @Override
    public String toString() {
        return valor;
    }
}
