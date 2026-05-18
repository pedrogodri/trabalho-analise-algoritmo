package org.example.util;

import org.example.exceptions.DadoInvalidoException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class ValidacaoTest {

    @Test
    void deveRetornarValorValidoSemAlteracoes() {
        assertEquals("Clean Code", Validacao.validarObrigatorio("Clean Code", "Campo"));
    }

    @Test
    void deveRemoverEspacosDoInicio() {
        assertEquals("Valor", Validacao.validarObrigatorio("   Valor", "Campo"));
    }

    @Test
    void deveRemoverEspacosDoFim() {
        assertEquals("Valor", Validacao.validarObrigatorio("Valor   ", "Campo"));
    }

    @Test
    void deveRemoverEspacosDeAmbosBordos() {
        assertEquals("Valor", Validacao.validarObrigatorio("  Valor  ", "Campo"));
    }

    @Test
    void deveLancarExcecaoParaValorNulo() {
        DadoInvalidoException excecao = assertThrows(DadoInvalidoException.class,
                () -> Validacao.validarObrigatorio(null, "Nome do produto"));
        assertEquals("Nome do produto não pode ser vazio", excecao.getMessage());
    }

    @Test
    void deveLancarExcecaoParaValorVazio() {
        DadoInvalidoException excecao = assertThrows(DadoInvalidoException.class,
                () -> Validacao.validarObrigatorio("", "Logradouro"));
        assertEquals("Logradouro não pode ser vazio", excecao.getMessage());
    }

    @Test
    void deveLancarExcecaoParaValorSoComEspacos() {
        DadoInvalidoException excecao = assertThrows(DadoInvalidoException.class,
                () -> Validacao.validarObrigatorio("   ", "Cidade"));
        assertEquals("Cidade não pode ser vazio", excecao.getMessage());
    }

    @Test
    void deveLancarExcecaoComNomeDoCampoCorretoNaMensagem() {
        DadoInvalidoException excecao = assertThrows(DadoInvalidoException.class,
                () -> Validacao.validarObrigatorio(null, "CEP"));
        assertTrue(excecao.getMessage().startsWith("CEP"));
    }
}
