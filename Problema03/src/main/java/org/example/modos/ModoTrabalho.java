package org.example.modos;

import org.example.interfaces.Modo;
import org.example.models.ArsCondicionados;
import org.example.models.Lampadas;
import org.example.models.Persianas;

/**
 * Modo Trabalho — configura a casa para o período produtivo.
 *
 * <p>Ações executadas ao ativar:</p>
 * <ol>
 *   <li>Liga todas as lâmpadas</li>
 *   <li>Abre todas as persianas</li>
 *   <li>Liga todos os ar-condicionados</li>
 *   <li>Define temperatura de conforto ({@value #TEMPERATURA_TRABALHO}°C) em todos os ACs</li>
 * </ol>
 *
 * <p><b>Ordem importante</b>: os ACs devem ser ligados <em>antes</em> de definir a temperatura,
 * pois o VentoBaumn lança exceção ao tentar definir temperatura com o aparelho desligado.</p>
 *
 * <p>Padrão aplicado: <b>Strategy</b> — implementação concreta de {@link Modo}
 * que encapsula as regras de negócio do modo trabalho.</p>
 */
public final class ModoTrabalho implements Modo {

    private static final int TEMPERATURA_TRABALHO = 25;

    /**
     * {@inheritDoc}
     *
     * <p>Ordem: lâmpadas ligadas → persianas abertas → ACs ligados → temperatura definida em
     * {@value #TEMPERATURA_TRABALHO}°C.</p>
     */
    @Override
    public void ativar(Lampadas lampadas, Persianas persianas, ArsCondicionados arsCondicionados) {
        lampadas.ligarTodas();
        persianas.abrirTodas();
        arsCondicionados.ligarTodos();
        arsCondicionados.definirTemperaturaEmTodos(TEMPERATURA_TRABALHO);
    }
}
