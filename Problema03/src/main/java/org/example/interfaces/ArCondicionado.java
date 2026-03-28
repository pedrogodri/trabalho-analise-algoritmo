package org.example.interfaces;

/**
 * Contrato universal para ar-condicionados inteligentes.
 *
 * <p>Abstrai as diferenças entre fabricantes (VentoBaumn possui {@code definirTemperatura(int)}
 * diretamente; GellaKaza só possui {@code aumentarTemperatura()} e {@code diminuirTemperatura()},
 * ajustando 1°C por vez) por trás de uma interface única com controle completo de temperatura.</p>
 *
 * <p>Padrão aplicado: <b>Adapter</b> — as implementações concretas adaptam as APIs
 * específicas de cada fabricante, incluindo a lógica de loop do GellaKaza para
 * atingir uma temperatura-alvo.</p>
 *
 * <p>Restrições comuns: temperatura permitida entre 15°C e 35°C para ambos os fabricantes.</p>
 */
public interface ArCondicionado {

    /**
     * Liga o ar-condicionado.
     */
    void ligar();

    /**
     * Desliga o ar-condicionado.
     */
    void desligar();

    /**
     * Aumenta a temperatura em 1°C.
     *
     * @throws IllegalArgumentException se a temperatura já estiver no limite máximo (35°C)
     */
    void aumentarTemperatura();

    /**
     * Diminui a temperatura em 1°C.
     *
     * @throws IllegalArgumentException se a temperatura já estiver no limite mínimo (15°C)
     */
    void diminuirTemperatura();

    /**
     * Define a temperatura diretamente para o valor informado.
     *
     * <p>Para o VentoBaumn, o aparelho deve estar ligado antes de chamar este método.
     * Para o GellaKaza, a temperatura é ajustada iterativamente via aumentar/diminuir.</p>
     *
     * @param temperatura temperatura desejada, entre 15°C e 35°C
     * @throws IllegalArgumentException se a temperatura estiver fora do intervalo permitido
     *                                   ou se o VentoBaumn estiver desligado
     */
    void definirTemperatura(int temperatura);
}
