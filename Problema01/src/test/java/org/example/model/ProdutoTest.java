package org.example.model;

import org.example.exceptions.DadoInvalidoException;
import org.example.model.vo.NomeProduto;
import org.example.model.vo.PesoEmKg;
import org.example.model.vo.ValorMonetario;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class ProdutoTest {

    private Produto produto(String nome, float valor, float peso) {
        return new Produto(new NomeProduto(nome), new ValorMonetario(valor), new PesoEmKg(peso));
    }

    @Test
    void deveRetornarNomeComoString() {
        Produto p = produto("Clean Code", 49.90f, 0.4f);
        assertEquals("Clean Code", p.getNomeProduto().toString());
    }

    @Test
    void deveRetornarValorCorreto() {
        Produto p = produto("Livro A", 89.90f, 0.5f);
        assertEquals(89.90f, p.getValorMonetario().valor(), 0.001f);
    }

    @Test
    void deveRetornarPesoCorreto() {
        Produto p = produto("Livro B", 35.00f, 0.35f);
        assertEquals(0.35f, p.getPesoEmKg().valor(), 0.001f);
    }

    @Test
    void deveLancarExcecaoParaNomeNulo() {
        assertThrows(DadoInvalidoException.class,
                () -> new Produto(new NomeProduto(null), new ValorMonetario(10f), new PesoEmKg(0.3f)));
    }

    @Test
    void deveLancarExcecaoParaValorZero() {
        assertThrows(DadoInvalidoException.class,
                () -> produto("Livro", 0f, 0.3f));
    }

    @Test
    void deveLancarExcecaoParaValorNegativo() {
        assertThrows(DadoInvalidoException.class,
                () -> produto("Livro", -5f, 0.3f));
    }

    @Test
    void deveLancarExcecaoParaPesoZero() {
        assertThrows(DadoInvalidoException.class,
                () -> produto("Livro", 10f, 0f));
    }

    @Test
    void deveLancarExcecaoParaPesoNegativo() {
        assertThrows(DadoInvalidoException.class,
                () -> produto("Livro", 10f, -1f));
    }

    @Test
    void doisProdutosComMesmosDadosDevemSerIguais() {
        Produto p1 = produto("Clean Code", 49.90f, 0.4f);
        Produto p2 = produto("Clean Code", 49.90f, 0.4f);
        assertEquals(p1, p2);
    }

    @Test
    void doisProdutosComMesmosDadosDevemTerMesmoHashCode() {
        Produto p1 = produto("Clean Code", 49.90f, 0.4f);
        Produto p2 = produto("Clean Code", 49.90f, 0.4f);
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    void produtosComNomesDiferentesDevemSerDiferentes() {
        Produto p1 = produto("Livro A", 49.90f, 0.4f);
        Produto p2 = produto("Livro B", 49.90f, 0.4f);
        assertNotEquals(p1, p2);
    }

    @Test
    void deveRetornarToStringComTodosOsDados() {
        Produto p = produto("Clean Code", 49.90f, 0.4f);
        String resultado = p.toString();
        assertTrue(resultado.contains("Clean Code"));
        assertTrue(resultado.contains("49"));
        assertTrue(resultado.contains("kg"));
    }

    @Test
    void deveRetornarVONomeProduto() {
        Produto p = produto("Clean Code", 49.90f, 0.4f);
        assertEquals(new NomeProduto("Clean Code"), p.getNomeProduto());
    }

    @Test
    void deveRetornarVOValorMonetario() {
        Produto p = produto("Livro A", 89.90f, 0.5f);
        assertEquals(new ValorMonetario(89.90f), p.getValorMonetario());
    }

    @Test
    void deveRetornarVOPesoEmKg() {
        Produto p = produto("Livro B", 35.00f, 0.35f);
        assertEquals(new PesoEmKg(0.35f), p.getPesoEmKg());
    }
}
