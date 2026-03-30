package org.example.modos;

import org.example.interfaces.Modo;
import org.example.models.ArsCondicionados;
import org.example.models.Lampadas;
import org.example.models.Persianas;
import org.example.values.Temperatura;

/**
 * Modo Filme — configura a casa para sessão de cinema em casa.
 *
 * <p>Ações executadas ao ativar:</p>
 * <ol>
 *   <li>Desliga todas as lâmpadas (ambiente escuro)</li>
 *   <li>Fecha todas as persianas (bloqueia luz externa)</li>
 *   <li>Liga todos os ar-condicionados</li>
 *   <li>Define temperatura agradável ({@value #TEMPERATURA_FILME_VALOR}°C) em todos os ACs</li>
 * </ol>
 *
 * <p>Demonstra o <b>Princípio Aberto/Fechado (OCP)</b>: um novo modo foi adicionado
 * sem modificar nenhuma classe existente — apenas implementando {@link Modo}.</p>
 *
 * <p>Padrão aplicado: <b>Strategy</b> — implementação concreta de {@link Modo}
 * que encapsula as regras de negócio do modo filme.</p>
 */
public final class ModoFilme implements Modo {

    private static final int TEMPERATURA_FILME_VALOR = 22;
    private static final Temperatura TEMPERATURA_FILME = new Temperatura(TEMPERATURA_FILME_VALOR);

    /**
     * {@inheritDoc}
     *
     * <p>Ordem: lâmpadas desligadas → persianas fechadas → ACs ligados →
     * temperatura definida em {@value #TEMPERATURA_FILME_VALOR}°C.</p>
     */
    @Override
    public void ativar(Lampadas lampadas, Persianas persianas, ArsCondicionados arsCondicionados) {
        System.out.println("[ModoFilme] Preparando ambiente para cinema em casa...");
        lampadas.desligarTodas();
        persianas.fecharTodas();
        arsCondicionados.ligarTodos();
        arsCondicionados.definirTemperaturaEmTodos(TEMPERATURA_FILME);
        System.out.println("[ModoFilme] Ambiente configurado: luzes apagadas, persianas fechadas, ACs a " + TEMPERATURA_FILME_VALOR + "°C.");
    }
}
