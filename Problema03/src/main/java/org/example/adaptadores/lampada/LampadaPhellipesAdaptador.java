package org.example.adaptadores.lampada;

import br.furb.analise.algoritmos.LampadaPhellipes;
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

    private final LampadaPhellipes lampada;

    /**
     * @param lampada instância da lâmpada Phellipes fornecida pela biblioteca do fabricante
     */
    public LampadaPhellipesAdaptador(LampadaPhellipes lampada) {
        this.lampada = lampada;
    }

    /**
     * Liga a lâmpada definindo a intensidade máxima (100).
     */
    @Override
    public void ligar() {
        lampada.setIntensidade(INTENSIDADE_LIGADA);
    }

    /**
     * Desliga a lâmpada definindo a intensidade zero (0).
     */
    @Override
    public void desligar() {
        lampada.setIntensidade(INTENSIDADE_DESLIGADA);
    }
}
