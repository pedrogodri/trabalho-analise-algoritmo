package org.example.dominio.empresa;

/**
 * Valor objeto que encapsula o nome de uma empresa.
 * Usado como chave em mapas de carteira e listagens.
 */
public final class NomeDaEmpresa {

    private final String nome;

    public NomeDaEmpresa(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome da empresa não pode ser vazio.");
        }
        this.nome = nome.trim();
    }

    public String getNome() {
        return nome;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof NomeDaEmpresa outro)) return false;
        return this.nome.equalsIgnoreCase(outro.nome);
    }

    @Override
    public int hashCode() {
        return nome.toLowerCase().hashCode();
    }

    @Override
    public String toString() {
        return nome;
    }
}
