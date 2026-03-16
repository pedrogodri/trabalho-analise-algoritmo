package org.example.entrega;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.example.implementation.entrega.SedexEntrega;

class SedexEntregaTest {

    private SedexEntrega sedex;

    @BeforeEach
    void setUp() {
        sedex = new SedexEntrega();
    }

    @Test
    void deveRetornar12ReaisParaPesoAbaixoDe500g() {
        assertEquals(12.50, sedex.calcular(0.3f));
    }

    @Test
    void deveRetornar12ReaisParaPesoExatamenteDe500g() {
        assertEquals(12.50, sedex.calcular(0.5f));
    }

    @Test
    void deveRetornar20ReaisParaPesoEntre500gE1Kg() {
        assertEquals(20.00, sedex.calcular(0.8f));
    }

    @Test
    void deveRetornar20ReaisParaPesoExatamenteDe1Kg() {
        assertEquals(20.00, sedex.calcular(1.0f));
    }

    @Test
    void deveRetornar48ReaisParaPesoDe1Kg100g() {
        // 1.1kg -> 1 grupo de 100g -> R$46,50 + R$1,50 = R$48,00
        assertEquals(48.00, sedex.calcular(1.1f), 0.01);
    }

    @Test
    void deveRetornar49_50ReaisParaPesoDe1Kg200g() {
        // 1.2kg -> 2 grupos de 100g -> R$46,50 + R$3,00 = R$49,50
        assertEquals(49.50, sedex.calcular(1.2f), 0.01);
    }

    @Test
    void deveArredondarGruposParaCimaParaPesosFracionados() {
        // 1.15kg -> ceil(1.5) = 2 grupos -> R$46,50 + R$3,00 = R$49,50
        assertEquals(49.50, sedex.calcular(1.15f), 0.01);
    }

    @Test
    void deveTerDescricaoCorreta() {
        assertEquals("Encomenda Sedex", sedex.descricao());
    }
}
