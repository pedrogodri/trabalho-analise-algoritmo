package org.example.entrega;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RetiradaLocalEntregaTest {

    private RetiradaLocalEntrega retirada;

    @BeforeEach
    void setUp() {
        retirada = new RetiradaLocalEntrega();
    }

    @Test
    void deveRetornarZeroIndependenteDoPeso() {
        assertEquals(0.00, retirada.calcular(0.3f));
        assertEquals(0.00, retirada.calcular(1.5f));
        assertEquals(0.00, retirada.calcular(10.0f));
    }

    @Test
    void deveTerDescricaoCorreta() {
        assertEquals("Retirada no Local", retirada.descricao());
    }
}
