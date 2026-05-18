package org.example.view;

import org.example.model.ItemPedido;
import org.example.model.Pedido;

import java.util.List;

/**
 * Responsável por exibir informações do pedido ao usuário.
 *
 * <p>Separa a responsabilidade de renderização da lógica de negócio,
 * seguindo o princípio da responsabilidade única (SRP).</p>
 */
public class PedidoView {

    private PedidoView() {}

    /**
     * Exibe o resumo completo do pedido com itens e valor total.
     *
     * @param pedido pedido a ser exibido
     */
    public static void exibirResumo(Pedido pedido) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("RESUMO DO PEDIDO");
        System.out.println("=".repeat(50));

        List<ItemPedido> itens = pedido.itens();
        for (int i = 0; i < itens.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, itens.get(i));
        }

        System.out.println("─".repeat(50));
        System.out.printf("Valor Total dos Produtos: R$ %.2f%n", pedido.valorTotalProdutos());
        System.out.println("=".repeat(50) + "\n");
    }

    /**
     * Exibe a confirmação após o produto ser adicionado ao carrinho.
     *
     * @param item item que foi adicionado
     */
    public static void exibirItemAdicionado(ItemPedido item) {
        System.out.printf("%s unidade(s) de '%s' adicionada(s) ao carrinho!%n%n",
                item.getQuantidade(), item.getProduto().getNomeProduto());
    }
}
