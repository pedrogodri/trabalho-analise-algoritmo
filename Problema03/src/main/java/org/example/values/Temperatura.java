package org.example.values;

/**
 * Value Object que representa uma temperatura válida para dispositivos IoT.
 *
 * <p>Encapsula a regra de negócio de que temperaturas permitidas estão entre
 * {@value #MIN}°C e {@value #MAX}°C, eliminando a duplicação dessas constantes
 * espalhadas pelos adaptadores e modos.</p>
 *
 * <p>Object Calisthenics: <b>Wrap All Primitives</b> — um {@code int} solto não
 * carrega nenhuma semântica; este objeto torna inválido representar uma temperatura
 * fora do domínio permitido.</p>
 *
 * <p>Clean Code: o nome revela a intenção; a validação fica em um único lugar (DRY).</p>
 */
public final class Temperatura {

    public static final int MIN = 15;
    public static final int MAX = 35;

    private final int valor;

    /**
     * Cria uma {@code Temperatura} com o valor informado.
     *
     * @param valor temperatura desejada
     * @throws IllegalArgumentException se {@code valor} estiver fora de [{@value #MIN}, {@value #MAX}]
     */
    public Temperatura(int valor) {
        if (valor < MIN || valor > MAX) {
            throw new IllegalArgumentException(
                    "Temperatura inválida: %d°C. Permitido entre %d°C e %d°C.".formatted(valor, MIN, MAX)
            );
        }
        this.valor = valor;
    }

    /**
     * Retorna o valor numérico da temperatura.
     *
     * @return valor em graus Celsius
     */
    public int valor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Temperatura other)) return false;
        return valor == other.valor;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(valor);
    }

    @Override
    public String toString() {
        return valor + "°C";
    }
}
