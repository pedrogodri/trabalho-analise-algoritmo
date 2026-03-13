package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.example.entrega.EstrategiaEntrega;
import org.example.entrega.PacEntrega;
import org.example.entrega.RetiradaLocalEntrega;
import org.example.entrega.SedexEntrega;
import org.example.exceptions.DadoInvalidoException;
import org.example.exceptions.EntregaNaoDisponivelException;
import org.example.model.Carrinho;
import org.example.model.EnderecoEntrega;
import org.example.model.ItemPedido;
import org.example.model.Pedido;
import org.example.model.Produto;
import org.example.model.vo.Cep;
import org.example.model.vo.Cidade;
import org.example.model.vo.Complemento;
import org.example.model.vo.Estado;
import org.example.model.vo.Logradouro;
import org.example.model.vo.NomeCliente;
import org.example.model.vo.NomeProduto;
import org.example.model.vo.NumeroEndereco;
import org.example.model.vo.PesoEmKg;
import org.example.model.vo.Quantidade;
import org.example.model.vo.ValorMonetario;

public class Main {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            List<Produto> catalogo = inicializarCatalogo();

            boolean atendimentoAtivo = true;

            while (atendimentoAtivo) {
                exibirMenuPrincipal();
                int opcao = obterOpcao(scanner);

                switch (opcao) {
                    case 1 -> iniciarCompra(scanner, catalogo);
                    case 2 -> cadastrarProduto(scanner, catalogo);
                    case 3 -> {
                        atendimentoAtivo = false;
                        exibirMensagemEncerramento();
                    }
                    default -> System.out.println("Opcao invalida! Tente novamente.\n");
                }
            }
        }
    }

    private static List<Produto> inicializarCatalogo() {
        List<Produto> catalogo = new ArrayList<>();
        catalogo.add(produto("O Senhor dos Anéis", 89.90f, 0.45f));
        catalogo.add(produto("Harry Potter e a Pedra Filosofal", 65.00f, 0.40f));
        catalogo.add(produto("1984 - George Orwell", 45.90f, 0.35f));
        catalogo.add(produto("O Código Da Vinci", 59.90f, 0.42f));
        catalogo.add(produto("Dom Casmurro - Machado de Assis", 35.00f, 0.30f));
        catalogo.add(produto("A Revolução dos Bichos", 38.50f, 0.28f));
        catalogo.add(produto("O Pequeno Príncipe", 42.00f, 0.25f));
        catalogo.add(produto("Cem Anos de Solidão", 72.50f, 0.48f));
        return catalogo;
    }

    private static Produto produto(String nome, float valor, float peso) {
        return new Produto(new NomeProduto(nome), new ValorMonetario(valor), new PesoEmKg(peso));
    }

    private static void cadastrarProduto(Scanner scanner, List<Produto> catalogo) {
        System.out.println("\n" + "─".repeat(50));
        System.out.println("CADASTRAR NOVO PRODUTO");
        System.out.println("─".repeat(50));

        NomeProduto nome = solicitarNome(scanner);
        ValorMonetario valor = solicitarValor(scanner);
        PesoEmKg peso = solicitarPeso(scanner);

        catalogo.add(new Produto(nome, valor, peso));

        System.out.println("\nProduto \"" + nome + "\" cadastrado com sucesso!\n");
    }

    private static NomeProduto solicitarNome(Scanner scanner) {
        while (true) {
            System.out.print("Nome do produto: ");
            try {
                return new NomeProduto(scanner.nextLine());
            } catch (DadoInvalidoException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static ValorMonetario solicitarValor(Scanner scanner) {
        while (true) {
            System.out.print("Valor (R$): ");
            try {
                float valor = Float.parseFloat(scanner.nextLine().replace(",", "."));
                return new ValorMonetario(valor);
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida! Digite um numero valido (ex: 49.90).");
            } catch (DadoInvalidoException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static PesoEmKg solicitarPeso(Scanner scanner) {
        while (true) {
            System.out.print("Peso em kg (ex: 0.45): ");
            try {
                float peso = Float.parseFloat(scanner.nextLine().replace(",", "."));
                return new PesoEmKg(peso);
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida! Digite um numero valido (ex: 0.45).");
            } catch (DadoInvalidoException e) {
                System.out.println(e.getMessage());
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
        System.out.println("2. Cadastrar Produto");
        System.out.println("3. Encerrar Atendimento");
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

    private static void iniciarCompra(Scanner scanner, List<Produto> catalogo) {
        Carrinho carrinho = new Carrinho();
        boolean continuar = true;

        while (continuar) {
            continuar = processarIteracaoCompra(scanner, catalogo, carrinho);
        }
    }

    private static boolean processarIteracaoCompra(Scanner scanner, List<Produto> catalogo, Carrinho carrinho) {
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
            System.out.println("Opcao invalida! Digite um numero valido.\n");
            return true;
        }

        return processarLivroSelecionado(scanner, catalogo.get(selecao - 1), carrinho);
    }

    private static boolean processarLivroSelecionado(Scanner scanner, Produto livro, Carrinho carrinho) {
        System.out.println("\nLivro selecionado: " + livro.getNome());
        System.out.printf("Preco: R$ %.2f%n", livro.getValor());

        Quantidade quantidade = solicitarQuantidade(scanner);

        if (quantidade == null) {
            exibirMensagemEncerramento();
            return false;
        }

        carrinho.adicionar(new ItemPedido(livro, quantidade));
        System.out.printf("%s unidade(s) de '%s' adicionada(s) ao carrinho!%n%n", quantidade, livro.getNome());

        return processarProximaAcao(scanner, carrinho);
    }

    private static boolean processarProximaAcao(Scanner scanner, Carrinho carrinho) {
        int acao = perguntarProximaAcao(scanner);

        return switch (acao) {
            case 1 -> true;
            case 2 -> {
                exibirResumoCompra(carrinho);
                exibirTelaEntrega(scanner, carrinho);
                carrinho.limpar();
                yield true;
            }
            case 3 -> {
                exibirMensagemEncerramento();
                yield false;
            }
            default -> {
                System.out.println("Opcao invalida!\n");
                yield true;
            }
        };
    }

    private static void exibirCatalogo(List<Produto> catalogo) {
        System.out.println("\n" + "─".repeat(50));
        System.out.println("CATALOGO DE LIVROS");
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
                System.out.println("Entrada invalida! Digite um numero inteiro.");
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
        System.out.print("Escolha uma opcao: ");

        return obterOpcao(scanner);
    }

    private static void exibirResumoCompra(Carrinho carrinho) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("RESUMO DO PEDIDO");
        System.out.println("=".repeat(50));

        List<ItemPedido> itens = carrinho.itens();
        for (int i = 0; i < itens.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, itens.get(i));
        }

        System.out.println("─".repeat(50));
        System.out.printf("Valor Total dos Produtos: R$ %.2f%n", carrinho.valorTotal());
        System.out.println("=".repeat(50) + "\n");
    }

    private static void exibirTelaEntrega(Scanner scanner, Carrinho carrinho) {
        System.out.println("╔" + "═".repeat(48) + "╗");
        System.out.println("║" + " ".repeat(48) + "║");
        System.out.println("║" + " ".repeat(15) + "DADOS DE ENTREGA" + " ".repeat(16) + "║");
        System.out.println("║" + " ".repeat(48) + "║");
        System.out.println("╚" + "═".repeat(48) + "╝");

        EnderecoEntrega endereco = coletarEndereco(scanner);

        Pedido pedido = new Pedido(carrinho);
        EstrategiaEntrega estrategiaSelecionada = selecionarModalidadeEntrega(scanner, pedido);
        double frete = pedido.calcularFrete(estrategiaSelecionada);

        exibirConfirmacaoCompra(endereco, pedido, estrategiaSelecionada, frete);
    }

    private static EnderecoEntrega coletarEndereco(Scanner scanner) {
        NomeCliente nome = solicitarNomeCliente(scanner);
        Cep cep = solicitarCep(scanner);
        Logradouro rua = solicitarLogradouro(scanner);
        NumeroEndereco numero = solicitarNumeroEndereco(scanner);
        Complemento complemento = solicitarComplemento(scanner);
        Cidade cidade = solicitarCidade(scanner);
        Estado estado = solicitarEstado(scanner);
        return new EnderecoEntrega(nome, cep, rua, numero, complemento, cidade, estado);
    }

    private static NomeCliente solicitarNomeCliente(Scanner scanner) {
        while (true) {
            System.out.print("Seu nome completo: ");
            try {
                return new NomeCliente(scanner.nextLine());
            } catch (DadoInvalidoException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static Cep solicitarCep(Scanner scanner) {
        while (true) {
            System.out.print("CEP: ");
            try {
                return new Cep(scanner.nextLine());
            } catch (DadoInvalidoException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static Logradouro solicitarLogradouro(Scanner scanner) {
        while (true) {
            System.out.print("Rua: ");
            try {
                return new Logradouro(scanner.nextLine());
            } catch (DadoInvalidoException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static NumeroEndereco solicitarNumeroEndereco(Scanner scanner) {
        while (true) {
            System.out.print("Numero: ");
            try {
                return new NumeroEndereco(scanner.nextLine());
            } catch (DadoInvalidoException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static Complemento solicitarComplemento(Scanner scanner) {
        System.out.print("Complemento (opcional): ");
        return new Complemento(scanner.nextLine());
    }

    private static Cidade solicitarCidade(Scanner scanner) {
        while (true) {
            System.out.print("Cidade: ");
            try {
                return new Cidade(scanner.nextLine());
            } catch (DadoInvalidoException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static Estado solicitarEstado(Scanner scanner) {
        while (true) {
            System.out.print("Estado (sigla, ex: SC): ");
            try {
                return new Estado(scanner.nextLine());
            } catch (DadoInvalidoException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void exibirConfirmacaoCompra(
            EnderecoEntrega endereco, Pedido pedido, EstrategiaEntrega estrategia, double frete) {

        System.out.println("\n" + "═".repeat(50));
        System.out.println("CONFIRMACAO DE COMPRA");
        System.out.println("═".repeat(50));
        System.out.printf("Cliente:  %s%n", endereco.nomeCliente());
        System.out.printf("Endereco: %s%n", endereco.linhaRua());
        System.out.printf("CEP:      %s%n", endereco.linhaCepCidadeEstado());
        System.out.println("─".repeat(50));
        System.out.printf("Valor da Compra:  R$ %.2f%n", pedido.valorTotalProdutos());
        System.out.printf("Tipo de Entrega:  %s%n", estrategia.descricao());
        System.out.printf("Taxa de Entrega:  R$ %.2f%n", frete);
        System.out.println("─".repeat(50));
        System.out.printf("VALOR TOTAL:      R$ %.2f%n", pedido.valorTotalProdutos() + frete);
        System.out.println("═".repeat(50));
        System.out.println("\nPedido confirmado com sucesso!");
        System.out.println("Um e-mail de confirmacao foi enviado para voce.\n");
    }

    private static EstrategiaEntrega selecionarModalidadeEntrega(Scanner scanner, Pedido pedido) {
        List<EstrategiaEntrega> todasModalidades = List.of(
                new PacEntrega(),
                new SedexEntrega(),
                new RetiradaLocalEntrega()
        );

        System.out.println("\n" + "─".repeat(50));
        System.out.println("MODALIDADES DE ENTREGA");
        System.out.println("─".repeat(50));

        List<EstrategiaEntrega> disponiveis = new ArrayList<>();
        int numeracao = 1;

        for (EstrategiaEntrega modalidade : todasModalidades) {
            try {
                double preco = modalidade.calcular(pedido.pesoTotalEmKg());
                System.out.printf("%d. %-22s R$ %.2f%n", numeracao++, modalidade.descricao(), preco);
                disponiveis.add(modalidade);
            } catch (EntregaNaoDisponivelException e) {
                System.out.printf("   %-22s Nao disponivel para este peso%n", modalidade.descricao());
            }
        }

        System.out.println("─".repeat(50));
        System.out.print("Escolha a modalidade de entrega: ");

        while (true) {
            int opcao = obterOpcao(scanner);
            if (opcao >= 1 && opcao <= disponiveis.size()) {
                return disponiveis.get(opcao - 1);
            }
            System.out.print("Opcao invalida! Tente novamente: ");
        }
    }

    private static void exibirMensagemEncerramento() {
        System.out.println("\n" + "*".repeat(50));
        System.out.println("\n┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
        System.out.println("┃                                                 ┃");
        System.out.println("┃  Obrigado por visitar nossa livraria!          ┃");
        System.out.println("┃                                                 ┃");
        System.out.println("┃  Ate breve! Volte sempre!                      ┃");
        System.out.println("┃                                                 ┃");
        System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
        System.out.println("\n" + "*".repeat(50) + "\n");
    }
}
