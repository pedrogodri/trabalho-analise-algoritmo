package org.example.infra;

import org.example.dominio.acao.PrecoAcao;
import org.example.dominio.acao.QuantidadeAcao;
import org.example.dominio.empresa.NomeDaEmpresa;
import org.example.dominio.ordem.TipoOrdem;

/**
 * Centraliza todas as mensagens de saída do mercado.
 *
 * <p>Clean Code / SRP: elimina {@code System.out.println} espalhado pelas classes de domínio.
 * O domínio não é responsável por saída de console. Toda mensagem passa por aqui,
 * facilitando substituição futura por um framework de logging real (ex: SLF4J).</p>
 *
 * <p><b>Padrão aplicado</b>: Facade — interface simples e coesa sobre o mecanismo de saída.</p>
 */
public final class LogMercado {

    private LogMercado() {}

    // ── Ordens ──────────────────────────────────────────────────────────────

    public static void registroDeOrdem(String nomeInvestidor,
                                        QuantidadeAcao quantidade,
                                        PrecoAcao preco,
                                        TipoOrdem tipo) {
        System.out.println("[MERCADO] " + nomeInvestidor
            + " registra " + tipo + " " + quantidade + " @ " + preco.exibir());
    }

    public static void nenhumaCombinacao(NomeDaEmpresa empresa) {
        System.out.println("[MERCADO] Nenhuma combinacao encontrada para " + empresa.getNome());
    }

    // ── Combinação e Transação ───────────────────────────────────────────────

    public static void combinacaoEncontrada(String nomeComprador, PrecoAcao precoCompra,
                                             String nomeVendedor, PrecoAcao precoVenda) {
        System.out.println("[COMBINACAO] COMPRA " + nomeComprador + " @ " + precoCompra.exibir()
            + " <-> VENDA " + nomeVendedor + " @ " + precoVenda.exibir());
    }

    public static void transacaoExecutada(String horario, NomeDaEmpresa empresa,
                                           QuantidadeAcao quantidade, PrecoAcao preco,
                                           String nomeComprador, String nomeVendedor) {
        System.out.println("[TRANSACAO] " + horario
            + " | " + empresa.getNome()
            + " | " + quantidade + " acoes @ " + preco.exibir()
            + " | Comprador: " + nomeComprador
            + " | Vendedor: " + nomeVendedor);
    }

    // ── Preço ────────────────────────────────────────────────────────────────

    public static void precoEstabelecido(NomeDaEmpresa empresa, PrecoAcao preco) {
        System.out.println("[PRECO] " + empresa.getNome() + " estabelecido em " + preco.exibir());
    }

    public static void precoAtualizado(NomeDaEmpresa empresa, PrecoAcao preco) {
        System.out.println("[PRECO] " + empresa.getNome() + " atualizado para " + preco.exibir());
    }

    public static void semPrecoEstabelecido(NomeDaEmpresa empresa) {
        System.out.println("  " + empresa.getNome() + ": (preco ainda nao estabelecido)");
    }

    // ── Notificação Observer ─────────────────────────────────────────────────

    public static void notificacaoDePreco(String nomeInvestidor, NomeDaEmpresa empresa,
                                           PrecoAcao novoPreco) {
        System.out.println("[NOTIFICACAO] " + nomeInvestidor
            + " <- " + empresa.getNome() + " atualizada para " + novoPreco.exibir());
    }

    // ── Carteira ─────────────────────────────────────────────────────────────

    public static void cabecalhoCarteira(String nomeProprietario) {
        System.out.println("  Carteira de " + nomeProprietario + ":");
    }

    public static void carteiraSemAcoes() {
        System.out.println("    (sem acoes)");
    }

    public static void linhaCarteira(String nomeEmpresa, QuantidadeAcao quantidade) {
        System.out.println("    " + nomeEmpresa + ": " + quantidade + " acoes");
    }
}
