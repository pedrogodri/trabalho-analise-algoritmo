package org.example.models;

import org.example.interfaces.ArCondicionado;

import java.util.List;

/**
 * Coleção de primeira classe para {@link ArCondicionado ar-condicionados}.
 *
 * <p>Encapsula uma lista de ar-condicionados e expõe operações coletivas, evitando
 * que a lógica de iteração se espalhe pelo restante do sistema.</p>
 *
 * <p>Object Calisthenics: <b>coleções de primeira classe</b> — nenhuma outra
 * variável de instância além da coleção em si é mantida por esta classe.</p>
 *
 * <p>A lista interna é imutável após a construção ({@link List#copyOf})
 * para garantir integridade do estado.</p>
 */
public final class ArsCondicionados {

    private final List<ArCondicionado> ars;

    /**
     * @param ars lista de ar-condicionados a serem gerenciados; não pode ser nula
     */
    public ArsCondicionados(List<ArCondicionado> ars) {
        this.ars = List.copyOf(ars);
    }

    /**
     * Liga todos os ar-condicionados da coleção.
     */
    public void ligarTodos() {
        ars.forEach(ArCondicionado::ligar);
    }

    /**
     * Desliga todos os ar-condicionados da coleção.
     */
    public void desligarTodos() {
        ars.forEach(ArCondicionado::desligar);
    }

    /**
     * Define a mesma temperatura em todos os ar-condicionados da coleção.
     *
     * <p><b>Atenção</b>: o VentoBaumn exige que o aparelho esteja ligado antes de chamar
     * este método. Certifique-se de chamar {@link #ligarTodos()} antes de
     * {@code definirTemperaturaEmTodos} ao utilizar o ModoTrabalho.</p>
     *
     * @param temperatura temperatura desejada, entre 15°C e 35°C
     */
    public void definirTemperaturaEmTodos(int temperatura) {
        ars.forEach(ar -> ar.definirTemperatura(temperatura));
    }
}
