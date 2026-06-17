package org.example.interfaces;

/**
 * Contrato universal para persianas automáticas.
 *
 * <p>Abstrai as diferenças entre fabricantes (Solarius usa {@code subirPersiana()}/{@code descerPersiana()};
 * NatLight possui mecânica de palhetas com múltiplas operações e restrições de ordem)
 * por trás de dois comandos simples: abrir e fechar.</p>
 *
 * <p>Padrão aplicado: <b>Adapter</b> — as implementações concretas encapsulam a
 * complexidade de cada fabricante, inclusive a ordem de operações exigida pela NatLight.</p>
 */
public interface Persiana {

    /**
     * Abre a persiana (ergue/sobe).
     * Para a NatLight, garante internamente que as palhetas estejam abertas antes de subir.
     */
    void abrir();

    /**
     * Fecha a persiana (desce/fecha palhetas).
     * Para a NatLight, garante internamente a ordem correta: desce primeiro, depois fecha as palhetas.
     */
    void fechar();
}
