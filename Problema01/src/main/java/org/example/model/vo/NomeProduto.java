package org.example.model.vo;

import org.example.exceptions.DadoInvalidoException;
import org.example.util.Validacao;

public class NomeProduto {

    private static final int TAMANHO_MAXIMO = 100;

    private final String valor;

    public NomeProduto(String valor) {
        String normalizado = Validacao.validarObrigatorio(valor, "Nome do produto");
        if (normalizado.length() > TAMANHO_MAXIMO) {
            throw new DadoInvalidoException("Nome do produto não pode ter mais de " + TAMANHO_MAXIMO + " caracteres");
        }
        this.valor = normalizado;
    }

    @Override
    public String toString() {
        return valor;
    }
}
