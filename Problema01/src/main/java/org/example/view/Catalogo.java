package org.example.view;

import org.example.model.Produto;
import org.example.model.vo.NomeProduto;
import org.example.model.vo.PesoEmKg;
import org.example.model.vo.ValorMonetario;

import java.util.ArrayList;
import java.util.List;

/**
 * Responsável por fornecer o catálogo de livros disponíveis para venda.
 */
public class Catalogo {

    private Catalogo() {}

    public static List<Produto> inicializarCatalogo() {
        List<Produto> catalogo = new ArrayList<>();
        catalogo.add(produto("O Senhor dos Anéis", 89.90f, 0.45f));
        catalogo.add(produto("Harry Potter e a Pedra Filosofal", 65.00f, 0.40f));
        catalogo.add(produto("1984 - George Orwell", 45.90f, 0.35f));
        catalogo.add(produto("O Código Da Vinci", 59.90f, 0.42f));
        catalogo.add(produto("Dom Casmurro - Machado de Assis", 35.00f, 0.30f));
        catalogo.add(produto("A Revolução dos Bichos", 38.50f, 0.28f));
        catalogo.add(produto("O Pequeno Príncipe", 42.00f, 0.25f));
        catalogo.add(produto("Cem Anos de Solidão", 72.50f, 0.48f));
        return catalogo;
    }

    private static Produto produto(String nome, float valor, float peso) {
        return new Produto(new NomeProduto(nome), new ValorMonetario(valor), new PesoEmKg(peso));
    }
}