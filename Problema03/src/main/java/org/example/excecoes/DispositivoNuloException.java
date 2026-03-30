package org.example.excecoes;

/**
 * Exceção lançada quando um dispositivo nulo é fornecido onde não é permitido.
 *
 * <p>Substitui o uso genérico de {@link NullPointerException} por uma exceção
 * com semântica de domínio, tornando o diagnóstico de erros mais claro.</p>
 */
public class DispositivoNuloException extends RuntimeException {

    public DispositivoNuloException(String mensagem) {
        super(mensagem);
    }

    public DispositivoNuloException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
