package org.example.model.vo;

import org.example.exceptions.DadoInvalidoException;
import org.example.util.Validacao;

public class Estado {

    private final String sigla;

    public Estado(String sigla) {
        String normalizado = Validacao.validarObrigatorio(sigla, "Estado").toUpperCase();
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
