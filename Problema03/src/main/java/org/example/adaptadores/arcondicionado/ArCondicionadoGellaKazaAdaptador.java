package org.example.adaptadores.arcondicionado;

import br.furb.analise.algoritmos.ArCondicionadoGellaKaza;
import org.example.interfaces.ArCondicionado;

/**
 * Adaptador do {@link ArCondicionadoGellaKaza} (fabricante GellaKaza) para a interface
 * universal {@link ArCondicionado}.
 *
 * <p>A API do fabricante usa {@code ativar()}/{@code desativar()} no lugar de ligar/desligar,
 * e não possui {@code definirTemperatura(int)} — apenas {@code aumentarTemperatura()} e
 * {@code diminuirTemperatura()}, que ajustam 1°C por vez.</p>
 *
 * <p>Para implementar {@code definirTemperatura(int)}, o adaptador itera chamando
 * {@code aumentarTemperatura()} ou {@code diminuirTemperatura()} até atingir o valor alvo.
 * Isso é o único caminho possível dado que o dispositivo não expõe um setter direto.</p>
 *
 * <p><b>Restrições do fabricante</b> (propagadas sem tratamento — são regras de domínio):</p>
 * <ul>
 *   <li>Temperatura permitida: entre 15°C e 35°C</li>
 *   <li>Temperatura inicial ao ser ativado: 28°C</li>
 *   <li>{@code aumentarTemperatura()} lança {@link IllegalArgumentException} se já estiver em 35°C</li>
 *   <li>{@code diminuirTemperatura()} lança {@link IllegalArgumentException} se já estiver em 15°C</li>
 * </ul>
 *
 * <p>Padrão aplicado: <b>Adapter (Objeto)</b> — além de adaptar a API, implementa
 * {@code definirTemperatura} por iteração incremental, único caminho viável com esta API.</p>
 */
public final class ArCondicionadoGellaKazaAdaptador implements ArCondicionado {

    private final ArCondicionadoGellaKaza arCondicionado;

    /**
     * @param arCondicionado instância do AC GellaKaza fornecida pela biblioteca do fabricante
     */
    public ArCondicionadoGellaKazaAdaptador(ArCondicionadoGellaKaza arCondicionado) {
        this.arCondicionado = arCondicionado;
    }

    /**
     * Liga o ar-condicionado (chama {@code ativar()} do fabricante).
     */
    @Override
    public void ligar() {
        arCondicionado.ativar();
    }

    /**
     * Desliga o ar-condicionado (chama {@code desativar()} do fabricante).
     */
    @Override
    public void desligar() {
        arCondicionado.desativar();
    }

    /**
     * {@inheritDoc}
     *
     * @throws IllegalArgumentException se a temperatura já estiver em 35°C
     */
    @Override
    public void aumentarTemperatura() {
        arCondicionado.aumentarTemperatura();
    }

    /**
     * {@inheritDoc}
     *
     * @throws IllegalArgumentException se a temperatura já estiver em 15°C
     */
    @Override
    public void diminuirTemperatura() {
        arCondicionado.diminuirTemperatura();
    }

    /**
     * {@inheritDoc}
     *
     * <p>Implementado por iteração: chama {@code aumentarTemperatura()} ou
     * {@code diminuirTemperatura()} repetidamente até atingir a temperatura alvo.
     * Isso é necessário pois o GellaKaza não oferece definição direta de temperatura.</p>
     *
     * @throws IllegalArgumentException se {@code temperatura} estiver fora de [15, 35],
     *                                   propagada pelo fabricante ao atingir o limite
     */
    @Override
    public void definirTemperatura(int temperatura) {
        while (arCondicionado.getTemperatura() < temperatura) {
            arCondicionado.aumentarTemperatura();
        }
        while (arCondicionado.getTemperatura() > temperatura) {
            arCondicionado.diminuirTemperatura();
        }
    }
}
