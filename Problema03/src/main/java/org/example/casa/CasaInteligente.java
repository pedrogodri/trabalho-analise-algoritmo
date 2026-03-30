package org.example.casa;

import org.example.excecoes.DispositivoNuloException;
import org.example.excecoes.ModoInvalidoException;
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
     * @param lampadas         coleção de lâmpadas da casa; não pode ser nula
     * @param persianas        coleção de persianas da casa; não pode ser nula
     * @param arsCondicionados coleção de ar-condicionados da casa; não pode ser nula
     * @throws DispositivoNuloException se qualquer argumento for nulo
     */
    public CasaInteligente(Lampadas lampadas, Persianas persianas, ArsCondicionados arsCondicionados) {
        if (lampadas == null) throw new DispositivoNuloException("CasaInteligente: lampadas não pode ser nula.");
        if (persianas == null) throw new DispositivoNuloException("CasaInteligente: persianas não pode ser nula.");
        if (arsCondicionados == null) throw new DispositivoNuloException("CasaInteligente: arsCondicionados não pode ser nulo.");
        this.lampadas = lampadas;
        this.persianas = persianas;
        this.arsCondicionados = arsCondicionados;
        System.out.println("[CasaInteligente] Casa inicializada com todos os dispositivos conectados.");
    }

    /**
     * Ativa um modo de operação na casa (ex: Modo Sono, Modo Trabalho, Modo Filme).
     *
     * <p>Delega ao objeto {@link Modo} as ações a serem executadas sobre os dispositivos,
     * mantendo a casa aberta para novos modos sem modificação (OCP).</p>
     *
     * @param modo modo a ser ativado; não pode ser nulo
     * @throws ModoInvalidoException se {@code modo} for nulo
     */
    public void ativarModo(Modo modo) {
        if (modo == null) throw new ModoInvalidoException("CasaInteligente: modo a ativar não pode ser nulo.");
        System.out.println("[CasaInteligente] Ativando modo: " + modo.getClass().getSimpleName() + "...");
        modo.ativar(lampadas, persianas, arsCondicionados);
        System.out.println("[CasaInteligente] Modo " + modo.getClass().getSimpleName() + " ativado com sucesso.");
    }

    /**
     * Liga todas as lâmpadas da casa.
     */
    public void ligarLampadas() {
        System.out.println("[CasaInteligente] Comando: ligar lâmpadas.");
        lampadas.ligarTodas();
    }

    /**
     * Desliga todas as lâmpadas da casa.
     */
    public void desligarLampadas() {
        System.out.println("[CasaInteligente] Comando: desligar lâmpadas.");
        lampadas.desligarTodas();
    }

    /**
     * Abre todas as persianas da casa.
     */
    public void abrirPersianas() {
        System.out.println("[CasaInteligente] Comando: abrir persianas.");
        persianas.abrirTodas();
    }

    /**
     * Fecha todas as persianas da casa.
     */
    public void fecharPersianas() {
        System.out.println("[CasaInteligente] Comando: fechar persianas.");
        persianas.fecharTodas();
    }

    /**
     * Liga todos os ar-condicionados da casa.
     */
    public void ligarArsCondicionados() {
        System.out.println("[CasaInteligente] Comando: ligar ar-condicionados.");
        arsCondicionados.ligarTodos();
    }

    /**
     * Desliga todos os ar-condicionados da casa.
     */
    public void desligarArsCondicionados() {
        System.out.println("[CasaInteligente] Comando: desligar ar-condicionados.");
        arsCondicionados.desligarTodos();
    }
}
