package org.example;

import java.util.List;
import java.util.Scanner;

import org.example.interfaces.IFormatoEntrega;
import org.example.model.EnderecoEntrega;
import org.example.model.ItemPedido;
import org.example.model.Pedido;
import org.example.model.Produto;
import org.example.model.vo.Quantidade;
import org.example.view.Catalogo;
import org.example.view.CatalogoView;
import org.example.view.EntregaView;
import org.example.view.PedidoView;
import org.example.view.PreenchimentoDados;


public class Main {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            List<Produto> catalogo = Catalogo.inicializarCatalogo();

            boolean atendimentoAtivo = true;

            while (atendimentoAtivo) {
                exibirMenuPrincipal();
                int opcao = obterOpcao(scanner);

                switch (opcao) {
                    case 1 -> {
                        if (!iniciarCompra(scanner, catalogo))
                            atendimentoAtivo = false;
                    }
                    case 2 -> {
                        atendimentoAtivo = false;
                        exibirMensagemEncerramento();
                    }
                    default -> System.out.println("Opção inválida! Tente novamente.\n");
                }
            }
        }
    }

    private static void exibirMenuPrincipal() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("┌─────────────────────────────────────────────────┐");
        System.out.println("│    BEM-VINDO À LIVRARIA ONLINE                  │");
        System.out.println("└─────────────────────────────────────────────────┘");
        System.out.println("=".repeat(50));
        System.out.println("1. Iniciar Compra");
        System.out.println("2. Encerrar Atendimento");
        System.out.println("=".repeat(50));
        System.out.print("Escolha uma opção: ");
    }

    private static int obterOpcao(Scanner scanner) {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static boolean iniciarCompra(Scanner scanner, List<Produto> catalogo) {
        Pedido pedido = new Pedido();
        boolean emAndamento = true;
        while (emAndamento) {
            emAndamento = processarIteracaoCompra(scanner, catalogo, pedido);
        }
        return false;
    }

    private static boolean processarIteracaoCompra(Scanner scanner, List<Produto> catalogo, Pedido pedido) {
        CatalogoView.exibir(catalogo);
        System.out.println("0. Encerrar Atendimento");
        System.out.println("─".repeat(50));
        System.out.print("Digite o numero do livro desejado ou 0 para sair: ");

        int selecao = obterOpcao(scanner);

        if (selecao == 0) {
            exibirMensagemEncerramento();
            return false;
        }

        if (selecao < 1 || selecao > catalogo.size()) {
            System.out.println("Opção inválida! Digite um número válido.\n");
            return true;
        }

        return processarLivroSelecionado(scanner, catalogo.get(selecao - 1), pedido);
    }

    private static boolean processarLivroSelecionado(Scanner scanner, Produto livro, Pedido pedido) {
        System.out.println("\nLivro selecionado: " + livro.getNomeProduto());
        System.out.println("Preço: " + livro.getValorMonetario());

        Quantidade quantidade = PreenchimentoDados.solicitarQuantidade(scanner);

        if (quantidade == null) {
            exibirMensagemEncerramento();
            return false;
        }

        ItemPedido item = new ItemPedido(livro, quantidade);
        pedido.adicionar(item);
        PedidoView.exibirItemAdicionado(item);

        return processarProximaAcao(scanner, pedido);
    }

    private static boolean processarProximaAcao(Scanner scanner, Pedido pedido) {
        int acao = perguntarProximaAcao(scanner);

        return switch (acao) {
            case 1 -> true;
            case 2 -> {
                PedidoView.exibirResumo(pedido);
                processarEntrega(scanner, pedido);
                exibirMensagemEncerramento();
                yield false;
            }
            case 3 -> {
                exibirMensagemEncerramento();
                yield false;
            }
            default -> {
                System.out.println("Opção inválida!\n");
                yield true;
            }
        };
    }

    private static int perguntarProximaAcao(Scanner scanner) {
        System.out.println("\n" + "─".repeat(50));
        System.out.println("O que deseja fazer agora?");
        System.out.println("1. Continuar comprando");
        System.out.println("2. Encerrar pedido e ir para entrega");
        System.out.println("3. Encerrar atendimento");
        System.out.println("─".repeat(50));
        System.out.print("Escolha uma opção: ");
        return obterOpcao(scanner);
    }

    private static void processarEntrega(Scanner scanner, Pedido pedido) {
        EntregaView.exibirCabecalho();
        EnderecoEntrega endereco = EntregaView.coletarEndereco(scanner);
        IFormatoEntrega modalidade = EntregaView.selecionarModalidade(scanner, pedido);
        double frete = pedido.calcularFrete(modalidade);
        EntregaView.exibirConfirmacao(endereco, pedido, modalidade, frete);
    }

    private static void exibirMensagemEncerramento() {
        System.out.println("\n" + "*".repeat(51));
        System.out.println("\n+" + "-".repeat(49) + "+");
        System.out.printf("|%-49s|%n", "");
        System.out.printf("|%-49s|%n", "  Obrigado por visitar nossa livraria!");
        System.out.printf("|%-49s|%n", "");
        System.out.printf("|%-49s|%n", "  Volte sempre!");
        System.out.printf("|%-49s|%n", "");
        System.out.println("+" + "-".repeat(49) + "+");
        System.out.println("\n" + "*".repeat(51) + "\n");
    }
}
