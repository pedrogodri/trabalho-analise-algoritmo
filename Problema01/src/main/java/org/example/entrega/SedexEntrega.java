package org.example.entrega;

/**
 * Estratégia de entrega via Sedex (Serviço de Encomenda Expressa).
 *
 * <p>Tabela de preços:</p>
 * <ul>
 *   <li>Até 500 g → R$ 12,50</li>
 *   <li>De 500 g a 1 kg → R$ 20,00</li>
 *   <li>Acima de 1 kg → R$ 46,50 + R$ 1,50 por cada 100 g adicionais
 *       (frações de 100 g são arredondadas para cima)</li>
 * </ul>
 */
public class SedexEntrega implements EstrategiaEntrega {

    private static final float LIMITE_500G = 0.5f;
    private static final float LIMITE_1KG = 1.0f;
    private static final double CUSTO_BASE_ACIMA_1KG = 46.50;
    private static final double CUSTO_POR_100G_ADICIONAL = 1.50;

    /**
     * Calcula o frete Sedex com base no peso do pedido.
     *
     * @param pesoEmKg peso total do pedido em quilogramas
     * @return valor do frete calculado conforme a faixa de peso
     */
    @Override
    public double calcular(float pesoEmKg) {
        if (pesoEmKg <= LIMITE_500G) {
            return 12.50;
        }
        if (pesoEmKg <= LIMITE_1KG) {
            return 20.00;
        }
        return calcularAcimaDe1Kg(pesoEmKg);
    }

    /**
     * Calcula o frete para pedidos acima de 1 kg.
     *
     * <p>Converte para gramas para evitar imprecisão de ponto flutuante
     * no cálculo dos grupos de 100 g.</p>
     *
     * @param pesoEmKg peso garantidamente maior que 1 kg
     * @return custo base somado ao valor dos grupos de 100 g adicionais
     */
    private double calcularAcimaDe1Kg(float pesoEmKg) {
        int pesoEmGramas = Math.round(pesoEmKg * 1000);
        int gramasAcimaDe1Kg = pesoEmGramas - 1000;
        int gruposDe100g = (int) Math.ceil(gramasAcimaDe1Kg / 100.0);
        return CUSTO_BASE_ACIMA_1KG + CUSTO_POR_100G_ADICIONAL * gruposDe100g;
    }

    @Override
    public String descricao() {
        return "Encomenda Sedex";
    }
}
