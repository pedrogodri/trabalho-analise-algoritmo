package org.example.observer;

import org.example.dominio.acao.PrecoAcao;
import org.example.dominio.empresa.NomeDaEmpresa;

/**
 * Contrato do lado Observer no padrão Observer.
 *
 * <p>Implementado por {@code Investidor}. O método {@code aoAtualizarPreco}
 * é chamado automaticamente pelo {@code SujeitoDePreco} (Empresa) sempre que
 * o preço de uma ação for alterado por uma transação executada no
 * {@code LivroDeOrdens}.</p>
 *
 * <p>Os observadores não devem alterar estado do sujeito dentro deste método
 * para evitar loops de notificação.</p>
 */
public interface ObservadorDePreco {

    /**
     * Chamado quando o preço da empresa observada é atualizado.
     *
     * @param nomeDaEmpresa nome da empresa cujo preço foi alterado
     * @param novoPreco     novo preço estabelecido após a transação
     */
    void aoAtualizarPreco(NomeDaEmpresa nomeDaEmpresa, PrecoAcao novoPreco);
}
