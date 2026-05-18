package org.example.dominio;

/**
 * Classe base para value objects que encapsulam um nome textual.
 *
 * <p>Elimina a duplicação entre {@code NomeDaEmpresa} e {@code NomeDoInvestidor},
 * que compartilham a mesma estrutura: validação, armazenamento, {@code equals}
 * case-insensitive, {@code hashCode} e {@code toString}.</p>
 *
 * <p>A igualdade respeita o tipo concreto: um {@code NomeDaEmpresa} nunca
 * será igual a um {@code NomeDoInvestidor}, mesmo que possuam o mesmo texto.</p>
 */
public abstract class Nome {

    private final String nome;

    protected Nome(String nome, String tipoEntidade) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome " + tipoEntidade + " não pode ser vazio.");
        }
        this.nome = nome.trim();
    }

    public String getNome() {
        return nome;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Nome outro)) return false;
        return getClass() == outro.getClass()
            && this.nome.equalsIgnoreCase(outro.nome);
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
