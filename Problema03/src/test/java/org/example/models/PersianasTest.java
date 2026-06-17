package org.example.models;

import br.furb.analise.algoritmos.PersianaNatLight;
import br.furb.analise.algoritmos.PersianaSolarius;
import org.example.adaptadores.persiana.PersianaNatLightAdaptador;
import org.example.adaptadores.persiana.PersianaSolariusAdaptador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersianasTest {

    private PersianaSolarius solariusNativa;
    private PersianaNatLight natLightNativa;
    private Persianas persianas;

    @BeforeEach
    void setUp() {
        solariusNativa = new PersianaSolarius();
        natLightNativa = new PersianaNatLight();
        persianas = new Persianas(List.of(
                new PersianaSolariusAdaptador(solariusNativa),
                new PersianaNatLightAdaptador(natLightNativa)
        ));
    }

    @Test
    void abrirTodas_deveAbrirTodasAsPersianas() {
        persianas.fecharTodas();
        persianas.abrirTodas();
        assertTrue(solariusNativa.estaAberta());
        assertTrue(natLightNativa.estaPalhetaErguida());
    }

    @Test
    void fecharTodas_deveFecharTodasAsPersianas() {
        persianas.fecharTodas();
        assertFalse(solariusNativa.estaAberta());
        assertFalse(natLightNativa.estaPalhetaErguida());
        assertFalse(natLightNativa.estaPalhetaAberta());
    }
}
