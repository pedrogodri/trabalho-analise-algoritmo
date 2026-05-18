package org.example.dominio.transacao;

import java.util.ArrayList;
import java.util.List;

/** First-class collection de transações concluídas. */
public final class ListaDeTransacoes {

    private final List<Transacao> transacoes;

    public ListaDeTransacoes() {
        this.transacoes = new ArrayList<>();
    }

    public void registrar(Transacao transacao) {
        transacoes.add(transacao);
        transacao.exibir();
    }

    public int getTotal() {
        return transacoes.size();
    }
}
