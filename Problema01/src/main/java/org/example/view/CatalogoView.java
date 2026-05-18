package org.example.view;

import org.example.model.Produto;

import java.util.List;

/**
 * Responsável por exibir o catálogo de produtos ao usuário.
 *
 * <p>Separa a responsabilidade de renderização da lógica de negócio,
 * seguindo o princípio da responsabilidade única (SRP).</p>
 */
public class CatalogoView {

    private CatalogoView() {}

    /**
     * Exibe todos os produtos do catálogo numerados com nome e preço.
     *
     * @param catalogo lista de produtos a ser exibida
     */
    public static void exibir(List<Produto> catalogo) {
        System.out.println("\n" + "─".repeat(50));
        System.out.println("CATÁLOGO DE LIVROS");
        System.out.println("─".repeat(50));

        for (int i = 0; i < catalogo.size(); i++) {
            Produto livro = catalogo.get(i);
            System.out.printf("%d. %s%n", i + 1, livro.getNomeProduto());
            System.out.printf("   Preço: %s%n", livro.getValorMonetario());
        }

        System.out.println("─".repeat(50));
    }
}
