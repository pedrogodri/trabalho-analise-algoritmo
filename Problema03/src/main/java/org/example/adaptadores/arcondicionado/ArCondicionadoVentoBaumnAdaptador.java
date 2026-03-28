package org.example.adaptadores.arcondicionado;

import br.furb.analise.algoritmos.ArCondicionadoVentoBaumn;
import org.example.interfaces.ArCondicionado;

/**
 * Adaptador do {@link ArCondicionadoVentoBaumn} (fabricante VentoBaumn) para a interface
 * universal {@link ArCondicionado}.
 *
 * <p>A API do fabricante oferece {@code ligar()}, {@code desligar()} e {@code definirTemperatura(int)}
 * diretamente, mas não possui {@code aumentarTemperatura()} e {@code diminuirTemperatura()}.
 * Esses métodos são implementados pelo adaptador usando {@code definirTemperatura(getTemperatura() ± 1)}.</p>
 *
 * <p><b>Restrições do fabricante</b> (propagadas sem tratamento — são regras de domínio do dispositivo):</p>
 * <ul>
 *   <li>{@code definirTemperatura(int)} lança {@link IllegalArgumentException} se o aparelho estiver desligado</li>
 *   <li>Temperatura permitida: entre 15°C e 35°C</li>
 *   <li>Temperatura inicial: 24°C</li>
 * </ul>
 *
 * <p>Padrão aplicado: <b>Adapter (Objeto)</b> — delega as operações nativas e implementa
 * aumentar/diminuir sobre a API de definição direta de temperatura.</p>
 */
public final class ArCondicionadoVentoBaumnAdaptador implements ArCondicionado {

    private final ArCondicionadoVentoBaumn arCondicionado;

    /**
     * @param arCondicionado instância do AC VentoBaumn fornecida pela biblioteca do fabricante
     */
    public ArCondicionadoVentoBaumnAdaptador(ArCondicionadoVentoBaumn arCondicionado) {
        this.arCondicionado = arCondicionado;
    }

    /** {@inheritDoc} */
    @Override
    public void ligar() {
        arCondicionado.ligar();
    }

    /** {@inheritDoc} */
    @Override
    public void desligar() {
        arCondicionado.desligar();
    }

    /**
     * {@inheritDoc}
     *
     * @throws IllegalArgumentException se o aparelho estiver desligado ou temperatura já for 35°C
     */
    @Override
    public void aumentarTemperatura() {
        arCondicionado.definirTemperatura(arCondicionado.getTemperatura() + 1);
    }

    /**
     * {@inheritDoc}
     *
     * @throws IllegalArgumentException se o aparelho estiver desligado ou temperatura já for 15°C
     */
    @Override
    public void diminuirTemperatura() {
        arCondicionado.definirTemperatura(arCondicionado.getTemperatura() - 1);
    }

    /**
     * {@inheritDoc}
     *
     * @throws IllegalArgumentException se o aparelho estiver desligado ou temperatura fora de [15, 35]
     */
    @Override
    public void definirTemperatura(int temperatura) {
        arCondicionado.definirTemperatura(temperatura);
    }
}
