package org.example.model;

import org.example.entrega.PacEntrega;
import org.example.entrega.RetiradaLocalEntrega;
import org.example.entrega.SedexEntrega;
import org.example.exceptions.EntregaNaoDisponivelException;
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
        Carrinho carrinho = new Carrinho();
        carrinho.adicionar(item("Livro A", 50.00f, 0.4f, 1));
        carrinho.adicionar(item("Livro B", 30.00f, 0.3f, 1));
        pedido = new Pedido(carrinho);
    }

    @Test
    void deveCalcularPesoTotalCorretamente() {
        assertEquals(0.7f, pedido.pesoTotalEmKg(), 0.001f);
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
        Carrinho carrinhoPesado = new Carrinho();
        carrinhoPesado.adicionar(item("Enciclopedia", 200.00f, 3.0f, 1));

        EntregaNaoDisponivelException excecao = assertThrows(EntregaNaoDisponivelException.class,
                () -> new Pedido(carrinhoPesado).calcularFrete(new PacEntrega()));

        assertEquals("PAC nao aceita pedidos acima de 2kg", excecao.getMessage());
    }

    @Test
    void deveAcumularPesoDeMultiplosItensComQuantidade() {
        // 1 livro de 0.6kg, quantidade 2 -> 1.2kg total
        Carrinho carrinhoComQuantidade = new Carrinho();
        carrinhoComQuantidade.adicionar(item("Livro C", 40.00f, 0.6f, 2));

        // 1.2kg no Sedex -> 2 grupos de 100g -> R$46,50 + R$3,00 = R$49,50
        assertEquals(49.50, new Pedido(carrinhoComQuantidade).calcularFrete(new SedexEntrega()), 0.01);
    }

    private static ItemPedido item(String nome, float valor, float peso, int qtd) {
        Produto produto = new Produto(new NomeProduto(nome), new ValorMonetario(valor), new PesoEmKg(peso));
        return new ItemPedido(produto, new Quantidade(qtd));
    }
}
