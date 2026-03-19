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

    private FabricaDeDadosMock() {
        // utilitário estático — não instanciável
    }

    public static Empresa criarPetrobrasSA() {
        return new Empresa(new NomeDaEmpresa("PetrobrasSA"));
    }

    public static Empresa criarValeSA() {
        return new Empresa(new NomeDaEmpresa("ValeSA"));
    }

    public static Investidor criarAlice() {
        return new Investidor(new NomeDoInvestidor("Alice"));
    }

    public static Investidor criarBob() {
        return new Investidor(new NomeDoInvestidor("Bob"));
    }

    public static Investidor criarCarol() {
        return new Investidor(new NomeDoInvestidor("Carol"));
    }

    public static Investidor criarDave() {
        return new Investidor(new NomeDoInvestidor("Dave"));
    }
}
