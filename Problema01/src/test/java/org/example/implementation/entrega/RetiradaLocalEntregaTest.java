package org.example.implementation.entrega;

import org.example.model.vo.PesoEmKg;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RetiradaLocalEntregaTest {

    private RetiradaLocalEntrega retirada;

    @BeforeEach
    void setUp() {
        retirada = new RetiradaLocalEntrega();
    }

    @Test
    void deveRetornarZeroParaPesoPequeno() {
        assertEquals(0.00, retirada.calcular(new PesoEmKg(0.3f)));
    }

    @Test
    void deveRetornarZeroParaPesoMedio() {
        assertEquals(0.00, retirada.calcular(new PesoEmKg(1.5f)));
    }

    @Test
    void deveRetornarZeroParaPesoGrande() {
        assertEquals(0.00, retirada.calcular(new PesoEmKg(10.0f)));
    }

    @Test
    void deveRetornarZeroParaPesoMuitoGrande() {
        assertEquals(0.00, retirada.calcular(new PesoEmKg(100.0f)));
    }

    @Test
    void deveTerDescricaoCorreta() {
        assertEquals("Retirada no Local", retirada.descricao());
    }
}
