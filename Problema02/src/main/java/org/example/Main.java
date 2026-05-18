package org.example;

import org.example.simulacao.SimulacaoDoMercado;

/** Ponto de entrada da aplicação. Executa a simulação do mercado de ações. */
public class Main {

    public static void main(String[] args) {
        SimulacaoDoMercado simulacao = new SimulacaoDoMercado();
        simulacao.executar();
    }
}
