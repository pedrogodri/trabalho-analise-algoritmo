package org.example.dominio.investidor;

import org.example.dominio.acao.PrecoAcao;
import org.example.dominio.acao.QuantidadeAcao;
import org.example.dominio.empresa.NomeDaEmpresa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InvestidorTest {

    private Investidor investidor;
    private NomeDaEmpresa empresa;

    @BeforeEach
    void inicializar() {
        investidor = new Investidor(new NomeDoInvestidor("Alice"));
        empresa = new NomeDaEmpresa("PetrobrasSA");
    }

    @Test
    void deveRetornarNomeCompleto() {
        assertEquals("Alice", investidor.getNomeCompleto());
    }

    @Test
    void deveRetornarNomeComoObjeto() {
        assertEquals(new NomeDoInvestidor("Alice"), investidor.getNome());
    }

    @Test
    void deveReceberAcoes() {
        investidor.receberAcoes(empresa, new QuantidadeAcao(100));
        assertTrue(investidor.possuiAcoesSuficientes(empresa, new QuantidadeAcao(100)));
    }

    @Test
    void deveDeduzirAcoes() {
        investidor.receberAcoes(empresa, new QuantidadeAcao(100));
        investidor.deduzirAcoes(empresa, new QuantidadeAcao(40));
        assertTrue(investidor.possuiAcoesSuficientes(empresa, new QuantidadeAcao(60)));
        assertFalse(investidor.possuiAcoesSuficientes(empresa, new QuantidadeAcao(61)));
    }

    @Test
    void naoDevePossuirAcoesSemReceberPrimeiro() {
        assertFalse(investidor.possuiAcoesSuficientes(empresa, new QuantidadeAcao(1)));
    }

    @Test
    void deveAcumularAcoesDeMultiplasReceitas() {
        investidor.receberAcoes(empresa, new QuantidadeAcao(50));
        investidor.receberAcoes(empresa, new QuantidadeAcao(50));
        assertTrue(investidor.possuiAcoesSuficientes(empresa, new QuantidadeAcao(100)));
    }

    @Test
    void deveReceberNotificacaoDePreco() {
        // Verifica que o método não lança exceção (saída vai para console)
        List<String> erros = new ArrayList<>();
        try {
            investidor.aoAtualizarPreco(empresa, new PrecoAcao("35.00"));
        } catch (Exception e) {
            erros.add(e.getMessage());
        }
        assertTrue(erros.isEmpty());
    }
}
