package org.example.mediador;

import org.example.dominio.acao.PrecoAcao;
import org.example.dominio.acao.QuantidadeAcao;
import org.example.dominio.empresa.NomeDaEmpresa;
import org.example.dominio.investidor.Investidor;
import org.example.dominio.investidor.NomeDoInvestidor;
import org.example.dominio.ordem.OrdemDeCompra;
import org.example.dominio.ordem.OrdemDeVenda;
import org.example.excecao.OrdemInvalidaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LivroDeOrdensTest {

    private FakeEmpresa empresa;
    private LivroDeOrdens livro;
    private Investidor comprador;
    private Investidor vendedor;

    @BeforeEach
    void inicializar() {
        empresa = new FakeEmpresa("EmpresaTeste");
        livro = new LivroDeOrdens(empresa);

        comprador = new Investidor(new NomeDoInvestidor("Comprador"));
        vendedor = new Investidor(new NomeDoInvestidor("Vendedor"));
    }

    @Test
    void deveExecutarTransacaoQuandoHouverCombinacao() {
        vendedor.receberAcoes(empresa.obterNome(), new QuantidadeAcao(100));

        livro.registrarOrdemDeVenda(
                new OrdemDeVenda(vendedor, new PrecoAcao("30.00"), new QuantidadeAcao(100))
        );
        livro.registrarOrdemDeCompra(
                new OrdemDeCompra(comprador, new PrecoAcao("35.00"), new QuantidadeAcao(100))
        );

        assertTrue(empresa.possuiPrecoEstabelecido());
        assertEquals(new PrecoAcao("35.00"), empresa.getPrecoAtual());

        assertTrue(comprador.possuiAcoesSuficientes(empresa.obterNome(), new QuantidadeAcao(100)));
        assertFalse(vendedor.possuiAcoesSuficientes(empresa.obterNome(), new QuantidadeAcao(1)));
    }

    @Test
    void naoDeveExecutarTransacaoQuandoNaoHouverCombinacao() {
        vendedor.receberAcoes(empresa.obterNome(), new QuantidadeAcao(100));

        livro.registrarOrdemDeVenda(
                new OrdemDeVenda(vendedor, new PrecoAcao("40.00"), new QuantidadeAcao(100))
        );
        livro.registrarOrdemDeCompra(
                new OrdemDeCompra(comprador, new PrecoAcao("30.00"), new QuantidadeAcao(100))
        );

        assertFalse(empresa.possuiPrecoEstabelecido());
        assertFalse(comprador.possuiAcoesSuficientes(empresa.obterNome(), new QuantidadeAcao(1)));
        assertTrue(vendedor.possuiAcoesSuficientes(empresa.obterNome(), new QuantidadeAcao(100)));
    }

    @Test
    void deveLancarExcecaoAoRegistrarVendaSemSaldoSuficiente() {
        OrdemDeVenda ordemDeVenda = new OrdemDeVenda(
                vendedor,
                new PrecoAcao("30.00"),
                new QuantidadeAcao(100)
        );

        assertThrows(OrdemInvalidaException.class,
                () -> livro.registrarOrdemDeVenda(ordemDeVenda));
    }

    @Test
    void deveExecutarTransacaoParcialQuandoQuantidadesForemDiferentes() {
        vendedor.receberAcoes(empresa.obterNome(), new QuantidadeAcao(100));

        livro.registrarOrdemDeVenda(
                new OrdemDeVenda(vendedor, new PrecoAcao("30.00"), new QuantidadeAcao(100))
        );
        livro.registrarOrdemDeCompra(
                new OrdemDeCompra(comprador, new PrecoAcao("35.00"), new QuantidadeAcao(40))
        );

        assertTrue(comprador.possuiAcoesSuficientes(empresa.obterNome(), new QuantidadeAcao(40)));
        assertTrue(vendedor.possuiAcoesSuficientes(empresa.obterNome(), new QuantidadeAcao(60)));
        assertFalse(vendedor.possuiAcoesSuficientes(empresa.obterNome(), new QuantidadeAcao(61)));
    }

    @Test
    void deveProcessarMultiplasCombinacoesEmSequencia() {
        Investidor comprador2 = new Investidor(new NomeDoInvestidor("Comprador2"));
        Investidor vendedor2 = new Investidor(new NomeDoInvestidor("Vendedor2"));

        vendedor.receberAcoes(empresa.obterNome(), new QuantidadeAcao(100));
        vendedor2.receberAcoes(empresa.obterNome(), new QuantidadeAcao(100));

        livro.registrarOrdemDeVenda(
                new OrdemDeVenda(vendedor, new PrecoAcao("30.00"), new QuantidadeAcao(50))
        );
        livro.registrarOrdemDeVenda(
                new OrdemDeVenda(vendedor2, new PrecoAcao("28.00"), new QuantidadeAcao(40))
        );

        livro.registrarOrdemDeCompra(
                new OrdemDeCompra(comprador, new PrecoAcao("35.00"), new QuantidadeAcao(50))
        );
        livro.registrarOrdemDeCompra(
                new OrdemDeCompra(comprador2, new PrecoAcao("32.00"), new QuantidadeAcao(40))
        );

        assertTrue(comprador.possuiAcoesSuficientes(empresa.obterNome(), new QuantidadeAcao(50)));
        assertTrue(comprador2.possuiAcoesSuficientes(empresa.obterNome(), new QuantidadeAcao(40)));

        assertFalse(vendedor.possuiAcoesSuficientes(empresa.obterNome(), new QuantidadeAcao(51)));
        assertFalse(vendedor2.possuiAcoesSuficientes(empresa.obterNome(), new QuantidadeAcao(61)));

        assertTrue(empresa.possuiPrecoEstabelecido());
    }

    @Test
    void deveUsarPrecoDoCompradorNaPrimeiraTransacao() {
        vendedor.receberAcoes(empresa.obterNome(), new QuantidadeAcao(100));

        livro.registrarOrdemDeVenda(
                new OrdemDeVenda(vendedor, new PrecoAcao("30.00"), new QuantidadeAcao(100))
        );
        livro.registrarOrdemDeCompra(
                new OrdemDeCompra(comprador, new PrecoAcao("35.00"), new QuantidadeAcao(100))
        );

        assertEquals(new PrecoAcao("35.00"), empresa.getPrecoAtual());
    }

    @Test
    void deveUsarPrecoDoVendedorNasTransacoesSubsequentes() {
        Investidor comprador2 = new Investidor(new NomeDoInvestidor("Comprador2"));
        Investidor vendedor2 = new Investidor(new NomeDoInvestidor("Vendedor2"));

        vendedor.receberAcoes(empresa.obterNome(), new QuantidadeAcao(100));
        vendedor2.receberAcoes(empresa.obterNome(), new QuantidadeAcao(100));

        livro.registrarOrdemDeVenda(
                new OrdemDeVenda(vendedor, new PrecoAcao("30.00"), new QuantidadeAcao(50))
        );
        livro.registrarOrdemDeCompra(
                new OrdemDeCompra(comprador, new PrecoAcao("35.00"), new QuantidadeAcao(50))
        );

        assertEquals(new PrecoAcao("35.00"), empresa.getPrecoAtual());

        livro.registrarOrdemDeVenda(
                new OrdemDeVenda(vendedor2, new PrecoAcao("29.00"), new QuantidadeAcao(40))
        );
        livro.registrarOrdemDeCompra(
                new OrdemDeCompra(comprador2, new PrecoAcao("40.00"), new QuantidadeAcao(40))
        );

        assertEquals(new PrecoAcao("29.00"), empresa.getPrecoAtual());
    }

    private static class FakeEmpresa implements AlvoDeAtualizacaoDePreco {

        private final NomeDaEmpresa nome;
        private PrecoAcao precoAtual;

        FakeEmpresa(String nome) {
            this.nome = new NomeDaEmpresa(nome);
        }

        @Override
        public void atualizarPreco(PrecoAcao novoPreco) {
            this.precoAtual = novoPreco;
        }

        @Override
        public NomeDaEmpresa obterNome() {
            return nome;
        }

        @Override
        public boolean possuiPrecoEstabelecido() {
            return precoAtual != null;
        }

        public PrecoAcao getPrecoAtual() {
            return precoAtual;
        }
    }
}