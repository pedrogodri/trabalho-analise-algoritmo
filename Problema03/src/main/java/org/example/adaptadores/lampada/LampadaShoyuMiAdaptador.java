package org.example.adaptadores.lampada;

import br.furb.analise.algoritmos.LampadaShoyuMi;
import org.example.excecoes.DispositivoNuloException;
import org.example.excecoes.FalhaDispositivoException;
import org.example.interfaces.Lampada;

/**
 * Adaptador da {@link LampadaShoyuMi} (fabricante ShoyuMi) para a interface universal {@link Lampada}.
 *
 * <p>A API do fabricante já oferece {@code ligar()} e {@code desligar()} diretamente,
 * portanto a adaptação é uma delegação direta sem transformação de comportamento.</p>
 *
 * <p>Padrão aplicado: <b>Adapter (Objeto)</b> — encapsula a instância da lâmpada do fabricante
 * e delega as chamadas sem modificar o comportamento original.</p>
 */
public final class LampadaShoyuMiAdaptador implements Lampada {

    private static final String NOME = "Lâmpada ShoyuMi";

    private final LampadaShoyuMi lampada;

    /**
     * @param lampada instância da lâmpada ShoyuMi fornecida pela biblioteca do fabricante
     * @throws DispositivoNuloException se {@code lampada} for nula
     */
    public LampadaShoyuMiAdaptador(LampadaShoyuMi lampada) {
        if (lampada == null) throw new DispositivoNuloException(NOME + ": dispositivo não pode ser nulo.");
        this.lampada = lampada;
    }

    /**
     * Liga a lâmpada ShoyuMi.
     *
     * @throws FalhaDispositivoException se o dispositivo falhar ao ligar
     */
    @Override
    public void ligar() {
        try {
            lampada.ligar();
            System.out.println("[" + NOME + "] Ligada com sucesso.");
        } catch (Exception e) {
            throw new FalhaDispositivoException("[" + NOME + "] Falha ao ligar: " + e.getMessage(), e);
        }
    }

    /**
     * Desliga a lâmpada ShoyuMi.
     *
     * @throws FalhaDispositivoException se o dispositivo falhar ao desligar
     */
    @Override
    public void desligar() {
        try {
            lampada.desligar();
            System.out.println("[" + NOME + "] Desligada com sucesso.");
        } catch (Exception e) {
            throw new FalhaDispositivoException("[" + NOME + "] Falha ao desligar: " + e.getMessage(), e);
        }
    }
}
