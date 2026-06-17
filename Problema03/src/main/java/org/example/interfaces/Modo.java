package org.example.interfaces;

import org.example.models.ArsCondicionados;
import org.example.models.Lampadas;
import org.example.models.Persianas;

/**
 * Contrato para modos de operação da casa inteligente.
 *
 * <p>Cada modo encapsula um conjunto de ações a serem executadas sobre os
 * dispositivos da casa. Novos modos podem ser criados implementando esta interface
 * sem necessidade de modificar a {@code CasaInteligente}.</p>
 *
 * <p>Padrão aplicado: <b>Strategy</b> — permite trocar o comportamento de ativação
 * de modo sem alterar a classe que o utiliza ({@code CasaInteligente}),
 * seguindo o Princípio Aberto/Fechado (OCP).</p>
 */
public interface Modo {

    /**
     * Ativa o modo, executando as ações sobre os dispositivos da casa.
     *
     * @param lampadas         coleção de lâmpadas da casa
     * @param persianas        coleção de persianas da casa
     * @param arsCondicionados coleção de ar-condicionados da casa
     */
    void ativar(Lampadas lampadas, Persianas persianas, ArsCondicionados arsCondicionados);
}
