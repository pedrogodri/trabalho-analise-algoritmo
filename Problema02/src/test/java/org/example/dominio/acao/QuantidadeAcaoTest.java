package org.example.dominio.acao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class QuantidadeAcaoTest {

    @Test
    void deveCriarQuantidadePositiva() {
        QuantidadeAcao quantidade = new QuantidadeAcao(100);
        assertEquals(100, quantidade.getQuantidade());
    }

    @Test
    void deveLancarExcecaoParaZero() {
        assertThrows(IllegalArgumentException.class, () -> new QuantidadeAcao(0));
    }

    @Test
    void deveLancarExcecaoParaNegativo() {
        assertThrows(IllegalArgumentException.class, () -> new QuantidadeAcao(-1));
    }

    @Test
    void deveAdicionarQuantidades() {
        QuantidadeAcao cinquenta = new QuantidadeAcao(50);
        QuantidadeAcao cem = new QuantidadeAcao(100);
        assertEquals(new QuantidadeAcao(150), cinquenta.adicionar(cem));
    }

    @Test
    void deveSubtrairQuantidades() {
        QuantidadeAcao cem = new QuantidadeAcao(100);
        QuantidadeAcao quarenta = new QuantidadeAcao(40);
        assertEquals(new QuantidadeAcao(60), cem.subtrair(quarenta));
    }

    @Test
    void deveLancarExcecaoAoSubtrairMaisDoQueOTotal() {
        QuantidadeAcao cinquenta = new QuantidadeAcao(50);
        QuantidadeAcao cem = new QuantidadeAcao(100);
        assertThrows(IllegalArgumentException.class, () -> cinquenta.subtrair(cem));
    }

    @Test
    void deveRetornarMinimoEntreQuantidades() {
        QuantidadeAcao cem = new QuantidadeAcao(100);
        QuantidadeAcao sessenta = new QuantidadeAcao(60);
        assertEquals(sessenta, cem.minimo(sessenta));
    }

    @Test
    void deveLancarExcecaoAoSubtrairQuantidadeIgualAoTotal() {
        QuantidadeAcao cem = new QuantidadeAcao(100);
        assertThrows(IllegalArgumentException.class, () -> cem.subtrair(new QuantidadeAcao(100)));
    }

    @Test
    void deveConsiderarQuantidadesIguaisComoIguais() {
        QuantidadeAcao q1 = new QuantidadeAcao(50);
        QuantidadeAcao q2 = new QuantidadeAcao(50);

        assertEquals(q1, q2);
        assertEquals(q1.hashCode(), q2.hashCode());
    }

    @Test
    void deveRetornarQuantidadeComoTexto() {
        QuantidadeAcao quantidade = new QuantidadeAcao(120);
        assertEquals("120", quantidade.toString());
    }

    @Test
    void deveRetornarVerdadeiroParaMaiorOuIgualQuandoMaior() {
        QuantidadeAcao cem = new QuantidadeAcao(100);
        QuantidadeAcao cinquenta = new QuantidadeAcao(50);
        assertTrue(cem.eMaiorOuIgualA(cinquenta));
    }

    @Test
    void deveRetornarVerdadeiroParaMaiorOuIgualQuandoIgual() {
        QuantidadeAcao q1 = new QuantidadeAcao(100);
        QuantidadeAcao q2 = new QuantidadeAcao(100);
        assertTrue(q1.eMaiorOuIgualA(q2));
    }

    @Test
    void deveRetornarFalsoParaMaiorOuIgualQuandoMenor() {
        QuantidadeAcao cinquenta = new QuantidadeAcao(50);
        QuantidadeAcao cem = new QuantidadeAcao(100);
        assertFalse(cinquenta.eMaiorOuIgualA(cem));
    }

    @Test
    void deveCompararQuantidadesCorretamente() {
        QuantidadeAcao menor = new QuantidadeAcao(10);
        QuantidadeAcao maior = new QuantidadeAcao(20);

        assertTrue(menor.compareTo(maior) < 0);
        assertTrue(maior.compareTo(menor) > 0);
        assertEquals(0, menor.compareTo(new QuantidadeAcao(10)));
    }

    @Test
    void deveRetornarMinimoQuandoPrimeiraMenor() {
        QuantidadeAcao dez = new QuantidadeAcao(10);
        QuantidadeAcao cem = new QuantidadeAcao(100);
        assertEquals(dez, dez.minimo(cem));
    }
}
