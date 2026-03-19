package org.example.dominio.acao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
}
