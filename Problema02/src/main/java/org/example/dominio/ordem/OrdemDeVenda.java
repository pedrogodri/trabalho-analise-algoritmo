package org.example.dominio.ordem;

import org.example.dominio.acao.PrecoAcao;
import org.example.dominio.acao.QuantidadeAcao;
import org.example.dominio.investidor.Investidor;

/** Ordem de venda registrada por um investidor. */
public final class OrdemDeVenda extends Ordem {

    public OrdemDeVenda(Investidor investidor, PrecoAcao precoAlvo, QuantidadeAcao quantidade) {
        super(investidor, precoAlvo, quantidade, TipoOrdem.VENDA);
    }
}
