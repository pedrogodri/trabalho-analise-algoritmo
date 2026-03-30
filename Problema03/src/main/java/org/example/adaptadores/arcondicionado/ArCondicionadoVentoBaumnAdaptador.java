package org.example.adaptadores.arcondicionado;

import br.furb.analise.algoritmos.ArCondicionadoVentoBaumn;
import org.example.excecoes.DispositivoNuloException;
import org.example.excecoes.FalhaDispositivoException;
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

    private static final String NOME = "Ar-condicionado VentoBaumn";

    private final ArCondicionadoVentoBaumn arCondicionado;

    /**
     * @param arCondicionado instância do AC VentoBaumn fornecida pela biblioteca do fabricante
     * @throws DispositivoNuloException se {@code arCondicionado} for nulo
     */
    public ArCondicionadoVentoBaumnAdaptador(ArCondicionadoVentoBaumn arCondicionado) {
        if (arCondicionado == null) throw new DispositivoNuloException(NOME + ": dispositivo não pode ser nulo.");
        this.arCondicionado = arCondicionado;
    }

    /**
     * Liga o ar-condicionado VentoBaumn.
     *
     * @throws FalhaDispositivoException se o dispositivo falhar ao ligar
     */
    @Override
    public void ligar() {
        try {
            arCondicionado.ligar();
            System.out.println("[" + NOME + "] Ligado com sucesso.");
        } catch (Exception e) {
            throw new FalhaDispositivoException("[" + NOME + "] Falha ao ligar: " + e.getMessage(), e);
        }
    }

    /**
     * Desliga o ar-condicionado VentoBaumn.
     *
     * @throws FalhaDispositivoException se o dispositivo falhar ao desligar
     */
    @Override
    public void desligar() {
        try {
            arCondicionado.desligar();
            System.out.println("[" + NOME + "] Desligado com sucesso.");
        } catch (Exception e) {
            throw new FalhaDispositivoException("[" + NOME + "] Falha ao desligar: " + e.getMessage(), e);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @throws FalhaDispositivoException se o aparelho estiver desligado ou temperatura já for 35°C
     */
    @Override
    public void aumentarTemperatura() {
        try {
            int novaTemp = arCondicionado.getTemperatura() + 1;
            arCondicionado.definirTemperatura(novaTemp);
            System.out.println("[" + NOME + "] Temperatura aumentada para " + novaTemp + "°C.");
        } catch (Exception e) {
            throw new FalhaDispositivoException("[" + NOME + "] Falha ao aumentar temperatura: " + e.getMessage(), e);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @throws FalhaDispositivoException se o aparelho estiver desligado ou temperatura já for 15°C
     */
    @Override
    public void diminuirTemperatura() {
        try {
            int novaTemp = arCondicionado.getTemperatura() - 1;
            arCondicionado.definirTemperatura(novaTemp);
            System.out.println("[" + NOME + "] Temperatura diminuída para " + novaTemp + "°C.");
        } catch (Exception e) {
            throw new FalhaDispositivoException("[" + NOME + "] Falha ao diminuir temperatura: " + e.getMessage(), e);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @throws FalhaDispositivoException se o aparelho estiver desligado ou temperatura fora de [15, 35]
     */
    @Override
    public void definirTemperatura(int temperatura) {
        try {
            arCondicionado.definirTemperatura(temperatura);
            System.out.println("[" + NOME + "] Temperatura definida para " + temperatura + "°C.");
        } catch (Exception e) {
            throw new FalhaDispositivoException(
                    "[" + NOME + "] Falha ao definir temperatura para " + temperatura + "°C: " + e.getMessage()
                    + " — verifique se o aparelho está ligado e se a temperatura está entre 15°C e 35°C.", e);
        }
    }
}
