package org.example.mediador;

import org.example.dominio.acao.PrecoAcao;
import org.example.dominio.ordem.ListaDeOrdens;
import org.example.dominio.ordem.Ordem;
import org.example.dominio.ordem.OrdemDeCompra;
import org.example.dominio.ordem.OrdemDeVenda;

import java.util.List;
import java.util.Optional;

/**
 * Algoritmo puro e sem estado que encontra pares elegíveis de compra/venda.
 *
 * <p><b>Critério de elegibilidade</b>: uma ordem de compra e uma de venda
 * são compatíveis quando o preço do comprador é maior ou igual ao preço
 * do vendedor ({@code precoCompra >= precoVenda}). Isso reflete a semântica
 * de ordens limitadas: o comprador aceita pagar até seu preço máximo,
 * e o vendedor aceita receber pelo menos seu preço mínimo.</p>
 *
 * <p><b>Algoritmo</b>:
 * <ol>
 *   <li>Ordena as ordens de compra por preço decrescente (maior lance primeiro).</li>
 *   <li>Para cada ordem de compra, busca a primeira venda cujo preço
 *       seja menor ou igual ao lance do comprador.</li>
 *   <li>Retorna o primeiro par encontrado ou {@link Optional#empty()} se
 *       não houver combinação possível.</li>
 * </ol>
 * </p>
 *
 * <p>Não possui efeitos colaterais; nunca altera o estado das ordens.</p>
 */
public final class CombinadorDeOrdens {

    /**
     * Tenta encontrar um par compatível entre as ordens de compra e venda pendentes.
     *
     * @param ordensDeCompra lista de ordens de compra pendentes
     * @param ordensDeVenda  lista de ordens de venda pendentes
     * @return par encontrado, ou {@link Optional#empty()} se não houver match
     */
    public Optional<ParDeOrdens> encontrarCombinacao(ListaDeOrdens ordensDeCompra,
                                                      ListaDeOrdens ordensDeVenda) {
        ListaDeOrdens comprasOrdenadas = ordensDeCompra.ordenadosPorPrecoDecrescente();
        List<Ordem> listaDeCompras = comprasOrdenadas.comoLista();

        for (Ordem ordemAtual : listaDeCompras) {
            OrdemDeCompra compra = (OrdemDeCompra) ordemAtual;
            PrecoAcao precoMaximoDoComprador = compra.getPrecoAlvo();
            Optional<OrdemDeVenda> vendaCompativel =
                ordensDeVenda.encontrarVendaComPrecoAteMáximo(precoMaximoDoComprador);

            if (vendaCompativel.isPresent()) {
                return Optional.of(new ParDeOrdens(compra, vendaCompativel.get()));
            }
        }
        return Optional.empty();
    }
}
