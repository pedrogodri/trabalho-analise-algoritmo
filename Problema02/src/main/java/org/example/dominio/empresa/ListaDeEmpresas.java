package org.example.dominio.empresa;

import java.util.ArrayList;
import java.util.List;

/** First-class collection de empresas listadas no mercado. */
public final class ListaDeEmpresas {

    private final List<Empresa> empresas;

    public ListaDeEmpresas() {
        this.empresas = new ArrayList<>();
    }

    public void adicionar(Empresa empresa) {
        empresas.add(empresa);
    }

    public void exibirPrecos() {
        empresas.forEach(Empresa::exibirPrecoAtual);
    }
}
