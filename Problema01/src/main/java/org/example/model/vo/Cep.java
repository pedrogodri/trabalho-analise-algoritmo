package org.example.model.vo;

import org.example.exceptions.DadoInvalidoException;
import org.example.util.Validacao;

import java.util.Objects;

/**
 * Value Object que representa um CEP brasileiro.
 *
 * <p>Aceita os formatos {@code XXXXX-XXX} e {@code XXXXXXXX} e normaliza
 * internamente para o formato com hífen. Lança {@link DadoInvalidoException}
 * se o valor não contiver exatamente 8 dígitos numéricos.</p>
 */
public class Cep {

    private static final int TOTAL_DIGITOS = 8;
    private static final String MENSAGEM_INVALIDO = "CEP invalido! Digite 8 digitos (ex: 00000-000 ou 00000000)";

    private final String valor;

    public Cep(String cep) {
        String digits = Validacao.validarObrigatorio(cep, "CEP").replaceAll("[^0-9]", "");
        if (digits.length() != TOTAL_DIGITOS) {
            throw new DadoInvalidoException(MENSAGEM_INVALIDO);
        }
        this.valor = digits.substring(0, 5) + "-" + digits.substring(5);
    }

    @Override
    public String toString() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cep that)) return false;
        return Objects.equals(valor, that.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }
}
