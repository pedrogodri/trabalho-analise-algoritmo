package org.example.models;

import org.example.interfaces.Persiana;

import java.util.List;

/**
 * Coleção de primeira classe para {@link Persiana persianas}.
 *
 * <p>Encapsula uma lista de persianas e expõe operações coletivas, evitando
 * que a lógica de iteração se espalhe pelo restante do sistema.</p>
 *
 * <p>Object Calisthenics: <b>coleções de primeira classe</b> — nenhuma outra
 * variável de instância além da coleção em si é mantida por esta classe.</p>
 *
 * <p>A lista interna é imutável após a construção ({@link List#copyOf})
 * para garantir integridade do estado.</p>
 */
public final class Persianas {

    private final List<Persiana> persianas;

    /**
     * @param persianas lista de persianas a serem gerenciadas; não pode ser nula
     */
    public Persianas(List<Persiana> persianas) {
        this.persianas = List.copyOf(persianas);
    }

    /**
     * Abre todas as persianas da coleção.
     */
    public void abrirTodas() {
        persianas.forEach(Persiana::abrir);
    }

    /**
     * Fecha todas as persianas da coleção.
     */
    public void fecharTodas() {
        persianas.forEach(Persiana::fechar);
    }
}
