package org.example.dominio.investidor;

import java.util.ArrayList;
import java.util.List;

/** First-class collection de investidores. */
public final class ListaDeInvestidores {

    private final List<Investidor> investidores;

    public ListaDeInvestidores() {
        this.investidores = new ArrayList<>();
    }

    public void adicionar(Investidor investidor) {
        investidores.add(investidor);
    }

    public void exibirCarteiras() {
        investidores.forEach(Investidor::exibirCarteira);
    }
}
