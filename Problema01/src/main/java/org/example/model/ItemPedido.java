package org.example.model;

import org.example.model.vo.Quantidade;

/**
 * Associa um {@link Produto} a uma {@link org.example.model.vo.Quantidade} dentro do {@link Carrinho}.
 *
 * <p>Centraliza o cálculo de subtotal e peso total do item,
 * evitando duplicação dessa lógica nos consumidores.</p>
 */
public class ItemPedido {

    private final Produto produto;
    private final Quantidade quantidade;

    public ItemPedido(Produto produto, Quantidade quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    /** @return preço unitário multiplicado pela quantidade */
    public float getSubtotal() {
        return produto.getValor() * quantidade.valor();
    }

    /** @return peso unitário do produto multiplicado pela quantidade, em quilogramas */
    public float getPesoTotal() {
        return produto.getPeso() * quantidade.valor();
    }

    @Override
    public String toString() {
        return String.format("%s | Qtd: %s | Subtotal: R$ %.2f",
                produto.getNome(), quantidade, getSubtotal());
    }
}
