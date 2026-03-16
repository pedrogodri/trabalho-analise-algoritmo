package org.example.model.vo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.example.exceptions.DadoInvalidoException;
import org.example.model.vo.NomeProduto;

class NomeProdutoTest {

    @Test
    void deveAceitarNomeValido() {
        NomeProduto nome = new NomeProduto("Clean Code");
        assertEquals("Clean Code", nome.toString());
    }

    @Test
    void deveFazerTrimDoNome() {
        NomeProduto nome = new NomeProduto("  Clean Code  ");
        assertEquals("Clean Code", nome.toString());
    }

    @Test
    void deveLancarExcecaoParaNomeNulo() {
        DadoInvalidoException excecao = assertThrows(DadoInvalidoException.class,
                () -> new NomeProduto(null));
        assertEquals("Nome do produto não pode ser vazio", excecao.getMessage());
    }

    @Test
    void deveLancarExcecaoParaNomeVazio() {
        DadoInvalidoException excecao = assertThrows(DadoInvalidoException.class,
                () -> new NomeProduto(""));
        assertEquals("Nome do produto não pode ser vazio", excecao.getMessage());
    }

    @Test
    void deveLancarExcecaoParaNomeSoComEspacos() {
        DadoInvalidoException excecao = assertThrows(DadoInvalidoException.class,
                () -> new NomeProduto("   "));
        assertEquals("Nome do produto não pode ser vazio", excecao.getMessage());
    }

    @Test
    void deveLancarExcecaoParaNomeAcimaDe100Caracteres() {
        String nomeGigante = "A".repeat(101);
        DadoInvalidoException excecao = assertThrows(DadoInvalidoException.class,
                () -> new NomeProduto(nomeGigante));
        assertTrue(excecao.getMessage().contains("100 caracteres"));
    }

    @Test
    void deveAceitarNomeComExatamente100Caracteres() {
        String nomeExato = "A".repeat(100);
        assertDoesNotThrow(() -> new NomeProduto(nomeExato));
    }

    @Test
    void deveRetornarToStringIgualAoNomeInformado() {
        NomeProduto nome = new NomeProduto("Dom Casmurro");
        assertEquals("Dom Casmurro", nome.toString());
    }

    @Test
    void deveRetornarNomeSemEspacosExtrasNoToString() {
        NomeProduto nome = new NomeProduto("  Livro Trim  ");
        assertEquals("Livro Trim", nome.toString());
    }
}
