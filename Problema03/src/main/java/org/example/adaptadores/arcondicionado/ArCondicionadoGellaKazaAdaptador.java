package org.example.adaptadores.arcondicionado;

import br.furb.analise.algoritmos.ArCondicionadoGellaKaza;
import org.example.excecoes.DispositivoNuloException;
import org.example.excecoes.FalhaDispositivoException;
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

    private static final String NOME = "Ar-condicionado GellaKaza";

    private final ArCondicionadoGellaKaza arCondicionado;

    /**
     * @param arCondicionado instância do AC GellaKaza fornecida pela biblioteca do fabricante
     * @throws DispositivoNuloException se {@code arCondicionado} for nulo
     */
    public ArCondicionadoGellaKazaAdaptador(ArCondicionadoGellaKaza arCondicionado) {
        if (arCondicionado == null) throw new DispositivoNuloException(NOME + ": dispositivo não pode ser nulo.");
        this.arCondicionado = arCondicionado;
    }

    /**
     * Liga o ar-condicionado (chama {@code ativar()} do fabricante).
     *
     * @throws FalhaDispositivoException se o dispositivo falhar ao ativar
     */
    @Override
    public void ligar() {
        try {
            arCondicionado.ativar();
            System.out.println("[" + NOME + "] Ligado (ativado) com sucesso.");
        } catch (Exception e) {
            throw new FalhaDispositivoException("[" + NOME + "] Falha ao ligar (ativar): " + e.getMessage(), e);
        }
    }

    /**
     * Desliga o ar-condicionado (chama {@code desativar()} do fabricante).
     *
     * @throws FalhaDispositivoException se o dispositivo falhar ao desativar
     */
    @Override
    public void desligar() {
        try {
            arCondicionado.desativar();
            System.out.println("[" + NOME + "] Desligado (desativado) com sucesso.");
        } catch (Exception e) {
            throw new FalhaDispositivoException("[" + NOME + "] Falha ao desligar (desativar): " + e.getMessage(), e);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @throws FalhaDispositivoException se a temperatura já estiver em 35°C
     */
    @Override
    public void aumentarTemperatura() {
        try {
            arCondicionado.aumentarTemperatura();
            System.out.println("[" + NOME + "] Temperatura aumentada para " + arCondicionado.getTemperatura() + "°C.");
        } catch (Exception e) {
            throw new FalhaDispositivoException("[" + NOME + "] Falha ao aumentar temperatura: " + e.getMessage()
                    + " — temperatura máxima permitida é 35°C.", e);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @throws FalhaDispositivoException se a temperatura já estiver em 15°C
     */
    @Override
    public void diminuirTemperatura() {
        try {
            arCondicionado.diminuirTemperatura();
            System.out.println("[" + NOME + "] Temperatura diminuída para " + arCondicionado.getTemperatura() + "°C.");
        } catch (Exception e) {
            throw new FalhaDispositivoException("[" + NOME + "] Falha ao diminuir temperatura: " + e.getMessage()
                    + " — temperatura mínima permitida é 15°C.", e);
        }
    }

    /**
     * {@inheritDoc}
     *
     * <p>Implementado por iteração: chama {@code aumentarTemperatura()} ou
     * {@code diminuirTemperatura()} repetidamente até atingir a temperatura alvo.
     * Isso é necessário pois o GellaKaza não oferece definição direta de temperatura.</p>
     *
     * @throws FalhaDispositivoException se {@code temperatura} estiver fora de [15, 35]
     */
    @Override
    public void definirTemperatura(int temperatura) {
        try {
            int tempAtual = arCondicionado.getTemperatura();
            System.out.println("[" + NOME + "] Ajustando temperatura de " + tempAtual + "°C para " + temperatura + "°C (iterativo)...");
            while (arCondicionado.getTemperatura() < temperatura) {
                arCondicionado.aumentarTemperatura();
            }
            while (arCondicionado.getTemperatura() > temperatura) {
                arCondicionado.diminuirTemperatura();
            }
            System.out.println("[" + NOME + "] Temperatura definida para " + temperatura + "°C.");
        } catch (Exception e) {
            throw new FalhaDispositivoException(
                    "[" + NOME + "] Falha ao definir temperatura para " + temperatura + "°C: " + e.getMessage()
                    + " — temperatura deve estar entre 15°C e 35°C.", e);
        }
    }
}
