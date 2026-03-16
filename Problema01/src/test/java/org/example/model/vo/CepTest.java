package org.example.model.vo;

import org.example.exceptions.DadoInvalidoException;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class CepTest {

    @Test
    void deveAceitarCepNoFormatoComHifen() {
        assertDoesNotThrow(() -> new Cep("89010-000"));
    }

    @Test
    void deveAceitarCepNoFormatoSemHifen() {
        assertDoesNotThrow(() -> new Cep("89010000"));
    }

    @Test
    void deveNormalizarCepParaFormatoComHifen() {
        Cep cep = new Cep("89010000");
        assertEquals("89010-000", cep.toString());
    }

    @Test
    void deveNormalizarCepComHifenParaFormatoComHifen() {
        Cep cep = new Cep("89010-000");
        assertEquals("89010-000", cep.toString());
    }

    @Test
    void deveLancarExcecaoParaCepNulo() {
        DadoInvalidoException excecao = assertThrows(DadoInvalidoException.class,
                () -> new Cep(null));
        assertEquals("CEP não pode ser vazio", excecao.getMessage());
    }

    @Test
    void deveLancarExcecaoParaCepVazio() {
        DadoInvalidoException excecao = assertThrows(DadoInvalidoException.class,
                () -> new Cep(""));
        assertEquals("CEP não pode ser vazio", excecao.getMessage());
    }

    @Test
    void deveLancarExcecaoParaCepComMenosDe8Digitos() {
        DadoInvalidoException excecao = assertThrows(DadoInvalidoException.class,
                () -> new Cep("1234567"));
        assertTrue(excecao.getMessage().contains("8 digitos"));
    }

    @Test
    void deveLancarExcecaoParaCepComMaisDe8Digitos() {
        DadoInvalidoException excecao = assertThrows(DadoInvalidoException.class,
                () -> new Cep("123456789"));
        assertTrue(excecao.getMessage().contains("8 digitos"));
    }

    @Test
    void deveLancarExcecaoParaCepComLetras() {
        assertThrows(DadoInvalidoException.class, () -> new Cep("ABCDE-FGH"));
    }

    @Test
    void deveAceitarCepComZerosAEsquerda() {
        Cep cep = new Cep("01310100");
        assertEquals("01310-100", cep.toString());
    }
}
