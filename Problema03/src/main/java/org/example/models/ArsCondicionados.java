package org.example.models;

import org.example.excecoes.DispositivoNuloException;
import org.example.interfaces.ArCondicionado;
import org.example.values.Temperatura;

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
public class ArsCondicionados {

    private final List<ArCondicionado> ars;

    /**
     * @param ars lista de ar-condicionados a serem gerenciados; não pode ser nula nem vazia
     * @throws DispositivoNuloException se {@code ars} for nula
     */
    public ArsCondicionados(List<ArCondicionado> ars) {
        if (ars == null) throw new DispositivoNuloException("Lista de ar-condicionados não pode ser nula.");
        this.ars = List.copyOf(ars);
    }

    /**
     * Liga todos os ar-condicionados da coleção.
     */
    public void ligarTodos() {
        System.out.println("[ArsCondicionados] Ligando todos os " + ars.size() + " ar-condicionado(s)...");
        ars.forEach(ArCondicionado::ligar);
        System.out.println("[ArsCondicionados] Todos os ar-condicionados foram ligados.");
    }

    /**
     * Desliga todos os ar-condicionados da coleção.
     */
    public void desligarTodos() {
        System.out.println("[ArsCondicionados] Desligando todos os " + ars.size() + " ar-condicionado(s)...");
        ars.forEach(ArCondicionado::desligar);
        System.out.println("[ArsCondicionados] Todos os ar-condicionados foram desligados.");
    }

    /**
     * Define a mesma temperatura em todos os ar-condicionados da coleção.
     *
     * <p><b>Atenção</b>: o VentoBaumn exige que o aparelho esteja ligado antes de chamar
     * este método. Certifique-se de chamar {@link #ligarTodos()} antes de
     * {@code definirTemperaturaEmTodos} ao utilizar o ModoTrabalho.</p>
     *
     * @param temperatura valor tipado de temperatura, entre {@value Temperatura#MIN}°C e {@value Temperatura#MAX}°C
     */
    public void definirTemperaturaEmTodos(Temperatura temperatura) {
        System.out.println("[ArsCondicionados] Definindo temperatura de " + temperatura.valor() + "°C em todos os " + ars.size() + " ar-condicionado(s)...");
        ars.forEach(ar -> ar.definirTemperatura(temperatura.valor()));
        System.out.println("[ArsCondicionados] Temperatura definida para " + temperatura.valor() + "°C em todos os ar-condicionados.");
    }
}
