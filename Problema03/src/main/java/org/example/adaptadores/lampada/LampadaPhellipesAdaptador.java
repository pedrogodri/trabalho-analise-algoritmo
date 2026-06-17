package org.example.adaptadores.lampada;

import br.furb.analise.algoritmos.LampadaPhellipes;
import org.example.excecoes.DispositivoNuloException;
import org.example.excecoes.FalhaDispositivoException;
import org.example.interfaces.Lampada;

/**
 * Adaptador da {@link LampadaPhellipes} (fabricante Phellipes) para a interface universal {@link Lampada}.
 *
 * <p>A API do fabricante não possui {@code ligar()}/{@code desligar()} — apenas
 * {@code setIntensidade(int)} com valores de 0 (desligada) a 100 (totalmente ligada).
 * A adaptação mapeia:</p>
 * <ul>
 *   <li>{@code ligar()} → {@code setIntensidade(100)} (intensidade máxima)</li>
 *   <li>{@code desligar()} → {@code setIntensidade(0)} (intensidade zero)</li>
 * </ul>
 *
 * <p>Padrão aplicado: <b>Adapter (Objeto)</b> — transforma a semântica de intensidade
 * para a semântica booleana ligado/desligado exigida pela interface universal.</p>
 */
public final class LampadaPhellipesAdaptador implements Lampada {

    private static final int INTENSIDADE_LIGADA = 100;
    private static final int INTENSIDADE_DESLIGADA = 0;
    private static final String NOME = "Lâmpada Phellipes";

    private final LampadaPhellipes lampada;

    /**
     * @param lampada instância da lâmpada Phellipes fornecida pela biblioteca do fabricante
     * @throws DispositivoNuloException se {@code lampada} for nula
     */
    public LampadaPhellipesAdaptador(LampadaPhellipes lampada) {
        if (lampada == null) throw new DispositivoNuloException(NOME + ": dispositivo não pode ser nulo.");
        this.lampada = lampada;
    }

    /**
     * Liga a lâmpada definindo a intensidade máxima (100).
     *
     * @throws FalhaDispositivoException se o dispositivo falhar ao ajustar intensidade
     */
    @Override
    public void ligar() {
        try {
            lampada.setIntensidade(INTENSIDADE_LIGADA);
            System.out.println("[" + NOME + "] Ligada com intensidade máxima (" + INTENSIDADE_LIGADA + ").");
        } catch (Exception e) {
            throw new FalhaDispositivoException("[" + NOME + "] Falha ao ligar (setIntensidade=" + INTENSIDADE_LIGADA + "): " + e.getMessage(), e);
        }
    }

    /**
     * Desliga a lâmpada definindo a intensidade zero (0).
     *
     * @throws FalhaDispositivoException se o dispositivo falhar ao ajustar intensidade
     */
    @Override
    public void desligar() {
        try {
            lampada.setIntensidade(INTENSIDADE_DESLIGADA);
            System.out.println("[" + NOME + "] Desligada (intensidade zerada).");
        } catch (Exception e) {
            throw new FalhaDispositivoException("[" + NOME + "] Falha ao desligar (setIntensidade=" + INTENSIDADE_DESLIGADA + "): " + e.getMessage(), e);
        }
    }
}
