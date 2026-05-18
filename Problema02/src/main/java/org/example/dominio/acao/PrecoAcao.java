package org.example.dominio.acao;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Representa o preço de uma ação no mercado.
 *
 * <p>Encapsula um {@link BigDecimal} com precisão de 2 casas decimais.
 * Imutável por design: toda operação de comparação retorna um booleano,
 * nunca expõe o valor interno diretamente.</p>
 *
 * <p>Implementa {@link Comparable} para permitir ordenação natural
 * (menor preço primeiro).</p>
 */
public final class PrecoAcao implements Comparable<PrecoAcao> {

    private final BigDecimal valor;

    public PrecoAcao(BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Preço da ação deve ser maior que zero.");
        }
        this.valor = valor.setScale(2, RoundingMode.HALF_UP);
    }

    public PrecoAcao(String valor) {
        this(new BigDecimal(valor));
    }

    /**
     * Verifica se este preço é maior ou igual ao preço informado.
     * Usado pelo combinador para validar elegibilidade de ordens:
     * uma compra é elegível quando seu preço >= preço de venda.
     */
    public boolean eMaiorOuIgualA(PrecoAcao outro) {
        return compareTo(outro) >= 0;
    }

    /**
     * Retorna uma representação formatada para exibição (ex: "R$ 35,00").
     */
    public String exibir() {
        return "R$ " + valor.toPlainString();
    }

    @Override
    public int compareTo(PrecoAcao outro) {
        return this.valor.compareTo(outro.valor);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PrecoAcao outro)) return false;
        return this.valor.compareTo(outro.valor) == 0;
    }

    @Override
    public int hashCode() {
        return valor.hashCode();
    }

    @Override
    public String toString() {
        return exibir();
    }
}
