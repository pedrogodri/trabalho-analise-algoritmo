package org.example.model.vo;

/**
 * Value Object opcional que representa o complemento de um endereço (ex: "Apto 4", "Bloco B").
 *
 * <p>Diferentemente dos demais VOs de endereço, aceita valor vazio ou nulo,
 * pois o complemento não é obrigatório. Use {@link #presente()} para verificar
 * se há valor antes de exibir.</p>
 */
public class Complemento {

    private final String valor;

    public Complemento(String valor) {
        this.valor = valor == null ? "" : valor.trim();
    }

    /** @return {@code true} se o complemento não for vazio ou apenas espaços */
    public boolean presente() {
        return !valor.isBlank();
    }

    @Override
    public String toString() {
        return valor;
    }
}
