package org.example.model.vo;

import org.example.exceptions.DadoInvalidoException;

public class Estado {

    private final String sigla;

    public Estado(String sigla) {
        if (sigla == null || sigla.isBlank()) {
            throw new DadoInvalidoException("Estado nao pode ser vazio");
        }
        String normalizado = sigla.trim().toUpperCase();
        if (!normalizado.matches("[A-Z]{2}")) {
            throw new DadoInvalidoException("Estado invalido! Use a sigla com 2 letras (ex: SC, SP, RJ)");
        }
        this.sigla = normalizado;
    }

    @Override
    public String toString() {
        return sigla;
    }
}
