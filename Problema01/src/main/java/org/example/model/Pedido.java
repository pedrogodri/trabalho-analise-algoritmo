package org.example.model;

import org.example.model.enums.TipoEntrega;

import java.util.ArrayList;

public class Pedido {
    private ArrayList<Produto> listaProdutos;
    private TipoEntrega tipoEntrega;

    public Pedido() {
        this.listaProdutos = new ArrayList<>();
    }

}
