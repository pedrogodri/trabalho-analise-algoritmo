package org.example.exceptions;

/**
 * Lançada pelos Value Objects quando um dado de entrada viola suas regras de negócio.
 *
 * <p>Estende {@link IllegalArgumentException} para indicar que o argumento
 * fornecido é semanticamente inválido no domínio da aplicação
 * (ex: nome em branco, peso negativo, CEP com formato incorreto).</p>
 */
public class DadoInvalidoException extends IllegalArgumentException {

    public DadoInvalidoException(String mensagem) {
        super(mensagem);
    }
}
