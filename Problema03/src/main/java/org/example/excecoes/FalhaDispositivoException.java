package org.example.excecoes;

/**
 * Exceção lançada quando a operação de um dispositivo falha em tempo de execução.
 *
 * <p>Envelopa erros originados nos adaptadores de fabricantes, traduzindo falhas
 * técnicas em mensagens de domínio compreensíveis.</p>
 */
public class FalhaDispositivoException extends RuntimeException {

    public FalhaDispositivoException(String mensagem) {
        super(mensagem);
    }

    public FalhaDispositivoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
