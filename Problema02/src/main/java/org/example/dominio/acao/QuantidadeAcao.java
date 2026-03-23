package org.example.dominio.acao;

/**
 * Representa a quantidade de ações em uma ordem ou carteira.
 *
 * <p>Imutável. Toda quantidade deve ser positiva; o construtor
 * rejeita valores zero ou negativos.</p>
 */
public final class QuantidadeAcao implements Comparable<QuantidadeAcao> {

    private final int quantidade;

    public QuantidadeAcao(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade de ações deve ser maior que zero.");
        }
        this.quantidade = quantidade;
    }

    /** Retorna nova quantidade somando a informada. */
    public QuantidadeAcao adicionar(QuantidadeAcao outra) {
        return new QuantidadeAcao(this.quantidade + outra.quantidade);
    }

    /**
     * Retorna nova quantidade subtraindo a informada.
     * Lança exceção se o resultado for zero ou negativo.
     */
    public QuantidadeAcao subtrair(QuantidadeAcao outra) {
        int resultado = this.quantidade - outra.quantidade;
        if (resultado <= 0) {
            throw new IllegalArgumentException("Quantidade insuficiente para subtrair.");
        }
        return new QuantidadeAcao(resultado);
    }

    /** Retorna a menor das duas quantidades. Útil para execução parcial de ordens. */
    public QuantidadeAcao minimo(QuantidadeAcao outra) {
        return new QuantidadeAcao(Math.min(this.quantidade, outra.quantidade));
    }

    public boolean eMaiorOuIgualA(QuantidadeAcao outra) {
        return compareTo(outra) >= 0;
    }

    public boolean eIgualA(QuantidadeAcao outra) {
        return equals(outra);
    }

    public int getQuantidade() {
        return quantidade;
    }

    @Override
    public int compareTo(QuantidadeAcao outra) {
        return Integer.compare(this.quantidade, outra.quantidade);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof QuantidadeAcao outra)) return false;
        return this.quantidade == outra.quantidade;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(quantidade);
    }

    @Override
    public String toString() {
        return String.valueOf(quantidade);
    }
}
