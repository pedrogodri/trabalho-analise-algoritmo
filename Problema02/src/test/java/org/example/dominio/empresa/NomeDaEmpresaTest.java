package org.example.dominio.empresa;

import org.example.dominio.investidor.NomeDoInvestidor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testa NomeDaEmpresa e, por extensão, o comportamento herdado de Nome:
 * validação, igualdade case-insensitive, hashCode e isolamento de tipo.
 */
class NomeDaEmpresaTest {

    @Test
    void deveCriarNomeValido() {
        NomeDaEmpresa nome = new NomeDaEmpresa("PetrobrasSA");
        assertEquals("PetrobrasSA", nome.getNome());
    }

    @Test
    void deveAplicarTrimAoNome() {
        NomeDaEmpresa nome = new NomeDaEmpresa("  ValeSA  ");
        assertEquals("ValeSA", nome.getNome());
    }

    @Test
    void deveLancarExcecaoParaNomeNulo() {
        assertThrows(IllegalArgumentException.class, () -> new NomeDaEmpresa(null));
    }

    @Test
    void deveLancarExcecaoParaNomeVazio() {
        assertThrows(IllegalArgumentException.class, () -> new NomeDaEmpresa(""));
    }

    @Test
    void deveLancarExcecaoParaNomeSomenteEspacos() {
        assertThrows(IllegalArgumentException.class, () -> new NomeDaEmpresa("   "));
    }

    @Test
    void deveSerIgualComMesmaCaix() {
        NomeDaEmpresa a = new NomeDaEmpresa("PetrobrasSA");
        NomeDaEmpresa b = new NomeDaEmpresa("PetrobrasSA");
        assertEquals(a, b);
    }

    @Test
    void deveSerIgualIgnorandoCaixaDaLetra() {
        NomeDaEmpresa a = new NomeDaEmpresa("petrobras");
        NomeDaEmpresa b = new NomeDaEmpresa("PETROBRAS");
        assertEquals(a, b);
    }

    @Test
    void naoDeveSerIgualComNomesDiferentes() {
        NomeDaEmpresa a = new NomeDaEmpresa("PetrobrasSA");
        NomeDaEmpresa b = new NomeDaEmpresa("ValeSA");
        assertNotEquals(a, b);
    }

    @Test
    void naoDeveSerIgualANomeDoInvestidorMesmoComTextoIgual() {
        NomeDaEmpresa empresa = new NomeDaEmpresa("Alice");
        NomeDoInvestidor investidor = new NomeDoInvestidor("Alice");
        assertNotEquals(empresa, investidor);
    }

    @Test
    void deveGerarMesmoHashCodeParaNomesIguaisCaseInsensitive() {
        NomeDaEmpresa a = new NomeDaEmpresa("vale");
        NomeDaEmpresa b = new NomeDaEmpresa("VALE");
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void deveRetornarNomeNoToString() {
        NomeDaEmpresa nome = new NomeDaEmpresa("PetrobrasSA");
        assertEquals("PetrobrasSA", nome.toString());
    }
}
