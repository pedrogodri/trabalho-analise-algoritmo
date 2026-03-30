package org.example.models;

import org.example.excecoes.DispositivoNuloException;
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
public class Persianas {

    private final List<Persiana> persianas;

    /**
     * @param persianas lista de persianas a serem gerenciadas; não pode ser nula
     * @throws DispositivoNuloException se {@code persianas} for nula
     */
    public Persianas(List<Persiana> persianas) {
        if (persianas == null) throw new DispositivoNuloException("Lista de persianas não pode ser nula.");
        this.persianas = List.copyOf(persianas);
    }

    /**
     * Abre todas as persianas da coleção.
     */
    public void abrirTodas() {
        System.out.println("[Persianas] Abrindo todas as " + persianas.size() + " persiana(s)...");
        persianas.forEach(Persiana::abrir);
        System.out.println("[Persianas] Todas as persianas foram abertas.");
    }

    /**
     * Fecha todas as persianas da coleção.
     */
    public void fecharTodas() {
        System.out.println("[Persianas] Fechando todas as " + persianas.size() + " persiana(s)...");
        persianas.forEach(Persiana::fechar);
        System.out.println("[Persianas] Todas as persianas foram fechadas.");
    }
}
