package org.example.model;

import org.example.model.vo.Cep;
import org.example.model.vo.Estado;
import org.example.util.Validacao;

import java.util.Objects;

/**
 * Agrega os dados de endereço de entrega do cliente.
 *
 * <p>Campos simples (rua, número, complemento, cidade) são validados
 * genericamente no construtor. Campos com formato específico ({@link Cep},
 * {@link Estado}) mantêm seus próprios Value Objects.</p>
 */
public class EnderecoEntrega {

    private final Cep cep;
    private final String rua;
    private final String numero;
    private final String complemento;
    private final String cidade;
    private final Estado estado;

    public EnderecoEntrega(Cep cep, String rua, String numero,
                           String complemento, String cidade, Estado estado) {
        this.cep = cep;
        this.rua = Validacao.validarObrigatorio(rua, "Logradouro");
        this.numero = Validacao.validarObrigatorio(numero, "Número do endereço");
        this.complemento = complemento == null || complemento.isBlank() ? "" : complemento.trim();
        this.cidade = Validacao.validarObrigatorio(cidade, "Cidade");
        this.estado = estado;
    }

    /** @return Value Object do CEP */
    public Cep getCep() {
        return cep;
    }

    /** @return Value Object do estado */
    public Estado getEstado() {
        return estado;
    }

    /** @return rua normalizada */
    public String getRua() {
        return rua;
    }

    /** @return número do endereço */
    public String getNumero() {
        return numero;
    }

    /** @return complemento ou string vazia se ausente */
    public String getComplemento() {
        return complemento;
    }

    /** @return cidade normalizada */
    public String getCidade() {
        return cidade;
    }

    /**
     * Formata rua, número e complemento (quando presente) em uma única linha.
     *
     * @return ex: "Rua das Flores, 123 (Apto 4)" ou "Rua das Flores, 123"
     */
    public String formatarLogradouro() {
        String comp = !complemento.isBlank() ? " (" + complemento + ")" : "";
        return rua + ", " + numero + comp;
    }

    /**
     * Formata CEP, cidade e estado em uma única linha.
     *
     * @return ex: "89010-000 - Blumenau/SC"
     */
    public String concatenarCepCidadeEstado() {
        return cep + " - " + cidade + "/" + estado;
    }

    @Override
    public String toString() {
        return formatarLogradouro() + " - " + concatenarCepCidadeEstado();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EnderecoEntrega that)) return false;
        return Objects.equals(cep, that.cep)
                && Objects.equals(rua, that.rua)
                && Objects.equals(numero, that.numero)
                && Objects.equals(complemento, that.complemento)
                && Objects.equals(cidade, that.cidade)
                && Objects.equals(estado, that.estado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cep, rua, numero, complemento, cidade, estado);
    }
}
