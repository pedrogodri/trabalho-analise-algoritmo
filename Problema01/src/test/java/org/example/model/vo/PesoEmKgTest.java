package org.example.model.vo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.example.exceptions.DadoInvalidoException;
import org.example.model.vo.PesoEmKg;

class PesoEmKgTest {

    @Test
    void deveAceitarPesoPositivo() {
        PesoEmKg peso = new PesoEmKg(0.5f);
        assertEquals(0.5f, peso.valor(), 0.001f);
    }

    @Test
    void deveLancarExcecaoParaPesoZero() {
        DadoInvalidoException excecao = assertThrows(DadoInvalidoException.class,
                () -> new PesoEmKg(0f));
        assertEquals("Peso do produto deve ser maior que zero", excecao.getMessage());
    }

    @Test
    void deveLancarExcecaoParaPesoNegativo() {
        DadoInvalidoException excecao = assertThrows(DadoInvalidoException.class,
                () -> new PesoEmKg(-1f));
        assertEquals("Peso do produto deve ser maior que zero", excecao.getMessage());
    }

    @Test
    void deveAceitarPesoMuitoPequeno() {
        assertDoesNotThrow(() -> new PesoEmKg(0.001f));
    }

    @Test
    void deveFormatarToStringComUnidade() {
        PesoEmKg peso = new PesoEmKg(1.5f);
        assertEquals("1.5kg", peso.toString());
    }

    @Test
    void deveFormatarToStringParaPesoInteiro() {
        PesoEmKg peso = new PesoEmKg(2.0f);
        assertEquals("2.0kg", peso.toString());
    }
}
