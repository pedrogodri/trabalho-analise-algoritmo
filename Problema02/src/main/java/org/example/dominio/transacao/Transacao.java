package org.example.dominio.transacao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.example.dominio.acao.PrecoAcao;
import org.example.dominio.acao.QuantidadeAcao;
import org.example.dominio.empresa.NomeDaEmpresa;
import org.example.dominio.investidor.Investidor;

/**
 * Registro imutável de uma transação concluída entre dois investidores.
 *
 * <p>Criada pelo {@code LivroDeOrdens} após cada execução bem-sucedida de
 * um par compra/venda. Nunca é modificada após a criação.</p>
 */
public final class Transacao {

    private final Investidor comprador;
    private final Investidor vendedor;
    private final NomeDaEmpresa empresa;
    private final PrecoAcao precoExecutado;
    private final QuantidadeAcao quantidadeExecutada;
    private final LocalDateTime momento;

    public Transacao(Investidor comprador, Investidor vendedor,
                     NomeDaEmpresa empresa, PrecoAcao precoExecutado,
                     QuantidadeAcao quantidadeExecutada) {
        this.comprador = comprador;
        this.vendedor = vendedor;
        this.empresa = empresa;
        this.precoExecutado = precoExecutado;
        this.quantidadeExecutada = quantidadeExecutada;
        this.momento = LocalDateTime.now();
    }

    public void exibir() {
        String horario = momento.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        System.out.println("[TRANSACAO] " + horario
            + " | " + empresa.getNome()
            + " | " + quantidadeExecutada + " acoes @ " + precoExecutado.exibir()
            + " | Comprador: " + comprador.getNome().getNome()
            + " | Vendedor: " + vendedor.getNome().getNome());
    }
}
