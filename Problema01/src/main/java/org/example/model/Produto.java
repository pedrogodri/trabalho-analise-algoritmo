package org.example.model;

import org.example.model.vo.NomeProduto;
import org.example.model.vo.PesoEmKg;
import org.example.model.vo.ValorMonetario;

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

    /** @return nome do livro como String */
    public String getNome() {
        return nome.toString();
    }

    /** @return preço de venda em reais */
    public float getValor() {
        return valor.valor();
    }

    /** @return peso do livro em quilogramas */
    public float getPeso() {
        return peso.valor();
    }
}
