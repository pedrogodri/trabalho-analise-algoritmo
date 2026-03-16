package org.example.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Coleção de itens selecionados pelo cliente antes de finalizar o pedido.
 *
 * <p>Aplica o princípio de <b>First Class Collection</b> (Object Calisthenics):
 * encapsula a lista de {@link ItemPedido} e centraliza operações como
 * cálculo do valor total, evitando que a lógica fique espalhada pelos consumidores.</p>
 */
public class Carrinho {

    private final List<ItemPedido> itens;

    public Carrinho() {
        this.itens = new ArrayList<>();
    }

    /** Adiciona um item ao carrinho. */
    public void adicionar(ItemPedido item) {
        itens.add(item);
    }

    /** Remove todos os itens do carrinho (utilizado após finalização de um pedido). */
    public void limpar() {
        itens.clear();
    }

    public boolean estaVazio() {
        return itens.isEmpty();
    }

    /**
     * @return visão somente-leitura dos itens do carrinho
     */
    public List<ItemPedido> itens() {
        return Collections.unmodifiableList(itens);
    }

    /**
     * @return soma dos subtotais de todos os itens do carrinho em reais
     */
    public double valorTotal() {
        return itens.stream().mapToDouble(ItemPedido::getSubtotal).sum();
    }
}
