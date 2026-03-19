package org.example.mediador;

import java.util.Optional;

import org.example.dominio.acao.PrecoAcao;
import org.example.dominio.acao.QuantidadeAcao;
import org.example.dominio.investidor.Investidor;
import org.example.dominio.investidor.NomeDoInvestidor;
import org.example.dominio.ordem.ListaDeOrdens;
import org.example.dominio.ordem.OrdemDeCompra;
import org.example.dominio.ordem.OrdemDeVenda;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Testa o algoritmo de combinação de ordens:
 * - preço de compra >= preço de venda → deve encontrar par
 * - preço de compra < preço de venda → não deve encontrar par
 */
class CombinadorDeOrdensTest {

    private CombinadorDeOrdens combinador;
    private Investidor comprador;
    private Investidor vendedor;

    @BeforeEach
    void inicializar() {
        combinador = new CombinadorDeOrdens();
        comprador = new Investidor(new NomeDoInvestidor("Comprador"));
        vendedor = new Investidor(new NomeDoInvestidor("Vendedor"));
    }

    @Test
    void deveEncontrarCombinacaoQuandoCompradorPagaMaisQueVendedor() {
        ListaDeOrdens compras = new ListaDeOrdens();
        ListaDeOrdens vendas = new ListaDeOrdens();

        compras.adicionar(new OrdemDeCompra(comprador, new PrecoAcao("35.00"), new QuantidadeAcao(100)));
        vendas.adicionar(new OrdemDeVenda(vendedor, new PrecoAcao("30.00"), new QuantidadeAcao(100)));

        Optional<ParDeOrdens> resultado = combinador.encontrarCombinacao(compras, vendas);
        assertTrue(resultado.isPresent());
    }

    @Test
    void deveEncontrarCombinacaoQuandoPrecosSaoIguais() {
        ListaDeOrdens compras = new ListaDeOrdens();
        ListaDeOrdens vendas = new ListaDeOrdens();

        compras.adicionar(new OrdemDeCompra(comprador, new PrecoAcao("38.00"), new QuantidadeAcao(50)));
        vendas.adicionar(new OrdemDeVenda(vendedor, new PrecoAcao("38.00"), new QuantidadeAcao(50)));

        Optional<ParDeOrdens> resultado = combinador.encontrarCombinacao(compras, vendas);
        assertTrue(resultado.isPresent());
    }

    @Test
    void naoDeveEncontrarCombinacaoQuandoCompradorPagaMenosQueVendedor() {
        ListaDeOrdens compras = new ListaDeOrdens();
        ListaDeOrdens vendas = new ListaDeOrdens();

        compras.adicionar(new OrdemDeCompra(comprador, new PrecoAcao("29.00"), new QuantidadeAcao(100)));
        vendas.adicionar(new OrdemDeVenda(vendedor, new PrecoAcao("30.00"), new QuantidadeAcao(100)));

        Optional<ParDeOrdens> resultado = combinador.encontrarCombinacao(compras, vendas);
        assertFalse(resultado.isPresent());
    }

    @Test
    void naoDeveEncontrarCombinacaoComListasVazias() {
        ListaDeOrdens compras = new ListaDeOrdens();
        ListaDeOrdens vendas = new ListaDeOrdens();

        Optional<ParDeOrdens> resultado = combinador.encontrarCombinacao(compras, vendas);
        assertFalse(resultado.isPresent());
    }

    @Test
    void deveEscolherMaiorCompradorPrimeiro() {
        ListaDeOrdens compras = new ListaDeOrdens();
        ListaDeOrdens vendas = new ListaDeOrdens();

        Investidor compradorA = new Investidor(new NomeDoInvestidor("CompradorA"));
        Investidor compradorB = new Investidor(new NomeDoInvestidor("CompradorB"));

        // CompradorB oferece mais — deve ser o escolhido para combinar
        compras.adicionar(new OrdemDeCompra(compradorA, new PrecoAcao("25.00"), new QuantidadeAcao(100)));
        compras.adicionar(new OrdemDeCompra(compradorB, new PrecoAcao("32.00"), new QuantidadeAcao(100)));
        vendas.adicionar(new OrdemDeVenda(vendedor, new PrecoAcao("30.00"), new QuantidadeAcao(100)));

        Optional<ParDeOrdens> resultado = combinador.encontrarCombinacao(compras, vendas);
        assertTrue(resultado.isPresent());
        assertEquals("CompradorB",
            resultado.get().getOrdemDeCompra().getInvestidor().getNome().getNome());
    }
}
