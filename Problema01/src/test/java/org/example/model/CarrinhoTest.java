package org.example.model;

import org.example.model.vo.NomeProduto;
import org.example.model.vo.PesoEmKg;
import org.example.model.vo.Quantidade;
import org.example.model.vo.ValorMonetario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CarrinhoTest {

    private Carrinho carrinho;

    @BeforeEach
    void setUp() {
        carrinho = new Carrinho();
    }

    @Test
    void deveIniciarVazio() {
        assertTrue(carrinho.estaVazio());
    }

    @Test
    void deveRetornarValorTotalZeroQuandoVazio() {
        assertEquals(0.00, carrinho.valorTotal(), 0.01);
    }

    @Test
    void deveRetornarListaVaziaQuandoVazio() {
        assertTrue(carrinho.itens().isEmpty());
    }

    @Test
    void deveNaoEstarVazioAposAdicionarItem() {
        carrinho.adicionar(item("Livro A", 50.00f, 0.5f, 1));
        assertFalse(carrinho.estaVazio());
    }

    @Test
    void deveCalcularValorTotalDeUmItem() {
        // R$50 x 2 = R$100
        carrinho.adicionar(item("Livro A", 50.00f, 0.5f, 2));
        assertEquals(100.00, carrinho.valorTotal(), 0.01);
    }

    @Test
    void deveCalcularValorTotalDeVariosItens() {
        // R$50 x1 + R$30 x2 = R$50 + R$60 = R$110
        carrinho.adicionar(item("Livro A", 50.00f, 0.5f, 1));
        carrinho.adicionar(item("Livro B", 30.00f, 0.3f, 2));
        assertEquals(110.00, carrinho.valorTotal(), 0.01);
    }

    @Test
    void deveConterTodosOsItensAdicionados() {
        carrinho.adicionar(item("Livro A", 50.00f, 0.5f, 1));
        carrinho.adicionar(item("Livro B", 30.00f, 0.3f, 1));
        assertEquals(2, carrinho.itens().size());
    }

    @Test
    void deveAceitarMesmoItemAdicionadoMultiplasVezes() {
        ItemPedido itemA = item("Livro A", 50.00f, 0.5f, 1);
        carrinho.adicionar(itemA);
        carrinho.adicionar(itemA);
        assertEquals(2, carrinho.itens().size());
        assertEquals(100.00, carrinho.valorTotal(), 0.01);
    }

    @Test
    void deveLimparTodosOsItens() {
        carrinho.adicionar(item("Livro A", 50.00f, 0.5f, 1));
        carrinho.adicionar(item("Livro B", 30.00f, 0.3f, 1));
        carrinho.limpar();
        assertTrue(carrinho.estaVazio());
    }

    @Test
    void deveRetornarValorZeroAposLimpar() {
        carrinho.adicionar(item("Livro A", 50.00f, 0.5f, 3));
        carrinho.limpar();
        assertEquals(0.00, carrinho.valorTotal(), 0.01);
    }

    @Test
    void deveRetornarListaImutavel() {
        carrinho.adicionar(item("Livro A", 50.00f, 0.5f, 1));
        List<ItemPedido> itens = carrinho.itens();
        assertThrows(UnsupportedOperationException.class,
                () -> itens.add(item("Livro Intruso", 10.00f, 0.1f, 1)));
    }

    @Test
    void deveRemoverItemDaListaImutavelLancarExcecao() {
        carrinho.adicionar(item("Livro A", 50.00f, 0.5f, 1));
        List<ItemPedido> itens = carrinho.itens();
        assertThrows(UnsupportedOperationException.class, () -> itens.remove(0));
    }

    private static ItemPedido item(String nome, float valor, float peso, int qtd) {
        Produto produto = new Produto(new NomeProduto(nome), new ValorMonetario(valor), new PesoEmKg(peso));
        return new ItemPedido(produto, new Quantidade(qtd));
    }
}
