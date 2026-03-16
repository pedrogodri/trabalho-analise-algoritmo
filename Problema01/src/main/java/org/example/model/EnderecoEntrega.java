package org.example.model;

import org.example.model.vo.Cep;
import org.example.model.vo.Cidade;
import org.example.model.vo.Complemento;
import org.example.model.vo.Estado;
import org.example.model.vo.Logradouro;
import org.example.model.vo.NomeCliente;
import org.example.model.vo.NumeroEndereco;

/**
 * Agrega os dados de endereço de entrega do cliente.
 *
 * <p>Cada campo é um Value Object que valida seus próprios invariantes,
 * garantindo que um {@code EnderecoEntrega} nunca seja construído com dados inválidos.
 * Encapsula também a formatação para exibição, evitando lógica de apresentação
 * espalhada pela camada de UI.</p>
 */
public class EnderecoEntrega {

    private final NomeCliente nomeCliente;
    private final Cep cep;
    private final Logradouro rua;
    private final NumeroEndereco numero;
    private final Complemento complemento;
    private final Cidade cidade;
    private final Estado estado;

    public EnderecoEntrega(NomeCliente nomeCliente, Cep cep, Logradouro rua,
                           NumeroEndereco numero, Complemento complemento,
                           Cidade cidade, Estado estado) {
        this.nomeCliente = nomeCliente;
        this.cep = cep;
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
        this.cidade = cidade;
        this.estado = estado;
    }

    public NomeCliente nomeCliente() {
        return nomeCliente;
    }

    /**
     * Formata rua, número e complemento (quando presente) em uma única linha.
     *
     * @return ex: "Rua das Flores, 123 (Apto 4)" ou "Rua das Flores, 123"
     */
    public String linhaRua() {
        String comp = complemento.presente() ? " (" + complemento + ")" : "";
        return rua + ", " + numero + comp;
    }

    /**
     * Formata CEP, cidade e estado em uma única linha.
     *
     * @return ex: "89010-000 - Blumenau/SC"
     */
    public String linhaCepCidadeEstado() {
        return cep + " - " + cidade + "/" + estado;
    }
}
