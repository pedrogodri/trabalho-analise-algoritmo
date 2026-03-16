package org.example.interfaces;

import org.example.implementation.entrega.PacEntrega;
import org.example.implementation.entrega.RetiradaLocalEntrega;
import org.example.implementation.entrega.SedexEntrega;

/**
 * Define o contrato para as modalidades de entrega da livraria.
 *
 * <p>Implementa o padrão <b>Strategy</b>: cada modalidade encapsula sua própria
 * regra de cálculo de frete, permitindo que {@link org.example.model.Pedido}
 * calcule o custo sem conhecer os detalhes de cada tipo de entrega.</p>
 *
 * <p>Modalidades disponíveis:</p>
 * <ul>
 *   <li>{@link PacEntrega} — aceita até 2 kg</li>
 *   <li>{@link SedexEntrega} — aceita qualquer peso, com custo variável</li>
 *   <li>{@link RetiradaLocalEntrega} — sem custo</li>
 * </ul>
 */
public interface IFormatoEntrega {

    /**
     * Calcula o custo de frete para o peso informado.
     *
     * @param pesoEmKg peso total do pedido em quilogramas
     * @return valor do frete em reais
     * @throws org.example.exceptions.EntregaNaoDisponivelException se a modalidade
     *         não aceitar o peso informado
     */
    double calcular(float pesoEmKg);

    /**
     * Retorna o nome da modalidade de entrega para exibição ao usuário.
     *
     * @return descrição legível da modalidade (ex: "Encomenda PAC")
     */
    String descricao();
}
