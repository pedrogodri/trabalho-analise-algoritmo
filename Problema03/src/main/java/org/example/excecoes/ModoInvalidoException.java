package org.example.excecoes;

/**
 * Exceção lançada quando uma operação é realizada com um modo inválido ou nulo.
 *
 * <p>Usada pela {@code CasaInteligente} e implementações de {@code Modo} para
 * comunicar falhas de configuração de maneira explícita.</p>
 */
public class ModoInvalidoException extends RuntimeException {

    public ModoInvalidoException(String mensagem) {
        super(mensagem);
    }

    public ModoInvalidoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
