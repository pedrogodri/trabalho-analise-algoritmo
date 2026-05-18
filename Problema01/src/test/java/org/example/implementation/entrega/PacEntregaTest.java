package org.example.implementation.entrega;

import org.example.exceptions.EntregaNaoDisponivelException;
import org.example.model.vo.PesoEmKg;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PacEntregaTest {

    private PacEntrega pac;

    @BeforeEach
    void setUp() {
        pac = new PacEntrega();
    }

    @Test
    void deveRetornar10ReaisParaPesoAbaixoDe1Kg() {
        assertEquals(10.00, pac.calcular(new PesoEmKg(0.8f)));
    }

    @Test
    void deveRetornar10ReaisParaPesoExatamenteDe1Kg() {
        assertEquals(10.00, pac.calcular(new PesoEmKg(1.0f)));
    }

    @Test
    void deveRetornar15ReaisParaPesoEntre1KgE2Kg() {
        assertEquals(15.00, pac.calcular(new PesoEmKg(1.5f)));
    }

    @Test
    void deveRetornar15ReaisParaPesoExatamenteDe2Kg() {
        assertEquals(15.00, pac.calcular(new PesoEmKg(2.0f)));
    }

    @Test
    void deveLancarExcecaoParaPesoLigeiramenteAcimaDe2Kg() {
        assertThrows(EntregaNaoDisponivelException.class, () -> pac.calcular(new PesoEmKg(2.001f)));
    }

    @Test
    void deveLancarExcecaoParaPesoBemAcimaDe2Kg() {
        assertThrows(EntregaNaoDisponivelException.class, () -> pac.calcular(new PesoEmKg(5.0f)));
    }

    @Test
    void deveLancarExcecaoComMensagemCorreta() {
        EntregaNaoDisponivelException excecao = assertThrows(EntregaNaoDisponivelException.class,
                () -> pac.calcular(new PesoEmKg(3.0f)));
        assertEquals("PAC nao aceita pedidos acima de 2kg", excecao.getMessage());
    }

    @Test
    void deveTerDescricaoCorreta() {
        assertEquals("Encomenda PAC", pac.descricao());
    }
}
