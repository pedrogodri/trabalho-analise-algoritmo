package org.example.model;

import org.example.model.vo.NomeProduto;
import org.example.model.vo.PesoEmKg;
import org.example.model.vo.ValorMonetario;

import java.util.Objects;

/**
 * Representa um livro disponível no catálogo da livraria.
 *
 * <p>Utiliza Value Objects para garantir que nome, valor e peso
 * sejam sempre válidos no momento da criação (fail-fast).</p>
 */
public class Produto {

    private final NomeProduto nome;
    private final ValorMonetario valor;
    private final PesoEmKg peso;

    /**
     * @param nome  nome do livro — não pode ser vazio
     * @param valor preço de venda — deve ser maior que zero
     * @param peso  peso físico — deve ser maior que zero
     */
    public Produto(NomeProduto nome, ValorMonetario valor, PesoEmKg peso) {
        this.nome = nome;
        this.valor = valor;
        this.peso = peso;
    }

    /** @return Value Object do nome */
    public NomeProduto getNomeProduto() {
        return nome;
    }

    /** @return Value Object do valor monetário, para cálculos precisos */
    public ValorMonetario getValorMonetario() {
        return valor;
    }

    /** @return Value Object do peso, para lógica de frete */
    public PesoEmKg getPesoEmKg() {
        return peso;
    }

    @Override
    public String toString() {
        return nome + " | " + valor + " | " + peso;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Produto that)) return false;
        return Objects.equals(nome, that.nome)
                && Objects.equals(valor, that.valor)
                && Objects.equals(peso, that.peso);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, valor, peso);
    }
}


