package org.example.models;

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
public final class Lampadas {

    private final List<Lampada> lampadas;

    /**
     * @param lampadas lista de lâmpadas a serem gerenciadas; não pode ser nula
     */
    public Lampadas(List<Lampada> lampadas) {
        this.lampadas = List.copyOf(lampadas);
    }

    /**
     * Liga todas as lâmpadas da coleção.
     */
    public void ligarTodas() {
        lampadas.forEach(Lampada::ligar);
    }

    /**
     * Desliga todas as lâmpadas da coleção.
     */
    public void desligarTodas() {
        lampadas.forEach(Lampada::desligar);
    }
}
