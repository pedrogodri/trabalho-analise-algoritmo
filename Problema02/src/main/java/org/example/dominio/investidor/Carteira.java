package org.example.dominio.investidor;

import java.util.HashMap;
import java.util.Map;

import org.example.dominio.acao.QuantidadeAcao;
import org.example.dominio.empresa.NomeDaEmpresa;
import org.example.excecao.SaldoInsuficienteException;
import org.example.infra.LogMercado;

/**
 * First-class collection que representa a carteira de ações de um investidor.
 *
 * <p><b>Invariante</b>: a quantidade de ações de qualquer empresa nunca é negativa.
 * Tentativas de deduzir mais ações do que o saldo disponível lançam
 * {@link IllegalStateException}, protegendo a consistência da carteira.</p>
 *
 * <p>Nenhum mapa interno é exposto; toda interação acontece por métodos
 * de comportamento ({@code receberAcoes}, {@code deduzirAcoes},
 * {@code possuiAcoesSuficientes}).</p>
 */
public final class Carteira {

    private final Map<NomeDaEmpresa, QuantidadeAcao> acoes;

    public Carteira() {
        this.acoes = new HashMap<>();
    }

    /**
     * Adiciona ações de uma empresa à carteira.
     * Se a empresa ainda não existir na carteira, a entrada é criada.
     */
    public void receberAcoes(NomeDaEmpresa empresa, QuantidadeAcao quantidade) {
        acoes.merge(empresa, quantidade, QuantidadeAcao::adicionar);
    }

    /**
     * Deduz ações de uma empresa da carteira.
     * Se todas as ações forem vendidas, a empresa é removida da carteira.
     *
     * @throws IllegalStateException se o investidor não possuir ações suficientes
     */
    public void deduzirAcoes(NomeDaEmpresa empresa, QuantidadeAcao quantidade) {
        validarSaldoSuficiente(empresa, quantidade);
        QuantidadeAcao saldoAtual = acoes.get(empresa);
        atualizarSaldoAposVenda(empresa, saldoAtual, quantidade);
    }

    private void validarSaldoSuficiente(NomeDaEmpresa empresa, QuantidadeAcao quantidade) {
        if (!possuiAcoesSuficientes(empresa, quantidade)) {
            throw new SaldoInsuficienteException(
                    "Saldo insuficiente de " + empresa.getNome() + " para deduzir " + quantidade + " ações.");
        }
    }

    private void atualizarSaldoAposVenda(NomeDaEmpresa empresa, QuantidadeAcao saldoAtual,
                                          QuantidadeAcao quantidade) {
        if (saldoAtual.eIgualA(quantidade)) {
            acoes.remove(empresa);
            return;
        }
        acoes.put(empresa, saldoAtual.subtrair(quantidade));
    }

    /**
     * Verifica se o investidor possui pelo menos a quantidade informada de ações.
     */
    public boolean possuiAcoesSuficientes(NomeDaEmpresa empresa, QuantidadeAcao quantidade) {
        QuantidadeAcao saldo = acoes.get(empresa);
        if (saldo == null) {
            return false;
        }
        return saldo.eIgualA(quantidade) || saldo.eMaiorQue(quantidade);
    }

    /** Exibe o conteúdo da carteira no console. */
    public void exibir(NomeDoInvestidor proprietario) {
        LogMercado.cabecalhoCarteira(proprietario.getNome());
        if (acoes.isEmpty()) {
            LogMercado.carteiraSemAcoes();
            return;
        }
        acoes.forEach((empresa, quantidade) ->
            LogMercado.linhaCarteira(empresa.getNome(), quantidade));
    }
}
