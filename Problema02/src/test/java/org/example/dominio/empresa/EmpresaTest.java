package org.example.dominio.empresa;

import java.util.ArrayList;
import java.util.List;

import org.example.dominio.acao.PrecoAcao;
import org.example.dominio.acao.QuantidadeAcao;
import org.example.dominio.investidor.Investidor;
import org.example.dominio.investidor.NomeDoInvestidor;
import org.example.dominio.ordem.OrdemDeCompra;
import org.example.dominio.ordem.OrdemDeVenda;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Testa as regras de negócio da Empresa:
 * - preço definido pela primeira transação (lance do comprador)
 * - notificação dos observadores após atualização de preço
 * - transações subsequentes usam preço do vendedor
 */
class EmpresaTest {

    private Empresa empresa;
    private Investidor comprador;
    private Investidor vendedor;

    @BeforeEach
    void inicializar() {
        empresa = new Empresa(new NomeDaEmpresa("EmpresaTeste"));
        comprador = new Investidor(new NomeDoInvestidor("Comprador"));
        vendedor = new Investidor(new NomeDoInvestidor("Vendedor"));
        // Vendedor precisa ter ações para poder vender
        vendedor.receberAcoes(empresa.obterNome(), new QuantidadeAcao(500));
    }

    @Test
    void deveIniciarSemPrecoEstabelecido() {
        assertFalse(empresa.possuiPrecoEstabelecido());
    }

    @Test
    void deveEstabelecerPrecoNaPrimeiraTransacao() {
        // Comprador paga R$35, vendedor aceita R$30
        // → preço deve ser R$35 (lance do comprador, pois é a primeira transação)
        empresa.registrarOrdemDeVenda(
            new OrdemDeVenda(vendedor, new PrecoAcao("30.00"), new QuantidadeAcao(100)));
        empresa.registrarOrdemDeCompra(
            new OrdemDeCompra(comprador, new PrecoAcao("35.00"), new QuantidadeAcao(100)));

        assertTrue(empresa.possuiPrecoEstabelecido());
    }

    @Test
    void deveNotificarObservadoresAposAtualizacaoDePreco() {
        List<String> notificacoes = new ArrayList<>();

        empresa.inscrever((nomeEmpresa, novoPreco) ->
            notificacoes.add(nomeEmpresa.getNome() + "=" + novoPreco.exibir()));

        empresa.registrarOrdemDeVenda(
            new OrdemDeVenda(vendedor, new PrecoAcao("30.00"), new QuantidadeAcao(100)));
        empresa.registrarOrdemDeCompra(
            new OrdemDeCompra(comprador, new PrecoAcao("35.00"), new QuantidadeAcao(100)));

        assertEquals(1, notificacoes.size());
        assertTrue(notificacoes.get(0).contains("EmpresaTeste"));
    }

    @Test
    void naoDeveNotificarQuandoNaoHaCombinacao() {
        List<String> notificacoes = new ArrayList<>();
        empresa.inscrever((nomeEmpresa, novoPreco) -> notificacoes.add("notificado"));

        // Comprador paga menos que o vendedor pede → sem match
        empresa.registrarOrdemDeVenda(
            new OrdemDeVenda(vendedor, new PrecoAcao("40.00"), new QuantidadeAcao(100)));
        empresa.registrarOrdemDeCompra(
            new OrdemDeCompra(comprador, new PrecoAcao("30.00"), new QuantidadeAcao(100)));

        assertTrue(notificacoes.isEmpty());
    }

    @Test
    void deveDesinscrevirObservador() {
        List<String> notificacoes = new ArrayList<>();
        var observador = (org.example.observer.ObservadorDePreco)
            (nomeEmpresa, novoPreco) -> notificacoes.add("notificado");

        empresa.inscrever(observador);
        empresa.desinscrever(observador);

        empresa.registrarOrdemDeVenda(
            new OrdemDeVenda(vendedor, new PrecoAcao("30.00"), new QuantidadeAcao(100)));
        empresa.registrarOrdemDeCompra(
            new OrdemDeCompra(comprador, new PrecoAcao("35.00"), new QuantidadeAcao(100)));

        assertTrue(notificacoes.isEmpty());
    }
}
