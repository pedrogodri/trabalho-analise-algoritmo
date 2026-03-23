package org.example.dominio.investidor;

import org.example.dominio.acao.PrecoAcao;
import org.example.dominio.acao.QuantidadeAcao;
import org.example.dominio.empresa.NomeDaEmpresa;
import org.example.infra.LogMercado;
import org.example.observer.ObservadorDePreco;

/**
 * Representa um participante do mercado que pode comprar e vender ações.
 *
 * <p>Implementa {@link ObservadorDePreco}: ao se inscrever em uma empresa,
 * receberá notificações em tempo real sempre que o preço for atualizado.</p>
 *
 * <p>Toda operação sobre ações é delegada à {@link Carteira}, preservando
 * encapsulamento e a invariante de saldo não-negativo.</p>
 */
public final class Investidor implements ObservadorDePreco {

    private final NomeDoInvestidor nome;
    private final Carteira carteira;

    public Investidor(NomeDoInvestidor nome) {
        this.nome = nome;
        this.carteira = new Carteira();
    }

    /** Recebe ações de uma empresa (resultado de compra ou transferência). */
    public void receberAcoes(NomeDaEmpresa empresa, QuantidadeAcao quantidade) {
        carteira.receberAcoes(empresa, quantidade);
    }

    /**
     * Deduz ações de uma empresa da carteira (resultado de venda).
     *
     * @throws IllegalStateException se saldo for insuficiente
     */
    public void deduzirAcoes(NomeDaEmpresa empresa, QuantidadeAcao quantidade) {
        carteira.deduzirAcoes(empresa, quantidade);
    }

    /** Verifica se o investidor possui ações suficientes para vender. */
    public boolean possuiAcoesSuficientes(NomeDaEmpresa empresa, QuantidadeAcao quantidade) {
        return carteira.possuiAcoesSuficientes(empresa, quantidade);
    }

    public NomeDoInvestidor getNome() {
        return nome;
    }

    /** Retorna o nome textual do investidor. Evita cadeias nome.getNome() externamente. */
    public String getNomeCompleto() {
        return nome.getNome();
    }

    /** Exibe o conteúdo da carteira no console. */
    public void exibirCarteira() {
        carteira.exibir(nome);
    }

    /**
     * Chamado pela Empresa ao alterar o preço de uma ação.
     * Imprime a notificação no console.
     */
    @Override
    public void aoAtualizarPreco(NomeDaEmpresa nomeDaEmpresa, PrecoAcao novoPreco) {
        LogMercado.notificacaoDePreco(getNomeCompleto(), nomeDaEmpresa, novoPreco);
    }
}
