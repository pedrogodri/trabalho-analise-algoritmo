package org.example.model.vo;

import org.example.exceptions.DadoInvalidoException;

import java.util.Objects;

/**
 * Value Object que representa o peso de um produto em quilogramas.
 *
 * <p>Garante que o peso seja sempre positivo no momento da criação (fail-fast).</p>
 */
public class PesoEmKg {

    private static final String MENSAGEM_INVALIDO = "Peso do produto deve ser maior que zero";

    private final float valor;

    public PesoEmKg(float valor) {
        if (valor <= 0) {
            throw new DadoInvalidoException(MENSAGEM_INVALIDO);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PesoEmKg that)) return false;
        return Float.compare(valor, that.valor) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }
}
