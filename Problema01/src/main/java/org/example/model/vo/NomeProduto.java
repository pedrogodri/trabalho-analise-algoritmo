package org.example.model.vo;

import org.example.exceptions.DadoInvalidoException;
import org.example.util.Validacao;

import java.util.Objects;

/**
 * Value Object que representa o nome de um produto.
 *
 * <p>Garante que o nome seja não-nulo, não-vazio e com no máximo
 * {@value #TAMANHO_MAXIMO} caracteres após normalização (trim).</p>
 */
public class NomeProduto {

    private static final int TAMANHO_MAXIMO = 100;
    private static final String CAMPO = "Nome do produto";

    private final String valor;

    public NomeProduto(String valor) {
        String normalizado = Validacao.validarObrigatorio(valor, CAMPO);
        validarTamanho(normalizado);
        this.valor = normalizado;
    }

    private void validarTamanho(String normalizado) {
        if (normalizado.length() > TAMANHO_MAXIMO) {
            throw new DadoInvalidoException(CAMPO + " não pode ter mais de " + TAMANHO_MAXIMO + " caracteres");
        }
    }

    @Override
    public String toString() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NomeProduto that)) return false;
        return Objects.equals(valor, that.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }
}
