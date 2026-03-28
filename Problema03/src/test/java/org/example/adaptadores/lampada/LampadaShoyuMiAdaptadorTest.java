package org.example.adaptadores.lampada;

import br.furb.analise.algoritmos.LampadaShoyuMi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LampadaShoyuMiAdaptadorTest {

    private LampadaShoyuMi lampadaNativa;
    private LampadaShoyuMiAdaptador adaptador;

    @BeforeEach
    void setUp() {
        lampadaNativa = new LampadaShoyuMi();
        adaptador = new LampadaShoyuMiAdaptador(lampadaNativa);
    }

    @Test
    void ligar_deveLigarALampada() {
        adaptador.ligar();
        assertTrue(lampadaNativa.estaLigada());
    }

    @Test
    void desligar_deveDesligarALampada() {
        adaptador.ligar();
        adaptador.desligar();
        assertFalse(lampadaNativa.estaLigada());
    }

    @Test
    void ligarEDesligar_deveAlternarEstado() {
        adaptador.ligar();
        assertTrue(lampadaNativa.estaLigada());

        adaptador.desligar();
        assertFalse(lampadaNativa.estaLigada());

        adaptador.ligar();
        assertTrue(lampadaNativa.estaLigada());
    }
}
