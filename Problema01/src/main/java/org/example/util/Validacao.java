package org.example.util;

import org.example.exceptions.DadoInvalidoException;

public class Validacao {

    private Validacao() {}

    /**
     * Valida que o valor não é nulo nem vazio, retornando-o já sem espaços extras.
     *
     * @throws DadoInvalidoException se o valor for nulo ou em branco
     */
    public static String validarObrigatorio(String valor, String nomeCampo) {
        if (valor == null || valor.isBlank()) {
            throw new DadoInvalidoException(nomeCampo + " não pode ser vazio");
        }
        return valor.trim();
    }
}
