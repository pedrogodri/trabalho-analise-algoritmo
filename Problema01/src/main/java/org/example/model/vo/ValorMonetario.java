package org.example.model.vo;

import org.example.exceptions.DadoInvalidoException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * Value Object que representa um valor monetário positivo.
 *
 * <p>Usa {@link BigDecimal} internamente para evitar imprecisões
 * de ponto flutuante em cálculos financeiros.</p>
 */
public class ValorMonetario {

    private static final String MENSAGEM_INVALIDO = "Valor do produto deve ser maior que zero";

    private final BigDecimal valor;

    public ValorMonetario(float valor) {
        this(BigDecimal.valueOf(valor));
    }

    public ValorMonetario(BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new DadoInvalidoException(MENSAGEM_INVALIDO);
        }
        this.valor = valor.setScale(2, RoundingMode.HALF_UP);
    }

    /** @return valor em reais como float (compatibilidade com cálculos existentes) */
    public float valor() {
        return valor.floatValue();
    }

    /** @return valor como {@link BigDecimal} para cálculos precisos */
    public BigDecimal valorExato() {
        return valor;
    }

    @Override
    public String toString() {
        return String.format("R$ %.2f", valor);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ValorMonetario that)) return false;
        return valor.compareTo(that.valor) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor.stripTrailingZeros());
    }
}
