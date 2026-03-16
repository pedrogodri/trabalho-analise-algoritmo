package org.example.model;

import org.example.model.vo.Cep;
import org.example.model.vo.Estado;
import org.example.util.Validacao;

/**
 * Agrega os dados de endereço de entrega do cliente.
 *
 * <p>Campos simples (nome, rua, número, complemento, cidade) são armazenados como
 * {@code String} e validados genericamente no construtor. Campos com formato
 * específico ({@link Cep}, {@link Estado}) mantêm seus próprios Value Objects.</p>
 */
public class EnderecoEntrega {

    private final Cep cep;
    private final String rua;
    private final String numero;
    private final String complemento;
    private final String cidade;
    private final Estado estado;

    public EnderecoEntrega(String nomeCliente, Cep cep, String rua,
                           String numero, String complemento,
                           String cidade, Estado estado) {
        this.cep = cep;
        this.rua = Validacao.validarObrigatorio(rua, "Logradouro");
        this.numero = Validacao.validarObrigatorio(numero, "Número do endereço");
        this.complemento = complemento == null || complemento.isBlank() ? "" : complemento.trim();
        this.cidade = Validacao.validarObrigatorio(cidade, "Cidade");
        this.estado = estado;
    }

    /**
     * Formata rua, número e complemento (quando presente) em uma única linha.
     *
     * @return ex: "Rua das Flores, 123 (Apto 4)" ou "Rua das Flores, 123"
     */
    public String formatarLougradouro() {
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
}
