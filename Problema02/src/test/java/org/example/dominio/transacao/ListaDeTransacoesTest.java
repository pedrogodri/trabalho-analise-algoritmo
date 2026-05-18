package org.example.dominio.transacao;

import org.example.dominio.acao.PrecoAcao;
import org.example.dominio.acao.QuantidadeAcao;
import org.example.dominio.empresa.NomeDaEmpresa;
import org.example.dominio.investidor.Investidor;
import org.example.dominio.investidor.NomeDoInvestidor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ListaDeTransacoesTest {

    private ListaDeTransacoes lista;
    private Investidor comprador;
    private Investidor vendedor;
    private NomeDaEmpresa empresa;

    @BeforeEach
    void inicializar() {
        lista = new ListaDeTransacoes();
        comprador = new Investidor(new NomeDoInvestidor("Alice"));
        vendedor = new Investidor(new NomeDoInvestidor("Dave"));
        empresa = new NomeDaEmpresa("PetrobrasSA");
    }

    private Transacao criarTransacao(String preco, int quantidade) {
        return new Transacao(comprador, vendedor, empresa,
                new PrecoAcao(preco), new QuantidadeAcao(quantidade));
    }

    @Test
    void deveIniciarComTotalZero() {
        assertEquals(0, lista.getTotal());
    }

    @Test
    void deveIncrementarTotalAoRegistrar() {
        lista.registrar(criarTransacao("35.00", 100));
        assertEquals(1, lista.getTotal());
    }

    @Test
    void deveAcumularMultiplasTransacoes() {
        lista.registrar(criarTransacao("35.00", 100));
        lista.registrar(criarTransacao("38.00", 50));
        lista.registrar(criarTransacao("20.00", 200));
        assertEquals(3, lista.getTotal());
    }

    @Test
    void deveRegistrarSemLancarExcecao() {
        assertDoesNotThrow(() -> lista.registrar(criarTransacao("30.00", 100)));
    }
}
