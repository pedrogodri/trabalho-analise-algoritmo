package org.example.adaptadores.lampada;

import br.furb.analise.algoritmos.LampadaShoyuMi;
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

    private final LampadaShoyuMi lampada;

    /**
     * @param lampada instância da lâmpada ShoyuMi fornecida pela biblioteca do fabricante
     */
    public LampadaShoyuMiAdaptador(LampadaShoyuMi lampada) {
        this.lampada = lampada;
    }

    /** {@inheritDoc} */
    @Override
    public void ligar() {
        lampada.ligar();
    }

    /** {@inheritDoc} */
    @Override
    public void desligar() {
        lampada.desligar();
    }
}
