package org.example.model;

import org.example.interfaces.IFormatoEntrega;

/**
 * Representa um pedido da livraria composto pelos itens do {@link Carrinho}.
 *
 * <p>Delega o cálculo de frete à {@link EntregaStrategy} fornecida,
 * sem depender de qual modalidade está sendo usada (Strategy Pattern).</p>
 */
public class Pedido {

    private final Carrinho carrinho;

    /**
     * @param carrinho carrinho com os itens do pedido
     */
    public Pedido(Carrinho carrinho) {
        this.carrinho = carrinho;
    }

    /**
     * Soma o peso de todos os itens considerando suas quantidades.
     *
     * @return peso total do pedido em quilogramas
     */
    public float pesoTotalEmKg() {
        return (float) carrinho.itens().stream()
                .mapToDouble(ItemPedido::getPesoTotal)
                .sum();
    }

    /**
     * @return soma dos subtotais (preço x quantidade) de todos os itens
     */
    public double valorTotalProdutos() {
        return carrinho.valorTotal();
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
