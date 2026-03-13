package org.example.exceptions;

/**
 * Lançada quando uma modalidade de entrega não suporta o peso do pedido.
 *
 * <p>Atualmente utilizada por {@link org.example.entrega.PacEntrega}
 * para pedidos acima de 2 kg. Permite que a camada de apresentação
 * capture e exiba a mensagem adequada ao usuário sem quebrar o fluxo principal.</p>
 */
public class EntregaNaoDisponivelException extends RuntimeException {

    public EntregaNaoDisponivelException(String mensagem) {
        super(mensagem);
    }
}
