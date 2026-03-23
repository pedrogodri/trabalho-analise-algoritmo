package org.example.dominio.empresa;

import org.example.dominio.acao.PrecoAcao;

/**
 * Contrato do lado Subject no padrão Observer.
 *
 * <p>Implementado por {@code Empresa}. Gerencia o ciclo de vida das inscrições:
 * observadores podem se inscrever para receber notificações de preço e
 * se desinscrever quando não quiserem mais atualizações.</p>
 *
 * <p>A notificação ({@code notificarObservadores}) é chamada internamente pela
 * {@code Empresa} após cada atualização de preço — nunca deve ser chamada
 * por código externo.</p>
 */
public interface SujeitoDePreco {

    /**
     * Inscreve um observador para receber notificações de alteração de preço.
     *
     * @param observador investidor que deseja ser notificado
     */
    void inscrever(ObservadorDePreco observador);

    /**
     * Remove a inscrição de um observador. Após isso, ele não receberá
     * mais notificações desta empresa.
     *
     * @param observador investidor que deseja parar de receber notificações
     */
    void desinscrever(ObservadorDePreco observador);

    /**
     * Notifica todos os observadores inscritos com o novo preço.
     * Chamado internamente pela Empresa; não deve ser invocado externamente.
     *
     * @param novoPreco preço estabelecido após a transação
     */
    void notificarObservadores(PrecoAcao novoPreco);
}
