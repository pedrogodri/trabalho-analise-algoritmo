package org.example.model;

import org.example.model.vo.NomeProduto;
import org.example.model.vo.PesoEmKg;
import org.example.model.vo.Quantidade;
import org.example.model.vo.ValorMonetario;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class ItemPedidoTest {

    @Test
    void deveCalcularSubtotalParaQuantidadeUm() {
        ItemPedido item = item("Livro Unico", 89.90f, 0.45f, 1);
        assertEquals(89.90f, item.getSubtotal(), 0.01f);
    }

    @Test
    void deveCalcularSubtotalMultiplicandoValorPorQuantidade() {
        // R$50 x 3 = R$150
        ItemPedido item = item("Livro Teste", 50.00f, 0.5f, 3);
        assertEquals(150.00f, item.getSubtotal(), 0.01f);
    }

    @Test
    void deveCalcularPesoTotalParaQuantidadeUm() {
        ItemPedido item = item("Livro Leve", 30.00f, 0.4f, 1);
        assertEquals(0.4f, item.getPesoTotal(), 0.001f);
    }

    @Test
    void deveCalcularPesoTotalMultiplicandoPesoPorQuantidade() {
        // 0.4kg x 2 = 0.8kg
        ItemPedido item = item("Livro Teste", 30.00f, 0.4f, 2);
        assertEquals(0.8f, item.getPesoTotal(), 0.001f);
    }

    @Test
    void deveCalcularSubtotalEPesoParaGrandesQuantidades() {
        // R$20 x 10 = R$200 | 0.3kg x 10 = 3.0kg
        ItemPedido item = item("Livro Barato", 20.00f, 0.3f, 10);
        assertEquals(200.00f, item.getSubtotal(), 0.01f);
        assertEquals(3.0f, item.getPesoTotal(), 0.001f);
    }

    @Test
    void deveConterNomeDoProdutoNoToString() {
        ItemPedido item = item("Dom Casmurro", 35.00f, 0.3f, 2);
        assertTrue(item.toString().contains("Dom Casmurro"));
    }

    @Test
    void deveConterQuantidadeNoToString() {
        ItemPedido item = item("Livro Alpha", 50.00f, 0.5f, 4);
        assertTrue(item.toString().contains("4"));
    }

    @Test
    void deveConterSubtotalNoToString() {
        ItemPedido item = item("Livro Beta", 50.00f, 0.5f, 2);
        // subtotal = R$100 -> toString deve conter "100"
        assertTrue(item.toString().contains("100"));
    }

    private static ItemPedido item(String nome, float valor, float peso, int qtd) {
        Produto produto = new Produto(new NomeProduto(nome), new ValorMonetario(valor), new PesoEmKg(peso));
        return new ItemPedido(produto, new Quantidade(qtd));
    }
}
