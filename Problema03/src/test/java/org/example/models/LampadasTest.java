package org.example.models;

import br.furb.analise.algoritmos.LampadaPhellipes;
import br.furb.analise.algoritmos.LampadaShoyuMi;
import org.example.adaptadores.lampada.LampadaPhellipesAdaptador;
import org.example.adaptadores.lampada.LampadaShoyuMiAdaptador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LampadasTest {

    private LampadaShoyuMi shoyuMiNativa;
    private LampadaPhellipes phellipesNativa;
    private Lampadas lampadas;

    @BeforeEach
    void setUp() {
        shoyuMiNativa = new LampadaShoyuMi();
        phellipesNativa = new LampadaPhellipes();
        lampadas = new Lampadas(List.of(
                new LampadaShoyuMiAdaptador(shoyuMiNativa),
                new LampadaPhellipesAdaptador(phellipesNativa)
        ));
    }

    @Test
    void ligarTodas_deveLigarTodasAsLampadas() {
        lampadas.ligarTodas();
        assertTrue(shoyuMiNativa.estaLigada());
        assertEquals(100, phellipesNativa.getIntensidade());
    }

    @Test
    void desligarTodas_deveDesligarTodasAsLampadas() {
        lampadas.ligarTodas();
        lampadas.desligarTodas();
        assertFalse(shoyuMiNativa.estaLigada());
        assertEquals(0, phellipesNativa.getIntensidade());
    }
}
