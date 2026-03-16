package org.example.model;

import org.example.interfaces.IFormatoEntrega;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa um pedido da livraria.
 *
 * <p>Centraliza os itens selecionados pelo cliente e delega o cálculo
 * de frete à {@link IFormatoEntrega} fornecida (Strategy Pattern).</p>
 */
public class Pedido {

    private final List<ItemPedido> itens;

    public Pedido() {
        this.itens = new ArrayList<>();
    }

    /** Adiciona um item ao pedido. */
    public void adicionar(ItemPedido item) {
        itens.add(item);
    }

    /** Remove todos os itens do pedido. */
    public void limpar() {
        itens.clear();
    }

    /** @return {@code true} se o pedido não tiver itens */
    public boolean estaVazio() {
        return itens.isEmpty();
    }

    /** @return visão somente-leitura dos itens do pedido */
    public List<ItemPedido> itens() {
        return Collections.unmodifiableList(itens);
    }

    /**
     * Soma o peso de todos os itens considerando suas quantidades.
     *
     * @return peso total do pedido em quilogramas
     */
    public float pesoTotalEmKg() {
        return (float) itens.stream()
                .mapToDouble(ItemPedido::getPesoTotal)
                .sum();
    }

    /**
     * @return soma dos subtotais (preço x quantidade) de todos os itens
     */
    public double valorTotalProdutos() {
        return itens.stream().mapToDouble(ItemPedido::getSubtotal).sum();
    }

    /**
     * Delega o cálculo do frete à estratégia informada.
     *
     * @param estrategia modalidade de entrega escolhida
     * @return custo do frete em reais
     * @throws org.example.exceptions.EntregaNaoDisponivelException se a estratégia
     *         não aceitar o peso do pedido
     */
    public double calcularFrete(IFormatoEntrega estrategia) {
        return estrategia.calcular(pesoTotalEmKg());
    }
}
