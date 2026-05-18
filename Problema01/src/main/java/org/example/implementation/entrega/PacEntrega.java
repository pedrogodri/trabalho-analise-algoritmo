package org.example.implementation.entrega;

import org.example.exceptions.EntregaNaoDisponivelException;
import org.example.interfaces.IFormatoEntrega;
import org.example.model.vo.PesoEmKg;

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
public class PacEntrega implements IFormatoEntrega {

    private static final double LIMITE_1KG = 1.0;
    private static final double LIMITE_2KG = 2.0;

    /**
     * Calcula o frete PAC com base no peso do pedido.
     *
     * @param pesoEmKg peso total do pedido encapsulado no Value Object
     * @return R$ 10,00 para até 1 kg; R$ 15,00 para até 2 kg
     * @throws EntregaNaoDisponivelException se o peso ultrapassar 2 kg
     */
    @Override
    public double calcular(PesoEmKg pesoEmKg) {
        double peso = pesoEmKg.valor();
        if (peso > LIMITE_2KG) {
            throw new EntregaNaoDisponivelException("PAC nao aceita pedidos acima de 2kg");
        }
        if (peso <= LIMITE_1KG) {
            return 10.00;
        }
        return 15.00;
    }

    @Override
    public String descricao() {
        return "Encomenda PAC";
    }
}
