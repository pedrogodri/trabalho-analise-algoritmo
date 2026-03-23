package org.example.dominio.investidor;

import org.example.dominio.acao.QuantidadeAcao;
import org.example.dominio.empresa.NomeDaEmpresa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CarteiraTest {

    private Carteira carteira;
    private NomeDaEmpresa empresa;

    @BeforeEach
    void inicializar() {
        carteira = new Carteira();
        empresa = new NomeDaEmpresa("PetrobrasSA");
    }

    @Test
    void deveReceberAcoesDeUmaEmpresa() {
        carteira.receberAcoes(empresa, new QuantidadeAcao(100));
        assertTrue(carteira.possuiAcoesSuficientes(empresa, new QuantidadeAcao(100)));
    }

    @Test
    void deveAcumularAcoesEmMultiplasRecepcoes() {
        carteira.receberAcoes(empresa, new QuantidadeAcao(50));
        carteira.receberAcoes(empresa, new QuantidadeAcao(50));
        assertTrue(carteira.possuiAcoesSuficientes(empresa, new QuantidadeAcao(100)));
    }

    @Test
    void deveDeduzirAcoesComSaldoSuficiente() {
        carteira.receberAcoes(empresa, new QuantidadeAcao(100));
        carteira.deduzirAcoes(empresa, new QuantidadeAcao(40));
        assertTrue(carteira.possuiAcoesSuficientes(empresa, new QuantidadeAcao(60)));
        assertFalse(carteira.possuiAcoesSuficientes(empresa, new QuantidadeAcao(61)));
    }

    @Test
    void deveLancarExcecaoAoDeduzirSemSaldo() {
        assertThrows(IllegalStateException.class,
            () -> carteira.deduzirAcoes(empresa, new QuantidadeAcao(10)));
    }

    @Test
    void deveRetornarFalsoParaEmpresaNaoExistenteNaCarteira() {
        assertFalse(carteira.possuiAcoesSuficientes(empresa, new QuantidadeAcao(1)));
    }
}
