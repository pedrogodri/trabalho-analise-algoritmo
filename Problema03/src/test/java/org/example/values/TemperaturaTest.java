package org.example.values;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TemperaturaTest {

    @Test
    void construtor_comValorMinimo_deveSerValido() {
        var t = new Temperatura(15);
        assertEquals(15, t.valor());
    }

    @Test
    void construtor_comValorMaximo_deveSerValido() {
        var t = new Temperatura(35);
        assertEquals(35, t.valor());
    }

    @Test
    void construtor_comValorIntermediario_deveSerValido() {
        var t = new Temperatura(24);
        assertEquals(24, t.valor());
    }

    @Test
    void construtor_abaixoDoMinimo_deveLancarIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Temperatura(14));
    }

    @Test
    void construtor_acimaDoMaximo_deveLancarIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Temperatura(36));
    }

    @Test
    void equals_comMesmoValor_deveRetornarTrue() {
        assertEquals(new Temperatura(20), new Temperatura(20));
    }

    @Test
    void equals_comValoresDiferentes_deveRetornarFalse() {
        assertNotEquals(new Temperatura(20), new Temperatura(21));
    }

    @Test
    void hashCode_comMesmoValor_deveSerIgual() {
        assertEquals(new Temperatura(20).hashCode(), new Temperatura(20).hashCode());
    }

    @Test
    void toString_deveConterValorEUnidade() {
        assertEquals("20°C", new Temperatura(20).toString());
    }
}
