package org.example.dominio.investidor;

/** Valor objeto que encapsula o nome de um investidor. */
public final class NomeDoInvestidor {

    private final String nome;

    public NomeDoInvestidor(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome do investidor não pode ser vazio.");
        }
        this.nome = nome.trim();
    }

    public String getNome() {
        return nome;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof NomeDoInvestidor outro)) return false;
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
