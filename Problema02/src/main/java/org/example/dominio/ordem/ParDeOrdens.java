package org.example.dominio.ordem;

/**
 * Representa um par de ordens compatíveis encontrado pelo {@code CombinadorDeOrdens}.
 * Imutável; transporta compra e venda para o {@code LivroDeOrdens} executar.
 */
public final class ParDeOrdens {

    private final OrdemDeCompra ordemDeCompra;
    private final OrdemDeVenda ordemDeVenda;

    public ParDeOrdens(OrdemDeCompra ordemDeCompra, OrdemDeVenda ordemDeVenda) {
        this.ordemDeCompra = ordemDeCompra;
        this.ordemDeVenda = ordemDeVenda;
    }

    public OrdemDeCompra getOrdemDeCompra() {
        return ordemDeCompra;
    }

    public OrdemDeVenda getOrdemDeVenda() {
        return ordemDeVenda;
    }
}
