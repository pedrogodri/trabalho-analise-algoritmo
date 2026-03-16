package org.example.implementation.entrega;

import org.example.model.vo.PesoEmKg;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SedexEntregaTest {

    private SedexEntrega sedex;

    @BeforeEach
    void setUp() {
        sedex = new SedexEntrega();
    }

    @Test
    void deveRetornar12Reais50ParaPesoAbaixoDe500g() {
        assertEquals(12.50, sedex.calcular(new PesoEmKg(0.3f)));
    }

    @Test
    void deveRetornar12Reais50ParaPesoExatamenteDe500g() {
        assertEquals(12.50, sedex.calcular(new PesoEmKg(0.5f)));
    }

    @Test
    void deveRetornar20ReaisParaPesoEntre500gE1Kg() {
        assertEquals(20.00, sedex.calcular(new PesoEmKg(0.8f)));
    }

    @Test
    void deveRetornar20ReaisParaPesoExatamenteDe1Kg() {
        assertEquals(20.00, sedex.calcular(new PesoEmKg(1.0f)));
    }

    @Test
    void deveRetornar48ReaisParaPesoDe1Kg100g() {
        // 1.1kg -> 1 grupo de 100g -> R$46,50 + R$1,50 = R$48,00
        assertEquals(48.00, sedex.calcular(new PesoEmKg(1.1f)), 0.01);
    }

    @Test
    void deveRetornar49Reais50ParaPesoDe1Kg200g() {
        // 1.2kg -> 2 grupos de 100g -> R$46,50 + R$3,00 = R$49,50
        assertEquals(49.50, sedex.calcular(new PesoEmKg(1.2f)), 0.01);
    }

    @Test
    void deveArredondarGruposParaCimaParaPesosFracionados() {
        // 1.15kg -> ceil(1.5) = 2 grupos -> R$46,50 + R$3,00 = R$49,50
        assertEquals(49.50, sedex.calcular(new PesoEmKg(1.15f)), 0.01);
    }

    @Test
    void deveCalcularFreteParaPesoMuitoAcimaDe1Kg() {
        // 3.0kg -> 2000g acima de 1kg -> ceil(2000/100) = 20 grupos -> R$46,50 + 20*R$1,50 = R$76,50
        assertEquals(76.50, sedex.calcular(new PesoEmKg(3.0f)), 0.01);
    }

    @Test
    void naoDeveLancarExcecaoParaQualquerPeso() {
        // Sedex não tem limite de peso
        assertDoesNotThrow(() -> sedex.calcular(new PesoEmKg(10.0f)));
        assertDoesNotThrow(() -> sedex.calcular(new PesoEmKg(50.0f)));
    }

    @Test
    void deveTerDescricaoCorreta() {
        assertEquals("Encomenda Sedex", sedex.descricao());
    }
}
