package org.example.dominio.investidor;

import java.util.HashMap;
import java.util.Map;

import org.example.dominio.acao.QuantidadeAcao;
import org.example.dominio.empresa.NomeDaEmpresa;

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
        QuantidadeAcao saldoAtual = acoes.getOrDefault(empresa, null);
        if (saldoAtual == null) {
            acoes.put(empresa, quantidade);
            return;
        }
        acoes.put(empresa, saldoAtual.adicionar(quantidade));
    }

    /**
     * Deduz ações de uma empresa da carteira.
     * Se todas as ações forem vendidas, a empresa é removida da carteira.
     *
     * @throws IllegalStateException se o investidor não possuir ações suficientes
     */
    public void deduzirAcoes(NomeDaEmpresa empresa, QuantidadeAcao quantidade) {
        if (!possuiAcoesSuficientes(empresa, quantidade)) {
            throw new IllegalStateException(
                "Saldo insuficiente de " + empresa.getNome() + " para deduzir " + quantidade + " ações.");
        }
        QuantidadeAcao saldoAtual = acoes.get(empresa);
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
        QuantidadeAcao saldo = acoes.getOrDefault(empresa, null);
        if (saldo == null) return false;
        return saldo.eIgualA(quantidade) || saldo.eMaiorQue(quantidade);
    }

    /** Exibe o conteúdo da carteira no console. */
    public void exibir(NomeDoInvestidor proprietario) {
        System.out.println("  Carteira de " + proprietario.getNome() + ":");
        if (acoes.isEmpty()) {
            System.out.println("    (sem ações)");
            return;
        }
        acoes.forEach((empresa, quantidade) ->
            System.out.println("    " + empresa.getNome() + ": " + quantidade + " ações"));
    }
}
