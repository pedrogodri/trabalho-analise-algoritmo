package org.example.model.vo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.example.exceptions.DadoInvalidoException;
import org.example.model.vo.Quantidade;

class QuantidadeTest {

    @Test
    void deveAceitarQuantidadePositiva() {
        Quantidade quantidade = new Quantidade(3);
        assertEquals(3, quantidade.valor());
    }

    @Test
    void deveLancarExcecaoParaQuantidadeZero() {
        DadoInvalidoException excecao = assertThrows(DadoInvalidoException.class,
                () -> new Quantidade(0));
        assertEquals("Quantidade deve ser maior que zero", excecao.getMessage());
    }

    @Test
    void deveLancarExcecaoParaQuantidadeNegativa() {
        DadoInvalidoException excecao = assertThrows(DadoInvalidoException.class,
                () -> new Quantidade(-5));
        assertEquals("Quantidade deve ser maior que zero", excecao.getMessage());
    }

    @Test
    void deveAceitarQuantidadeUm() {
        assertDoesNotThrow(() -> new Quantidade(1));
    }

    @Test
    void deveFormatarToStringComoInteiro() {
        Quantidade quantidade = new Quantidade(7);
        assertEquals("7", quantidade.toString());
    }

    @Test
    void deveFormatarToStringParaQuantidadeUm() {
        Quantidade quantidade = new Quantidade(1);
        assertEquals("1", quantidade.toString());
    }
}
