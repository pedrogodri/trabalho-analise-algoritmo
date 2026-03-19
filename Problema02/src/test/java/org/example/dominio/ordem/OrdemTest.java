package org.example.dominio.ordem;

import org.example.dominio.acao.PrecoAcao;
import org.example.dominio.acao.QuantidadeAcao;
import org.example.dominio.investidor.Investidor;
import org.example.dominio.investidor.NomeDoInvestidor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrdemTest {

    private Investidor criarInvestidor(String nome) {
        return new Investidor(new NomeDoInvestidor(nome));
    }

    @Test
    void deveCriarOrdemDemCompraPendente() {
        OrdemDeCompra ordem = new OrdemDeCompra(
            criarInvestidor("Alice"), new PrecoAcao("35.00"), new QuantidadeAcao(100));
        assertTrue(ordem.estaPendente());
    }

    @Test
    void deveMudarStatusParaExecutadaAoDeduzirTodaQuantidade() {
        OrdemDeCompra ordem = new OrdemDeCompra(
            criarInvestidor("Alice"), new PrecoAcao("35.00"), new QuantidadeAcao(100));

        ordem.deduzirQuantidade(new QuantidadeAcao(100));

        assertFalse(ordem.estaPendente());
    }

    @Test
    void deveManterStatusPendenteAposDeduccaoParcial() {
        OrdemDeCompra ordem = new OrdemDeCompra(
            criarInvestidor("Alice"), new PrecoAcao("35.00"), new QuantidadeAcao(100));

        ordem.deduzirQuantidade(new QuantidadeAcao(60));

        assertTrue(ordem.estaPendente());
        assertEquals(new QuantidadeAcao(40), ordem.getQuantidadeRestante());
    }

    @Test
    void deveIdentificarDirecaoDaOrdem() {
        OrdemDeCompra compra = new OrdemDeCompra(
            criarInvestidor("Alice"), new PrecoAcao("35.00"), new QuantidadeAcao(100));
        OrdemDeVenda venda = new OrdemDeVenda(
            criarInvestidor("Dave"), new PrecoAcao("30.00"), new QuantidadeAcao(100));

        assertEquals(TipoOrdem.COMPRA, compra.getDirecao());
        assertEquals(TipoOrdem.VENDA, venda.getDirecao());
    }
}
