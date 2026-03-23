package org.example.dominio.empresa;

import org.example.dominio.acao.PrecoAcao;

/**
 * Interface que permite ao {@code LivroDeOrdens} atualizar o preço da empresa
 * sem depender diretamente da classe {@code Empresa}.
 *
 * <p>Quebra a dependência entre {@code dominio.ordem} e {@code dominio.empresa}:
 * o livro de ordens só conhece este contrato; a empresa o implementa.</p>
 */
public interface AlvoDeAtualizacaoDePreco {

    /**
     * Atualiza o preço da ação da empresa e notifica os observadores inscritos.
     *
     * @param novoPreco preço resultante da transação executada
     */
    void atualizarPreco(PrecoAcao novoPreco);

    /**
     * Informa o nome da empresa, usado para registros de transação.
     */
    NomeDaEmpresa obterNome();

    /**
     * Indica se a empresa já possui um preço de mercado estabelecido.
     * Retorna {@code false} antes da primeira transação ocorrer.
     */
    boolean possuiPrecoEstabelecido();
}
