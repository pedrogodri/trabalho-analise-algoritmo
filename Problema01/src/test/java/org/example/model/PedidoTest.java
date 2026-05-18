package org.example.model;

import org.example.exceptions.EntregaNaoDisponivelException;
import org.example.implementation.entrega.PacEntrega;
import org.example.implementation.entrega.RetiradaLocalEntrega;
import org.example.implementation.entrega.SedexEntrega;
import org.example.model.vo.NomeProduto;
import org.example.model.vo.PesoEmKg;
import org.example.model.vo.Quantidade;
import org.example.model.vo.ValorMonetario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PedidoTest {

    private Pedido pedido;

    @BeforeEach
    void setUp() {
        // Dois livros: 0.4kg + 0.3kg = 0.7kg total
        pedido = new Pedido();
        pedido.adicionar(item("Livro A", 50.00f, 0.4f, 1));
        pedido.adicionar(item("Livro B", 30.00f, 0.3f, 1));
    }

    @Test
    void deveCalcularPesoTotalCorretamente() {
        assertEquals(0.7f, pedido.pesoTotalEmKg().valor(), 0.001f);
    }

    @Test
    void deveCalcularValorTotalDeProdutosCorretamente() {
        assertEquals(80.00, pedido.valorTotalProdutos(), 0.01);
    }

    @Test
    void deveCalcularFretePacCorretamente() {
        // 0.7kg <= 1kg -> R$10,00
        assertEquals(10.00, pedido.calcularFrete(new PacEntrega()));
    }

    @Test
    void deveCalcularFreteSedexCorretamente() {
        // 0.7kg > 0.5kg e <= 1kg -> R$20,00
        assertEquals(20.00, pedido.calcularFrete(new SedexEntrega()));
    }

    @Test
    void deveCalcularFreteRetiradaLocalComoZero() {
        assertEquals(0.00, pedido.calcularFrete(new RetiradaLocalEntrega()));
    }

    @Test
    void deveLancarExcecaoQuandoPedidoUltrapassaLimiteDoPAC() {
        Pedido pedidoPesado = new Pedido();
        pedidoPesado.adicionar(item("Enciclopedia", 200.00f, 3.0f, 1));

        EntregaNaoDisponivelException excecao = assertThrows(EntregaNaoDisponivelException.class,
                () -> pedidoPesado.calcularFrete(new PacEntrega()));

        assertEquals("PAC nao aceita pedidos acima de 2kg", excecao.getMessage());
    }

    @Test
    void deveAcumularPesoDeMultiplosItensComQuantidade() {
        // 1 livro de 0.6kg, quantidade 2 -> 1.2kg total
        Pedido pedidoComQuantidade = new Pedido();
        pedidoComQuantidade.adicionar(item("Livro C", 40.00f, 0.6f, 2));

        // 1.2kg no Sedex -> 2 grupos de 100g -> R$46,50 + R$3,00 = R$49,50
        assertEquals(49.50, pedidoComQuantidade.calcularFrete(new SedexEntrega()), 0.01);
    }

    @Test
    void deveRetornarPesoZeroParaPedidoVazio() {
        // Pedido vazio tem peso mínimo representável (praticamente zero)
        assertTrue(new Pedido().pesoTotalEmKg().valor() < 0.001f);
    }

    @Test
    void deveRetornarValorZeroParaPedidoVazio() {
        assertEquals(0.00, new Pedido().valorTotalProdutos(), 0.01);
    }

    @Test
    void deveCalcularFreteRetiradaLocalZeroParaPedidoVazio() {
        assertEquals(0.00, new Pedido().calcularFrete(new RetiradaLocalEntrega()), 0.01);
    }

    @Test
    void deveCalcularValorTotalComVariosItensEQuantidades() {
        // Livro A: R$50 x2 = R$100 | Livro B: R$30 x3 = R$90 => total R$190
        Pedido pedidoMisto = new Pedido();
        pedidoMisto.adicionar(item("Livro A", 50.00f, 0.4f, 2));
        pedidoMisto.adicionar(item("Livro B", 30.00f, 0.3f, 3));
        assertEquals(190.00, pedidoMisto.valorTotalProdutos(), 0.01);
    }

    private static ItemPedido item(String nome, float valor, float peso, int qtd) {
        Produto produto = new Produto(new NomeProduto(nome), new ValorMonetario(valor), new PesoEmKg(peso));
        return new ItemPedido(produto, new Quantidade(qtd));
    }
}
