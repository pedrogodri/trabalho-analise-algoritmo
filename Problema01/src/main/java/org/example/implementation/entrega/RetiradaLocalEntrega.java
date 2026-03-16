package org.example.implementation.entrega;

import org.example.interfaces.IFormatoEntrega;

/**
 * Estratégia de entrega por retirada na própria loja.
 *
 * <p>Não há custo de frete independentemente do peso do pedido.
 * O cliente é responsável por buscar o pedido no endereço da livraria.</p>
 */
public class RetiradaLocalEntrega implements IFormatoEntrega {

    /**
     * Retorna zero, pois a retirada no local não possui custo de frete.
     *
     * @param pesoEmKg peso do pedido (não utilizado nesta modalidade)
     * @return sempre {@code 0.00}
     */
    @Override
    public double calcular(double pesoEmKg) {
        return 0.00;
    }

    @Override
    public String descricao() {
        return "Retirada no Local";
    }
}
