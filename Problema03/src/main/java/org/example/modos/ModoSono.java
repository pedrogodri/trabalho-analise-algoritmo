package org.example.modos;

import org.example.interfaces.Modo;
import org.example.models.ArsCondicionados;
import org.example.models.Lampadas;
import org.example.models.Persianas;

/**
 * Modo Sono — prepara a casa para o período de descanso.
 *
 * <p>Ações executadas ao ativar:</p>
 * <ol>
 *   <li>Desliga todas as lâmpadas</li>
 *   <li>Fecha todas as persianas</li>
 *   <li>Desliga todos os ar-condicionados</li>
 * </ol>
 *
 * <p>Padrão aplicado: <b>Strategy</b> — implementação concreta de {@link Modo}
 * que encapsula as regras de negócio do modo sono.</p>
 */
public final class ModoSono implements Modo {

    /**
     * {@inheritDoc}
     *
     * <p>Ordem: lâmpadas desligadas → persianas fechadas → ACs desligados.</p>
     */
    @Override
    public void ativar(Lampadas lampadas, Persianas persianas, ArsCondicionados arsCondicionados) {
        lampadas.desligarTodas();
        persianas.fecharTodas();
        arsCondicionados.desligarTodos();
    }
}
