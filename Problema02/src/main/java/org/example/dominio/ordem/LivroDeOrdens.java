package org.example.dominio.ordem;

import org.example.dominio.acao.PrecoAcao;
import org.example.dominio.acao.QuantidadeAcao;
import org.example.dominio.empresa.AlvoDeAtualizacaoDePreco;
import org.example.dominio.investidor.Investidor;
import org.example.dominio.transacao.ListaDeTransacoes;
import org.example.dominio.transacao.Transacao;
import org.example.excecao.OrdemInvalidaException;
import org.example.infra.LogMercado;

/**
 * Mediador central do mercado de ações para uma empresa específica.
 *
 * <p>Responsabilidades:
 * <ul>
 *   <li>Receber e armazenar ordens de compra e venda.</li>
 *   <li>Delegar ao {@link CombinadorDeOrdens} a busca por pares compatíveis.</li>
 *   <li>Executar a transação: transferir ações entre investidores,
 *       atualizar quantidades das ordens e acionar a atualização de preço da empresa.</li>
 *   <li>Registrar o histórico de transações concluídas.</li>
 * </ul>
 * </p>
 *
 * <p><b>Regra de preço de execução</b>:
 * <ul>
 *   <li>Se a empresa <b>ainda não possui preço</b> estabelecido (primeira transação),
 *       o preço de execução é o <b>lance do comprador</b>. Isso define o valor
 *       inicial da ação no mercado.</li>
 *   <li>Se a empresa <b>já possui preço</b>, o preço de execução é o
 *       <b>pedido do vendedor</b> (semântica padrão de ordens limitadas).</li>
 * </ul>
 * </p>
 */
public final class LivroDeOrdens {

    private final AlvoDeAtualizacaoDePreco empresa;
    private final ListaDeOrdens ordensDeCompra;
    private final ListaDeOrdens ordensDeVenda;
    private final ListaDeTransacoes transacoes;
    private final CombinadorDeOrdens combinador;

    public LivroDeOrdens(AlvoDeAtualizacaoDePreco empresa) {
        this.empresa = empresa;
        this.ordensDeCompra = new ListaDeOrdens();
        this.ordensDeVenda = new ListaDeOrdens();
        this.transacoes = new ListaDeTransacoes();
        this.combinador = new CombinadorDeOrdens();
    }

    /** Registra uma ordem de compra e tenta processar combinações imediatamente. */
    public void registrarOrdemDeCompra(OrdemDeCompra ordem) {
        adicionarEProcessar(ordem, ordensDeCompra);
    }

    /** Registra uma ordem de venda e tenta processar combinações imediatamente. */
    public void registrarOrdemDeVenda(OrdemDeVenda ordem) {
        validarOrdemDeVenda(ordem);
        adicionarEProcessar(ordem, ordensDeVenda);
    }

    private void adicionarEProcessar(Ordem ordem, ListaDeOrdens lista) {
        LogMercado.registroDeOrdem(
                ordem.getInvestidor().getNomeCompleto(),
                ordem.getQuantidadeRestante(),
                ordem.getPrecoAlvo(),
                ordem.getDirecao()
        );
        lista.adicionar(ordem);
        processarOrdens();
    }

    private void validarOrdemDeVenda(OrdemDeVenda ordem) {
        if (!ordem.getInvestidor().possuiAcoesSuficientes(
                empresa.obterNome(),
                ordem.getQuantidadeRestante())) {
            throw new OrdemInvalidaException(
                    "Investidor " + ordem.getInvestidor().getNomeCompleto()
                            + " nao possui acoes suficientes de "
                            + empresa.obterNome().getNome()
                            + " para registrar ordem de venda de "
                            + ordem.getQuantidadeRestante() + " acoes."
            );
        }
    }

    /**
     * Verifica se existe combinação possível entre ordens pendentes e,
     * enquanto houver, executa as transações correspondentes.
     */
    private void processarOrdens() {
        while (true) {
            var combinacao = combinador.encontrarCombinacao(ordensDeCompra, ordensDeVenda);

            if (combinacao.isEmpty()) {
                LogMercado.nenhumaCombinacao(empresa.obterNome());
                break;
            }

            executarCombinacao(combinacao.get());
        }
    }

    private void executarCombinacao(ParDeOrdens par) {
        OrdemDeCompra compra = par.getOrdemDeCompra();
        OrdemDeVenda venda = par.getOrdemDeVenda();

        PrecoAcao precoDeExecucao = resolverPrecoDeExecucao(compra, venda);
        QuantidadeAcao quantidadeExecutada = resolverQuantidade(compra, venda);

        LogMercado.combinacaoEncontrada(
                compra.getInvestidor().getNomeCompleto(), compra.getPrecoAlvo(),
                venda.getInvestidor().getNomeCompleto(), venda.getPrecoAlvo()
        );

        transferirAcoes(compra.getInvestidor(), venda.getInvestidor(), quantidadeExecutada);
        marcarOrdens(compra, venda, quantidadeExecutada);

        empresa.atualizarPreco(precoDeExecucao);

        Transacao transacao = new Transacao(
                compra.getInvestidor(),
                venda.getInvestidor(),
                empresa.obterNome(),
                precoDeExecucao,
                quantidadeExecutada
        );
        transacoes.registrar(transacao);
    }

    /**
     * Determina o preço de execução conforme a regra de negócio:
     * primeira transação usa o lance do comprador; as subsequentes usam o pedido do vendedor.
     */
    private PrecoAcao resolverPrecoDeExecucao(OrdemDeCompra compra, OrdemDeVenda venda) {
        if (!empresa.possuiPrecoEstabelecido()) {
            return compra.getPrecoAlvo();
        }
        return venda.getPrecoAlvo();
    }

    /** Executa o mínimo entre a quantidade desejada pelo comprador e disponível pelo vendedor. */
    private QuantidadeAcao resolverQuantidade(OrdemDeCompra compra, OrdemDeVenda venda) {
        return compra.getQuantidadeRestante().minimo(venda.getQuantidadeRestante());
    }

    private void transferirAcoes(
            Investidor comprador,
            Investidor vendedor,
            QuantidadeAcao quantidade
    ) {
        vendedor.deduzirAcoes(empresa.obterNome(), quantidade);
        comprador.receberAcoes(empresa.obterNome(), quantidade);
    }

    private void marcarOrdens(
            OrdemDeCompra compra,
            OrdemDeVenda venda,
            QuantidadeAcao quantidadeExecutada
    ) {
        compra.deduzirQuantidade(quantidadeExecutada);
        venda.deduzirQuantidade(quantidadeExecutada);
    }
}
