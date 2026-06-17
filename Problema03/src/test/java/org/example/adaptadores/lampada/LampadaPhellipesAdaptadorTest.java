package org.example.adaptadores.lampada;

import br.furb.analise.algoritmos.LampadaPhellipes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LampadaPhellipesAdaptadorTest {

    private LampadaPhellipes lampadaNativa;
    private LampadaPhellipesAdaptador adaptador;

    @BeforeEach
    void setUp() {
        lampadaNativa = new LampadaPhellipes();
        adaptador = new LampadaPhellipesAdaptador(lampadaNativa);
    }

    @Test
    void ligar_deveDefinirIntensidadeMaxima() {
        adaptador.ligar();
        assertEquals(100, lampadaNativa.getIntensidade());
    }

    @Test
    void desligar_deveDefinirIntensidadeZero() {
        adaptador.ligar();
        adaptador.desligar();
        assertEquals(0, lampadaNativa.getIntensidade());
    }

    @Test
    void ligarEDesligar_deveAlternarIntensidade() {
        adaptador.ligar();
        assertEquals(100, lampadaNativa.getIntensidade());

        adaptador.desligar();
        assertEquals(0, lampadaNativa.getIntensidade());

        adaptador.ligar();
        assertEquals(100, lampadaNativa.getIntensidade());
    }
}
