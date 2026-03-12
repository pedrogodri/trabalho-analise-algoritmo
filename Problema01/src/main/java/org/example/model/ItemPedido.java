package org.example.model;

public class ItemPedido {
    private Produto produto;
    private int quantidade;

    public ItemPedido(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public float getSubtotal() {
        return produto.getValor() * quantidade;
    }

    public float getPesoTotal() {
        return produto.getPeso() * quantidade;
    }

    @Override
    public String toString() {
        return String.format("%s | Qtd: %d | Subtotal: R$ %.2f",
                produto.getNome(), quantidade, getSubtotal());
    }
}
