package org.example.model.vo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.example.exceptions.DadoInvalidoException;

class ValorMonetarioTest {

    @Test
    void deveAceitarValorPositivo() {
        ValorMonetario valor = new ValorMonetario(49.90f);
        assertEquals(49.90f, valor.valor(), 0.001f);
    }

    @Test
    void deveLancarExcecaoParaValorZero() {
        DadoInvalidoException excecao = assertThrows(DadoInvalidoException.class,
                () -> new ValorMonetario(0f));
        assertEquals("Valor do produto deve ser maior que zero", excecao.getMessage());
    }

    @Test
    void deveLancarExcecaoParaValorNegativo() {
        DadoInvalidoException excecao = assertThrows(DadoInvalidoException.class,
                () -> new ValorMonetario(-10f));
        assertEquals("Valor do produto deve ser maior que zero", excecao.getMessage());
    }

    @Test
    void deveAceitarValorMuitoPequeno() {
        assertDoesNotThrow(() -> new ValorMonetario(0.01f));
    }
}
