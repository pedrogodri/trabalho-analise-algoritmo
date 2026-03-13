package org.example.entrega;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.example.exceptions.EntregaNaoDisponivelException;

class PacEntregaTest {

    private PacEntrega pac;

    @BeforeEach
    void setUp() {
        pac = new PacEntrega();
    }

    @Test
    void deveRetornar10ReaisParaPesoAbaixoDe1Kg() {
        assertEquals(10.00, pac.calcular(0.8f));
    }

    @Test
    void deveRetornar10ReaisParaPesoExatamenteDe1Kg() {
        assertEquals(10.00, pac.calcular(1.0f));
    }

    @Test
    void deveRetornar15ReaisParaPesoEntre1KgE2Kg() {
        assertEquals(15.00, pac.calcular(1.5f));
    }

    @Test
    void deveRetornar15ReaisParaPesoExatamenteDe2Kg() {
        assertEquals(15.00, pac.calcular(2.0f));
    }

    @Test
    void deveLancarExcecaoParaPesoAcimaDe2Kg() {
        assertThrows(EntregaNaoDisponivelException.class, () -> pac.calcular(2.1f));
    }

    @Test
    void deveTerDescricaoCorreta() {
        assertEquals("Encomenda PAC", pac.descricao());
    }
}
