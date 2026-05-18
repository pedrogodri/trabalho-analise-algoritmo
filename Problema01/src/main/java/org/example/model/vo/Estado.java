package org.example.model.vo;

import org.example.exceptions.DadoInvalidoException;
import org.example.util.Validacao;

import java.util.Objects;

/**
 * Value Object que representa a sigla de um estado brasileiro.
 *
 * <p>Aceita qualquer combinação de maiúsculas/minúsculas e normaliza
 * para maiúsculas. Lança {@link DadoInvalidoException} se a sigla não
 * tiver exatamente 2 letras (ex: SC, SP, RJ).</p>
 */
public class Estado {

    private static final String MENSAGEM_INVALIDO = "Estado invalido! Use a sigla com 2 letras (ex: SC, SP, RJ)";

    private final String sigla;

    public Estado(String sigla) {
        String normalizado = Validacao.validarObrigatorio(sigla, "Estado").toUpperCase();
        if (!normalizado.matches("[A-Z]{2}")) {
            throw new DadoInvalidoException(MENSAGEM_INVALIDO);
        }
        this.sigla = normalizado;
    }

    @Override
    public String toString() {
        return sigla;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Estado that)) return false;
        return Objects.equals(sigla, that.sigla);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sigla);
    }
}
