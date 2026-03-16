package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.example.exceptions.DadoInvalidoException;
import org.example.exceptions.EntregaNaoDisponivelException;
import org.example.implementation.entrega.PacEntrega;
import org.example.implementation.entrega.RetiradaLocalEntrega;
import org.example.implementation.entrega.SedexEntrega;
import org.example.interfaces.IFormatoEntrega;
import org.example.model.EnderecoEntrega;
import org.example.model.ItemPedido;
import org.example.model.Pedido;
import org.example.model.Produto;
import org.example.model.vo.Cep;
import org.example.model.vo.Estado;
import org.example.model.vo.NomeProduto;
import org.example.model.vo.PesoEmKg;
import org.example.model.vo.Quantidade;
import org.example.model.vo.ValorMonetario;


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

    // private static List<Produto> inicializarCatalogo() {
    //     List<Produto> catalogo = new ArrayList<>();
    //     catalogo.add(produto("O Senhor dos Anéis", 89.90f, 0.45f));
    //     catalogo.add(produto("Harry Potter e a Pedra Filosofal", 65.00f, 0.40f));
    //     catalogo.add(produto("1984 - George Orwell", 45.90f, 0.35f));
    //     catalogo.add(produto("O Código Da Vinci", 59.90f, 0.42f));
    //     catalogo.add(produto("Dom Casmurro - Machado de Assis", 35.00f, 0.30f));
    //     catalogo.add(produto("A Revolução dos Bichos", 38.50f, 0.28f));
    //     catalogo.add(produto("O Pequeno Príncipe", 42.00f, 0.25f));
    //     catalogo.add(produto("Cem Anos de Solidão", 72.50f, 0.48f));
    //     return catalogo;
    // }

    // private static Produto produto(String nome, float valor, float peso) {
    //     return new Produto(new NomeProduto(nome), new ValorMonetario(valor), new PesoEmKg(peso));
    // }

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
        boolean continuar = true;
        boolean deveEncerrar = false;

        while (continuar) {
            continuar = processarIteracaoCompra(scanner, catalogo, pedido);
            if (!continuar) deveEncerrar = true;
        }
        return !deveEncerrar;
    }

    private static boolean processarIteracaoCompra(Scanner scanner, List<Produto> catalogo, Pedido pedido) {
        exibirCatalogo(catalogo);
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
        System.out.println("\nLivro selecionado: " + livro.getNome());
        System.out.printf("Preco: R$ %.2f%n", livro.getValor());

        Quantidade quantidade = solicitarQuantidade(scanner);

        if (quantidade == null) {
            exibirMensagemEncerramento();
            return false;
        }

        pedido.adicionar(new ItemPedido(livro, quantidade));
        System.out.printf("%s unidade(s) de '%s' adicionada(s) ao carrinho!%n%n", quantidade, livro.getNome());

        return processarProximaAcao(scanner, pedido);
    }

    private static boolean processarProximaAcao(Scanner scanner, Pedido pedido) {
        int acao = perguntarProximaAcao(scanner);

        return switch (acao) {
            case 1 -> true;
            case 2 -> {
                exibirResumoCompra(pedido);
                exibirTelaEntrega(scanner, pedido);
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

    private static void exibirCatalogo(List<Produto> catalogo) {
        System.out.println("\n" + "─".repeat(50));
        System.out.println("CATÁLOGO DE LIVROS");
        System.out.println("─".repeat(50));

        for (int i = 0; i < catalogo.size(); i++) {
            Produto livro = catalogo.get(i);
            System.out.printf("%d. %s%n", i + 1, livro.getNome());
            System.out.printf("   Preco: R$ %.2f%n", livro.getValor());
        }

        System.out.println("─".repeat(50));
    }

    // Retorna null quando o usuário quer encerrar (digita 0)
    // Loop até receber entrada válida — validação centralizada no VO Quantidade
    private static Quantidade solicitarQuantidade(Scanner scanner) {
        while (true) {
            System.out.print("\nQuantas unidades deseja (ou 0 para encerrar)? ");
            try {
                int valor = Integer.parseInt(scanner.nextLine());
                if (valor == 0) {
                    System.out.println("\nEncerrando atendimento...");
                    return null;
                }
                return new Quantidade(valor);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida! Digite um número inteiro.");
            } catch (DadoInvalidoException e) {
                System.out.println(e.getMessage());
            }
        }
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

    private static void exibirResumoCompra(Pedido pedido) {
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

    private static void exibirTelaEntrega(Scanner scanner, Pedido pedido) {
        System.out.println("+" + "-".repeat(48) + "+");
        System.out.printf("|%-48s|%n", "");
        System.out.printf("|%16s%-32s|%n", "", "DADOS DE ENTREGA");
        System.out.printf("|%-48s|%n", "");
        System.out.println("+" + "-".repeat(48) + "+");

        EnderecoEntrega endereco = coletarEndereco(scanner);

        IFormatoEntrega formatoEntrega = selecionarModalidadeEntrega(scanner, pedido);
        double frete = pedido.calcularFrete(formatoEntrega);

        exibirConfirmacaoCompra(endereco, pedido, formatoEntrega, frete);
    }

    private static EnderecoEntrega coletarEndereco(Scanner scanner) {
        String nome = PreenchimentoDados.solicitarCampoObrigatorio(scanner, "Seu nome completo: ", "Nome do cliente");
        Cep cep = PreenchimentoDados.solicitarCep(scanner);
        String rua = PreenchimentoDados.solicitarCampoObrigatorio(scanner, "Rua: ", "Logradouro");
        String numero = PreenchimentoDados.solicitarCampoObrigatorio(scanner, "Numero: ", "Número do endereço");
        System.out.print("Complemento (opcional): ");
        String complemento = scanner.nextLine();
        String cidade = PreenchimentoDados.solicitarCampoObrigatorio(scanner, "Cidade: ", "Cidade");
        Estado estado = PreenchimentoDados.solicitarEstado(scanner);
        return new EnderecoEntrega(nome, cep, rua, numero, complemento, cidade, estado);
    }

    // private static String solicitarCampoObrigatorio(Scanner scanner, String prompt, String nomeCampo) {
    //     while (true) {
    //         System.out.print(prompt);
    //         String valor = scanner.nextLine();
    //         if (valor != null && !valor.isBlank()) {
    //             return valor;
    //         }
    //         System.out.println(nomeCampo + " não pode ser vazio");
    //     }
    // }

    // private static Cep solicitarCep(Scanner scanner) {
    //     while (true) {
    //         System.out.print("CEP: ");
    //         try {
    //             return new Cep(scanner.nextLine());
    //         } catch (DadoInvalidoException e) {
    //             System.out.println(e.getMessage());
    //         }
    //     }
    // }

    // private static Estado solicitarEstado(Scanner scanner) {
    //     while (true) {
    //         System.out.print("Estado (sigla, ex: SC): ");
    //         try {
    //             return new Estado(scanner.nextLine());
    //         } catch (DadoInvalidoException e) {
    //             System.out.println(e.getMessage());
    //         }
    //     }
    // }

    private static void exibirConfirmacaoCompra(
            EnderecoEntrega endereco, Pedido pedido, IFormatoEntrega formatoEntrega, double frete) {

        System.out.println("\n" + "═".repeat(50));
        System.out.println("CONFIRMAÇÃO DE COMPRA");
        System.out.println("═".repeat(50));
        //System.out.printf("Cliente:  %s%n", endereco.nomeCliente()); //colocar nome do cliente no pedido
        System.out.printf("Endereco: %s%n", endereco.formatarLougradouro());
        System.out.printf("CEP:      %s%n", endereco.concatenarCepCidadeEstado());
        System.out.println("─".repeat(50));
        System.out.printf("Valor da Compra:  R$ %.2f%n", pedido.valorTotalProdutos());
        System.out.printf("Tipo de Entrega:  %s%n", formatoEntrega.descricao());
        System.out.printf("Taxa de Entrega:  R$ %.2f%n", frete);
        System.out.println("─".repeat(50));
        System.out.printf("VALOR TOTAL:      R$ %.2f%n", pedido.valorTotalProdutos() + frete);
        System.out.println("═".repeat(50));
        System.out.println("\nPedido confirmado com sucesso!");
        System.out.println("Um e-mail de confirmação foi enviado para você.\n");
    }

    private static IFormatoEntrega selecionarModalidadeEntrega(Scanner scanner, Pedido pedido) {
        List<IFormatoEntrega> todasModalidades = List.of(
                new PacEntrega(),
                new SedexEntrega(),
                new RetiradaLocalEntrega()
        );

        System.out.println("\n" + "─".repeat(50));
        System.out.println("MODALIDADES DE ENTREGA");
        System.out.println("─".repeat(50));

        List<IFormatoEntrega> disponiveis = new ArrayList<>();
        int numeracao = 1;

        for (IFormatoEntrega modalidade : todasModalidades) {
            try {
                double preco = modalidade.calcular(pedido.pesoTotalEmKg());
                System.out.printf("%d. %-22s R$ %.2f%n", numeracao++, modalidade.descricao(), preco);
                disponiveis.add(modalidade);
            } catch (EntregaNaoDisponivelException e) {
                System.out.printf("   %-22s Não disponível para este peso%n", modalidade.descricao());
            }
        }

        System.out.println("─".repeat(50));
        System.out.print("Escolha a modalidade de entrega: ");

        while (true) {
            int opcao = obterOpcao(scanner);
            if (opcao >= 1 && opcao <= disponiveis.size()) {
                return disponiveis.get(opcao - 1);
            }
            System.out.print("Opção inválida! Tente novamente: ");
        }
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
