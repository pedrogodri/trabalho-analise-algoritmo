package org.example.model.vo;

import org.example.exceptions.DadoInvalidoException;

/**
 * Value Object que representa um CEP brasileiro.
 *
 * <p>Aceita os formatos {@code XXXXX-XXX} e {@code XXXXXXXX} e normaliza
 * internamente para o formato com hífen. Lança {@link org.example.exceptions.DadoInvalidoException}
 * se o valor não contiver exatamente 8 dígitos numéricos.</p>
 */
public class Cep {

    private final String valor;

    public Cep(String cep) {
        if (cep == null || cep.isBlank()) {
            throw new DadoInvalidoException("CEP nao pode ser vazio");
        }
        String digits = cep.trim().replaceAll("[^0-9]", "");
        if (digits.length() != 8) {
            throw new DadoInvalidoException("CEP invalido! Digite 8 digitos (ex: 89010-000 ou 89010000)");
        }
        this.valor = digits.substring(0, 5) + "-" + digits.substring(5);
    }

    @Override
    public String toString() {
        return valor;
    }
}
