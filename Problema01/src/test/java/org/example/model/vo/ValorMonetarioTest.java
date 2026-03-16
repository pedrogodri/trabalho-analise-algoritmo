package org.example.model.vo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.example.exceptions.DadoInvalidoException;

import java.math.BigDecimal;

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
    void deveLancarExcecaoParaBigDecimalNulo() {
        assertThrows(DadoInvalidoException.class, () -> new ValorMonetario((BigDecimal) null));
    }

    @Test
    void deveAceitarValorMuitoPequeno() {
        assertDoesNotThrow(() -> new ValorMonetario(0.01f));
    }

    @Test
    void deveFormatarToStringComoMoeda() {
        ValorMonetario valor = new ValorMonetario(50.00f);
        assertEquals(String.format("R$ %.2f", 50.00f), valor.toString());
    }

    @Test
    void deveFormatarToStringComCentavos() {
        ValorMonetario valor = new ValorMonetario(9.99f);
        assertTrue(valor.toString().startsWith("R$ "));
        assertTrue(valor.toString().contains("9"));
    }

    @Test
    void doisValoresIguaisDevemSerIguais() {
        assertEquals(new ValorMonetario(49.90f), new ValorMonetario(49.90f));
    }

    @Test
    void valoresDistintosDevemSerDiferentes() {
        assertNotEquals(new ValorMonetario(49.90f), new ValorMonetario(59.90f));
    }

    @Test
    void doisValoresIguaisDevemTerMesmoHashCode() {
        assertEquals(new ValorMonetario(49.90f).hashCode(), new ValorMonetario(49.90f).hashCode());
    }

    @Test
    void deveRetornarValorExatoComoBigDecimal() {
        ValorMonetario valor = new ValorMonetario(new BigDecimal("49.90"));
        assertEquals(new BigDecimal("49.90"), valor.valorExato());
    }
}
