package org.example.model.enums;

public enum TipoEntrega {
    RETIRADALOJA("Retirada no Local"),
    PAC("Encomenda PAC"),
    SEDEX("Encomenda Sedex");

    private final String descricao;

    TipoEntrega(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
