package org.example.interfaces;

/**
 * Contrato universal para lâmpadas inteligentes.
 *
 * <p>Abstrai as diferenças entre fabricantes (ShoyuMi usa {@code ligar()}/{@code desligar()};
 * Phellipes usa {@code setIntensidade(int)}) por trás de uma interface única.
 * Qualquer lâmpada do sistema, independente do fabricante, deve implementar esta interface.</p>
 *
 * <p>Padrão aplicado: <b>Adapter</b> — as implementações concretas adaptam as APIs
 * específicas de cada fabricante para este contrato.</p>
 */
public interface Lampada {

    /**
     * Liga a lâmpada.
     * O comportamento exato depende do fabricante (ex: intensidade máxima para Phellipes).
     */
    void ligar();

    /**
     * Desliga a lâmpada.
     * O comportamento exato depende do fabricante (ex: intensidade zero para Phellipes).
     */
    void desligar();
}
