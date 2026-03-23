package org.example.dominio.ordem;

import org.example.dominio.acao.PrecoAcao;
import org.example.dominio.acao.QuantidadeAcao;
import org.example.dominio.investidor.Investidor;

/** Ordem de compra registrada por um investidor. */
public final class OrdemDeCompra extends Ordem {

    public OrdemDeCompra(Investidor investidor, PrecoAcao precoAlvo, QuantidadeAcao quantidade) {
        super(investidor, precoAlvo, quantidade, TipoOrdem.COMPRA);
    }
}
