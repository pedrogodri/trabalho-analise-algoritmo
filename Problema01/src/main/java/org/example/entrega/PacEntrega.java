package org.example.entrega;

import org.example.exceptions.EntregaNaoDisponivelException;

/**
 * Estratégia de entrega via encomenda PAC (Postagem Aéreo Comercial).
 *
 * <p>Tabela de preços:</p>
 * <ul>
 *   <li>Até 1 kg → R$ 10,00</li>
 *   <li>De 1 kg a 2 kg → R$ 15,00</li>
 *   <li>Acima de 2 kg → modalidade indisponível</li>
 * </ul>
 */
public class PacEntrega implements EstrategiaEntrega {

    private static final float LIMITE_1KG = 1.0f;
    private static final float LIMITE_2KG = 2.0f;

    /**
     * Calcula o frete PAC com base no peso do pedido.
     *
     * @param pesoEmKg peso total do pedido em quilogramas
     * @return R$ 10,00 para até 1 kg; R$ 15,00 para até 2 kg
     * @throws EntregaNaoDisponivelException se o peso ultrapassar 2 kg
     */
    @Override
    public double calcular(float pesoEmKg) {
        if (pesoEmKg > LIMITE_2KG) {
            throw new EntregaNaoDisponivelException("PAC nao aceita pedidos acima de 2kg");
        }
        if (pesoEmKg <= LIMITE_1KG) {
            return 10.00;
        }
        return 15.00;
    }

    @Override
    public String descricao() {
        return "Encomenda PAC";
    }
}
