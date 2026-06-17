package org.example.models;

import org.example.excecoes.DispositivoNuloException;
import org.example.interfaces.Lampada;

import java.util.List;

/**
 * Coleção de primeira classe para {@link Lampada lâmpadas}.
 *
 * <p>Encapsula uma lista de lâmpadas e expõe operações coletivas, evitando
 * que a lógica de iteração se espalhe pelo restante do sistema.</p>
 *
 * <p>Object Calisthenics: <b>coleções de primeira classe</b> — nenhuma outra
 * variável de instância além da coleção em si é mantida por esta classe.</p>
 *
 * <p>A lista interna é imutável após a construção ({@link List#copyOf})
 * para garantir integridade do estado.</p>
 */
public class Lampadas {

    private final List<Lampada> lampadas;

    /**
     * @param lampadas lista de lâmpadas a serem gerenciadas; não pode ser nula
     * @throws DispositivoNuloException se {@code lampadas} for nula
     */
    public Lampadas(List<Lampada> lampadas) {
        if (lampadas == null) throw new DispositivoNuloException("Lista de lâmpadas não pode ser nula.");
        this.lampadas = List.copyOf(lampadas);
    }

    /**
     * Liga todas as lâmpadas da coleção.
     */
    public void ligarTodas() {
        System.out.println("[Lampadas] Ligando todas as " + lampadas.size() + " lâmpada(s)...");
        lampadas.forEach(Lampada::ligar);
        System.out.println("[Lampadas] Todas as lâmpadas foram ligadas.");
    }

    /**
     * Desliga todas as lâmpadas da coleção.
     */
    public void desligarTodas() {
        System.out.println("[Lampadas] Desligando todas as " + lampadas.size() + " lâmpada(s)...");
        lampadas.forEach(Lampada::desligar);
        System.out.println("[Lampadas] Todas as lâmpadas foram desligadas.");
    }
}
