package org.example.dominio.empresa;

import org.example.dominio.Nome;

/**
 * Valor objeto que encapsula o nome de uma empresa.
 * Usado como chave em mapas de carteira e listagens.
 */
public final class NomeDaEmpresa extends Nome {

    public NomeDaEmpresa(String nome) {
        super(nome, "da empresa");
    }
}
