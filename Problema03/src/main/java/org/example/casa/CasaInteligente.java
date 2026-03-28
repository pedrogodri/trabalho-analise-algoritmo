package org.example.casa;

import org.example.interfaces.Modo;
import org.example.models.ArsCondicionados;
import org.example.models.Lampadas;
import org.example.models.Persianas;

/**
 * Fachada central de controle da casa inteligente.
 *
 * <p>Fornece um ponto único de acesso para controlar todos os dispositivos da casa —
 * lâmpadas, persianas e ar-condicionados — de forma unificada, independente dos
 * fabricantes envolvidos.</p>
 *
 * <p>Internamente coordena três subsistemas encapsulados em coleções de primeira classe:
 * {@link Lampadas}, {@link Persianas} e {@link ArsCondicionados}.
 * A lógica de controle coletivo vive nas próprias coleções;
 * a lógica de modos vive nas implementações de {@link Modo}.</p>
 *
 * <p>Padrão aplicado: <b>Facade</b> — simplifica a interface de um sistema complexo
 * (múltiplos dispositivos de múltiplos fabricantes) em um único objeto de fácil uso.
 * Combinado com <b>Strategy</b> para os modos de operação.</p>
 */
public final class CasaInteligente {

    private final Lampadas lampadas;
    private final Persianas persianas;
    private final ArsCondicionados arsCondicionados;

    /**
     * @param lampadas         coleção de lâmpadas da casa
     * @param persianas        coleção de persianas da casa
     * @param arsCondicionados coleção de ar-condicionados da casa
     */
    public CasaInteligente(Lampadas lampadas, Persianas persianas, ArsCondicionados arsCondicionados) {
        this.lampadas = lampadas;
        this.persianas = persianas;
        this.arsCondicionados = arsCondicionados;
    }

    /**
     * Ativa um modo de operação na casa (ex: Modo Sono, Modo Trabalho).
     *
     * <p>Delega ao objeto {@link Modo} as ações a serem executadas sobre os dispositivos,
     * mantendo a casa aberta para novos modos sem modificação (OCP).</p>
     *
     * @param modo modo a ser ativado; não pode ser nulo
     */
    public void ativarModo(Modo modo) {
        modo.ativar(lampadas, persianas, arsCondicionados);
    }

    /**
     * Liga todas as lâmpadas da casa.
     */
    public void ligarLampadas() {
        lampadas.ligarTodas();
    }

    /**
     * Desliga todas as lâmpadas da casa.
     */
    public void desligarLampadas() {
        lampadas.desligarTodas();
    }

    /**
     * Abre todas as persianas da casa.
     */
    public void abrirPersianas() {
        persianas.abrirTodas();
    }

    /**
     * Fecha todas as persianas da casa.
     */
    public void fecharPersianas() {
        persianas.fecharTodas();
    }

    /**
     * Liga todos os ar-condicionados da casa.
     */
    public void ligarArsCondicionados() {
        arsCondicionados.ligarTodos();
    }

    /**
     * Desliga todos os ar-condicionados da casa.
     */
    public void desligarArsCondicionados() {
        arsCondicionados.desligarTodos();
    }
}
