package org.example.model.vo;

import org.example.exceptions.DadoInvalidoException;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class EstadoTest {

    @Test
    void deveAceitarSiglaValida() {
        assertDoesNotThrow(() -> new Estado("SC"));
    }

    @Test
    void deveNormalizarSiglaParaMaiusculo() {
        Estado estado = new Estado("sc");
        assertEquals("SC", estado.toString());
    }

    @Test
    void deveAceitarSiglaJaEmMaiusculo() {
        Estado estado = new Estado("SP");
        assertEquals("SP", estado.toString());
    }

    @Test
    void deveAceitarSiglaMista() {
        Estado estado = new Estado("Rj");
        assertEquals("RJ", estado.toString());
    }

    @Test
    void deveLancarExcecaoParaSiglaNula() {
        DadoInvalidoException excecao = assertThrows(DadoInvalidoException.class,
                () -> new Estado(null));
        assertEquals("Estado não pode ser vazio", excecao.getMessage());
    }

    @Test
    void deveLancarExcecaoParaSiglaVazia() {
        DadoInvalidoException excecao = assertThrows(DadoInvalidoException.class,
                () -> new Estado(""));
        assertEquals("Estado não pode ser vazio", excecao.getMessage());
    }

    @Test
    void deveLancarExcecaoParaSiglaComUmaLetra() {
        DadoInvalidoException excecao = assertThrows(DadoInvalidoException.class,
                () -> new Estado("S"));
        assertTrue(excecao.getMessage().contains("2 letras"));
    }

    @Test
    void deveLancarExcecaoParaSiglaComTresLetras() {
        DadoInvalidoException excecao = assertThrows(DadoInvalidoException.class,
                () -> new Estado("SCA"));
        assertTrue(excecao.getMessage().contains("2 letras"));
    }

    @Test
    void deveLancarExcecaoParaSiglaComNumeros() {
        assertThrows(DadoInvalidoException.class, () -> new Estado("S1"));
    }

    @Test
    void deveLancarExcecaoParaSiglaSoComEspacos() {
        assertThrows(DadoInvalidoException.class, () -> new Estado("   "));
    }
}
