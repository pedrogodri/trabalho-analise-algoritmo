package org.example.simulacao;

import org.example.dominio.empresa.Empresa;
import org.example.dominio.empresa.NomeDaEmpresa;
import org.example.dominio.investidor.Investidor;
import org.example.dominio.investidor.NomeDoInvestidor;

/**
 * Cria os atores do mercado com dados fictícios para a simulação.
 * Cada método de fábrica retorna um objeto pré-configurado e pronto para uso.
 */
public final class FabricaDeDadosMock {

    private FabricaDeDadosMock() {}

    public static Empresa criarEmpresa(String nome) {
        return new Empresa(new NomeDaEmpresa(nome));
    }

    public static Investidor criarInvestidor(String nome) {
        return new Investidor(new NomeDoInvestidor(nome));
    }
}
