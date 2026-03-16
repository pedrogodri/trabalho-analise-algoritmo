package org.example.model;

import org.example.model.vo.Quantidade;

/**
 * Associa um {@link Produto} a uma {@link Quantidade} dentro de um {@link Pedido}.
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

    /** @return produto associado a este item */
    public Produto getProduto() {
        return produto;
    }

    /** @return quantidade solicitada */
    public Quantidade getQuantidade() {
        return quantidade;
    }

    /**
     * Calcula o subtotal usando o {@link org.example.model.vo.ValorMonetario} do produto
     * para maior precisão.
     *
     * @return preço unitário multiplicado pela quantidade, em reais
     */
    public double getSubtotal() {
        return produto.getValorMonetario().valorExato()
                .multiply(java.math.BigDecimal.valueOf(quantidade.valor()))
                .doubleValue();
    }

    /**
     * Calcula o peso total usando o {@link org.example.model.vo.PesoEmKg} do produto.
     *
     * @return peso unitário do produto multiplicado pela quantidade, em quilogramas
     */
    public double getPesoTotal() {
        return produto.getPesoEmKg().valor() * quantidade.valor();
    }

    @Override
    public String toString() {
        return String.format("%s | Qtd: %s | Subtotal: R$ %.2f",
                produto.getNomeProduto(), quantidade, getSubtotal());
    }
}
