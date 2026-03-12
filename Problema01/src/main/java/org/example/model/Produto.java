package org.example.model;

public class Produto {
    private String nome;
    private float valor;
    private float peso;

    public Produto(String nome, float valor, float peso) {
        this.nome = nome;
        this.valor = valor;
        this.peso = peso;
    }

    public String getNome() {
        return nome;
    }

    public float getValor() {
        return valor;
    }

    public float getPeso() {
        return peso;
    }
}
