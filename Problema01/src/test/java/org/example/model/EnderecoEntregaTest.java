package org.example.model;

import org.example.exceptions.DadoInvalidoException;
import org.example.model.vo.Cep;
import org.example.model.vo.Estado;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class EnderecoEntregaTest {

    private EnderecoEntrega endereco(String rua, String numero, String complemento, String cidade) {
        return new EnderecoEntrega(
                new Cep("89010000"),
                rua,
                numero,
                complemento,
                cidade,
                new Estado("SC")
        );
    }

    @Test
    void deveFormatarLogradouroSemComplemento() {
        EnderecoEntrega e = endereco("Rua das Flores", "123", "", "Blumenau");
        assertEquals("Rua das Flores, 123", e.formatarLogradouro());
    }

    @Test
    void deveFormatarLogradouroComComplemento() {
        EnderecoEntrega e = endereco("Rua das Flores", "123", "Apto 4", "Blumenau");
        assertEquals("Rua das Flores, 123 (Apto 4)", e.formatarLogradouro());
    }

    @Test
    void deveFormatarLogradouroComComplementoNulo() {
        EnderecoEntrega e = endereco("Rua XV de Novembro", "500", null, "Curitiba");
        assertEquals("Rua XV de Novembro, 500", e.formatarLogradouro());
    }

    @Test
    void deveFormatarLogradouroComComplementoSoEspacos() {
        EnderecoEntrega e = endereco("Av. Brasil", "10", "   ", "Joinville");
        assertEquals("Av. Brasil, 10", e.formatarLogradouro());
    }

    @Test
    void deveConcatenarCepCidadeEstado() {
        EnderecoEntrega e = endereco("Rua A", "1", "", "Blumenau");
        assertEquals("89010-000 - Blumenau/SC", e.concatenarCepCidadeEstado());
    }

    @Test
    void deveLancarExcecaoParaRuaVazia() {
        assertThrows(DadoInvalidoException.class,
                () -> endereco("", "123", "", "Blumenau"));
    }

    @Test
    void deveLancarExcecaoParaRuaNula() {
        assertThrows(DadoInvalidoException.class,
                () -> endereco(null, "123", "", "Blumenau"));
    }

    @Test
    void deveLancarExcecaoParaNumeroVazio() {
        assertThrows(DadoInvalidoException.class,
                () -> endereco("Rua A", "", "", "Blumenau"));
    }

    @Test
    void deveLancarExcecaoParaCidadeVazia() {
        assertThrows(DadoInvalidoException.class,
                () -> endereco("Rua A", "1", "", ""));
    }

    @Test
    void doisEnderecosIguaisDevemSerIguais() {
        EnderecoEntrega e1 = endereco("Rua das Flores", "123", "Apto 4", "Blumenau");
        EnderecoEntrega e2 = endereco("Rua das Flores", "123", "Apto 4", "Blumenau");
        assertEquals(e1, e2);
    }

    @Test
    void doisEnderecosIguaisDevemTerMesmoHashCode() {
        EnderecoEntrega e1 = endereco("Rua das Flores", "123", "", "Blumenau");
        EnderecoEntrega e2 = endereco("Rua das Flores", "123", "", "Blumenau");
        assertEquals(e1.hashCode(), e2.hashCode());
    }

    @Test
    void enderecosComRuasDiferentesDevemSerDiferentes() {
        assertNotEquals(
                endereco("Rua A", "1", "", "Blumenau"),
                endereco("Rua B", "1", "", "Blumenau")
        );
    }

    @Test
    void deveRetornarToStringComLogradouroECepCidadeEstado() {
        EnderecoEntrega e = endereco("Rua das Flores", "123", "", "Blumenau");
        String resultado = e.toString();
        assertTrue(resultado.contains("Rua das Flores"));
        assertTrue(resultado.contains("89010-000"));
        assertTrue(resultado.contains("Blumenau"));
        assertTrue(resultado.contains("SC"));
    }

    @Test
    void deveRetornarVOCep() {
        EnderecoEntrega e = endereco("Rua A", "1", "", "Blumenau");
        assertEquals(new Cep("89010-000"), e.getCep());
    }

    @Test
    void deveRetornarVOEstado() {
        EnderecoEntrega e = endereco("Rua A", "1", "", "Blumenau");
        assertEquals(new Estado("SC"), e.getEstado());
    }
}
