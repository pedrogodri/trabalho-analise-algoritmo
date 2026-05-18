package org.example.dominio.ordem;

import org.example.dominio.acao.PrecoAcao;
import org.example.dominio.acao.QuantidadeAcao;
import org.example.dominio.investidor.Investidor;
import org.example.dominio.investidor.NomeDoInvestidor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ListaDeOrdensTest {

    private Investidor investidor;

    @BeforeEach
    void inicializar() {
        investidor = new Investidor(new NomeDoInvestidor("Investidor"));
    }

    private OrdemDeCompra compra(String preco, int quantidade) {
        return new OrdemDeCompra(investidor, new PrecoAcao(preco), new QuantidadeAcao(quantidade));
    }

    private OrdemDeVenda venda(String preco, int quantidade) {
        return new OrdemDeVenda(investidor, new PrecoAcao(preco), new QuantidadeAcao(quantidade));
    }

    @Test
    void deveEstarVaziaAoCriar() {
        ListaDeOrdens lista = new ListaDeOrdens();
        assertTrue(lista.estaVazia());
    }

    @Test
    void naoDeveEstarVaziaAposAdicionar() {
        ListaDeOrdens lista = new ListaDeOrdens();
        lista.adicionar(compra("10.00", 100));
        assertFalse(lista.estaVazia());
    }

    @Test
    void deveConsiderarVaziaQuandoTodasOrdensForemExecutadas() {
        ListaDeOrdens lista = new ListaDeOrdens();
        OrdemDeCompra ordem = compra("10.00", 100);
        lista.adicionar(ordem);
        ordem.deduzirQuantidade(new QuantidadeAcao(100)); // marca como EXECUTADA
        assertTrue(lista.estaVazia());
    }

    @Test
    void deveRetornarApenasOrdensPendentesNoComoLista() {
        ListaDeOrdens lista = new ListaDeOrdens();
        OrdemDeCompra executada = compra("10.00", 50);
        OrdemDeCompra pendente = compra("20.00", 50);

        lista.adicionar(executada);
        lista.adicionar(pendente);
        executada.deduzirQuantidade(new QuantidadeAcao(50));

        List<Ordem> resultado = lista.comoLista();
        assertEquals(1, resultado.size());
        assertEquals(pendente, resultado.get(0));
    }

    @Test
    void deveOrdenarOrdensPorPrecoDecrescente() {
        ListaDeOrdens lista = new ListaDeOrdens();
        lista.adicionar(compra("10.00", 100));
        lista.adicionar(compra("30.00", 100));
        lista.adicionar(compra("20.00", 100));

        List<Ordem> ordenadas = lista.ordenadosPorPrecoDecrescente().comoLista();

        assertEquals(new PrecoAcao("30.00"), ordenadas.get(0).getPrecoAlvo());
        assertEquals(new PrecoAcao("20.00"), ordenadas.get(1).getPrecoAlvo());
        assertEquals(new PrecoAcao("10.00"), ordenadas.get(2).getPrecoAlvo());
    }

    @Test
    void deveEncontrarVendaComPrecoAteMáximo() {
        ListaDeOrdens lista = new ListaDeOrdens();
        OrdemDeVenda v = venda("30.00", 100);
        lista.adicionar(v);

        Optional<OrdemDeVenda> resultado = lista.encontrarVendaComPrecoAteMáximo(new PrecoAcao("35.00"));

        assertTrue(resultado.isPresent());
        assertEquals(v, resultado.get());
    }

    @Test
    void deveEncontrarVendaComPrecoExatamenteIgualAoMaximo() {
        ListaDeOrdens lista = new ListaDeOrdens();
        lista.adicionar(venda("35.00", 100));

        Optional<OrdemDeVenda> resultado = lista.encontrarVendaComPrecoAteMáximo(new PrecoAcao("35.00"));

        assertTrue(resultado.isPresent());
    }

    @Test
    void naoDeveEncontrarVendaQuandoPrecoSuperaMaximo() {
        ListaDeOrdens lista = new ListaDeOrdens();
        lista.adicionar(venda("40.00", 100));

        Optional<OrdemDeVenda> resultado = lista.encontrarVendaComPrecoAteMáximo(new PrecoAcao("35.00"));

        assertFalse(resultado.isPresent());
    }

    @Test
    void naoDeveEncontrarVendaEmListaVazia() {
        ListaDeOrdens lista = new ListaDeOrdens();

        Optional<OrdemDeVenda> resultado = lista.encontrarVendaComPrecoAteMáximo(new PrecoAcao("35.00"));

        assertFalse(resultado.isPresent());
    }

    @Test
    void naoDeveEncontrarVendaExecutadaComoPendente() {
        ListaDeOrdens lista = new ListaDeOrdens();
        OrdemDeVenda v = venda("30.00", 100);
        lista.adicionar(v);
        v.deduzirQuantidade(new QuantidadeAcao(100)); // executada

        Optional<OrdemDeVenda> resultado = lista.encontrarVendaComPrecoAteMáximo(new PrecoAcao("35.00"));

        assertFalse(resultado.isPresent());
    }

    @Test
    void ordenacaoNaoDeveIncluirOrdensExecutadas() {
        ListaDeOrdens lista = new ListaDeOrdens();
        OrdemDeCompra executada = compra("50.00", 100);
        lista.adicionar(executada);
        lista.adicionar(compra("20.00", 100));
        executada.deduzirQuantidade(new QuantidadeAcao(100));

        List<Ordem> ordenadas = lista.ordenadosPorPrecoDecrescente().comoLista();

        assertEquals(1, ordenadas.size());
        assertEquals(new PrecoAcao("20.00"), ordenadas.get(0).getPrecoAlvo());
    }
}
