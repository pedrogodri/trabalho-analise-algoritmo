package org.example.dominio.acao;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PrecoAcaoTest {

    @Test
    void deveCriarPrecoComValorPositivo() {
        PrecoAcao preco = new PrecoAcao("10.00");
        assertEquals("R$ 10.00", preco.exibir());
    }

    @Test
    void deveLancarExcecaoParaPrecoZero() {
        assertThrows(IllegalArgumentException.class, () -> new PrecoAcao(BigDecimal.ZERO));
    }

    @Test
    void deveLancarExcecaoParaPrecoNegativo() {
        assertThrows(IllegalArgumentException.class, () -> new PrecoAcao("-5.00"));
    }

    @Test
    void deveRetornarVerdadeiroQuandoMaiorOuIgual() {
        PrecoAcao maior = new PrecoAcao("35.00");
        PrecoAcao menor = new PrecoAcao("30.00");
        assertTrue(maior.eMaiorOuIgualA(menor));
    }

    @Test
    void deveRetornarVerdadeiroQuandoIgual() {
        PrecoAcao preco1 = new PrecoAcao("38.00");
        PrecoAcao preco2 = new PrecoAcao("38.00");
        assertTrue(preco1.eMaiorOuIgualA(preco2));
    }

    @Test
    void deveRetornarFalsoQuandoMenor() {
        PrecoAcao menor = new PrecoAcao("29.00");
        PrecoAcao maior = new PrecoAcao("30.00");
        assertFalse(menor.eMaiorOuIgualA(maior));
    }

    @Test
    void deveAplicarArredondamentoParaDuasCasas() {
        PrecoAcao preco = new PrecoAcao("10.555");
        assertEquals("R$ 10.56", preco.exibir());
    }

    @Test
    void deveImplementarEquality() {
        PrecoAcao preco1 = new PrecoAcao("25.00");
        PrecoAcao preco2 = new PrecoAcao("25.00");
        assertEquals(preco1, preco2);
    }
}
