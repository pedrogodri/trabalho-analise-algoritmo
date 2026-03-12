package org.example;

import org.example.model.Produto;
import org.example.model.ItemPedido;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Produto> livros = inicializarCatalogo();
        
        boolean atendimentoAtivo = true;
        
        while (atendimentoAtivo) {
            exibirMenuPrincipal();
            int opcao = obterOpcao(scanner);
            
            switch (opcao) {
                case 1:
                    iniciarCompra(scanner, livros);
                    break;
                case 2:
                    atendimentoAtivo = false;
                    exibirMensagemEncerramento();
                    break;
                default:
                    System.out.println("Opcao invalida! Tente novamente.\n");
            }
        }
        
        scanner.close();
    }
    
    private static ArrayList<Produto> inicializarCatalogo() {
        ArrayList<Produto> livros = new ArrayList<>();
        livros.add(new Produto("O Senhor dos Anéis", 89.90f, 0.45f));
        livros.add(new Produto("Harry Potter e a Pedra Filosofal", 65.00f, 0.40f));
        livros.add(new Produto("1984 - George Orwell", 45.90f, 0.35f));
        livros.add(new Produto("O Código Da Vinci", 59.90f, 0.42f));
        livros.add(new Produto("Dom Casmurro - Machado de Assis", 35.00f, 0.30f));
        livros.add(new Produto("A Revolução dos Bichos", 38.50f, 0.28f));
        livros.add(new Produto("O Pequeno Príncipe", 42.00f, 0.25f));
        livros.add(new Produto("Cem Anos de Solidão", 72.50f, 0.48f));
        
        return livros;
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
    
    private static void iniciarCompra(Scanner scanner, ArrayList<Produto> livros) {
        ArrayList<ItemPedido> carrinho = new ArrayList<>();
        boolean continueCompra = true;
        
        while (continueCompra) {
            exibirCatalogo(livros);
            
            // Menu de seleção
            System.out.println("0. Encerrar Atendimento");
            System.out.println("─".repeat(50));
            System.out.print("Digite o numero do livro desejado ou 0 para sair: ");
            
            int selecaoLivro = obterOpcao(scanner);
            
            // Validar opção de saída
            if (selecaoLivro == 0) {
                continueCompra = false;
                exibirMensagemEncerramento();
                break;
            }
            
            // Validar seleção do livro
            if (selecaoLivro < 1 || selecaoLivro > livros.size()) {
                System.out.println("Opcao invalida! Digite um numero valido.\n");
                continue;
            }
            
            // Obter quantidade
            Produto livroSelecionado = livros.get(selecaoLivro - 1);
            System.out.println("\nLivro selecionado: " + livroSelecionado.getNome());
            System.out.printf("Preco: R$ %.2f%n", livroSelecionado.getValor());
            
            int quantidade = obterQuantidade(scanner);
            
            if (quantidade == -1) { // Usuário escolheu encerrar
                continueCompra = false;
                exibirMensagemEncerramento();
                break;
            }
            
            if (quantidade <= 0) {
                System.out.println("Quantidade invalida! Tente novamente.\n");
                continue;
            }
            
            // Adicionar ao carrinho
            carrinho.add(new ItemPedido(livroSelecionado, quantidade));
            System.out.printf("%d unidade(s) de '%s' adicionada(s) ao carrinho!%n%n", 
                    quantidade, livroSelecionado.getNome());
            
            // Perguntar próxima ação
            int proximaAcao = perguntarProximaAcao(scanner);
            
            switch (proximaAcao) {
                case 1: // Continuar comprando
                    continue;
                case 2: // Encerrar pedido e ir para entrega
                    exibirResumoCompra(carrinho);
                    exibirTelaEntrega(scanner, carrinho);
                    carrinho.clear();
                    continueCompra = true; // Voltar ao menu principal
                    break;
                case 3: // Encerrar atendimento
                    exibirMensagemEncerramento();
                    continueCompra = false;
                    break;
                default:
                    System.out.println("Opcao invalida!\n");
            }
        }
    }
    
    private static void exibirCatalogo(ArrayList<Produto> livros) {
        System.out.println("\n" + "─".repeat(50));
        System.out.println("CATALOGO DE LIVROS");
        System.out.println("─".repeat(50));
        
        for (int i = 0; i < livros.size(); i++) {
            Produto livro = livros.get(i);
            System.out.printf("%d. %s%n", i + 1, livro.getNome());
            System.out.printf("   Preco: R$ %.2f%n", livro.getValor());
        }
        
        System.out.println("─".repeat(50));
    }
    
    private static int obterQuantidade(Scanner scanner) {
        System.out.print("\nQuantas unidades deseja (ou 0 para encerrar)? ");
        
        try {
            int quantidade = Integer.parseInt(scanner.nextLine());
            
            if (quantidade == 0) {
                System.out.println("\nEncerrando atendimento...");
                return -1; // Codigo especial para encerrar
            }
            
            if (quantidade < 0) {
                System.out.println("A quantidade nao pode ser negativa!");
                return 0;
            }
            
            return quantidade;
        } catch (NumberFormatException e) {
            System.out.println("Entrada invalida! Digite um numero inteiro.");
            return 0;
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
    
    private static void exibirResumoCompra(ArrayList<ItemPedido> carrinho) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("RESUMO DO PEDIDO");
        System.out.println("=".repeat(50));
        
        float valorTotal = 0;
        float pesoTotal = 0;
        
        for (int i = 0; i < carrinho.size(); i++) {
            ItemPedido item = carrinho.get(i);
            System.out.printf("%d. %s%n", i + 1, item);
            valorTotal += item.getSubtotal();
            pesoTotal += item.getPesoTotal();
        }
        
        System.out.println("─".repeat(50));
        System.out.printf("Valor Total: R$ %.2f%n", valorTotal);
        System.out.println("=".repeat(50) + "\n");
    }
    
    private static void exibirTelaEntrega(Scanner scanner, ArrayList<ItemPedido> carrinho) {
        System.out.println("╔" + "═".repeat(48) + "╗");
        System.out.println("║" + " ".repeat(48) + "║");
        System.out.println("║" + " ".repeat(15) + "DADOS DE ENTREGA" + " ".repeat(16) + "║");
        System.out.println("║" + " ".repeat(48) + "║");
        System.out.println("╚" + "═".repeat(48) + "╝");
        
        System.out.print("Seu nome completo: ");
        String nome = scanner.nextLine();
        
        System.out.print("CEP: ");
        String cep = scanner.nextLine();
        
        System.out.print("Rua: ");
        String rua = scanner.nextLine();
        
        System.out.print("Número: ");
        String numero = scanner.nextLine();
        
        System.out.print("Complemento (opcional): ");
        String complemento = scanner.nextLine();
        
        System.out.print("Cidade: ");
        String cidade = scanner.nextLine();
        
        System.out.print("Estado: ");
        String estado = scanner.nextLine();
        
        // Calcular valor e peso total
        float valorTotal = 0;
        float pesoTotal = 0;
        for (ItemPedido item : carrinho) {
            valorTotal += item.getSubtotal();
            pesoTotal += item.getPesoTotal();
        }
        
        // Determinar tipo de entrega baseado no peso
        String tipoEntrega;
        float prazo;
        float taxa = 0;
        
        if (pesoTotal <= 0.5f) {
            tipoEntrega = "Sedex - Econômico";
            prazo = 5;
            taxa = 15.90f;
        } else if (pesoTotal <= 2f) {
            tipoEntrega = "Sedex - Padrão";
            prazo = 3;
            taxa = 25.00f;
        } else {
            tipoEntrega = "Sedex - Express";
            prazo = 1;
            taxa = 50.00f;
        }
        
        System.out.println("\n" + "═".repeat(50));
        System.out.println("CONFIRMACAO DE COMPRA");
        System.out.println("═".repeat(50));
        System.out.printf("Cliente: %s%n", nome);
        System.out.printf("Endereco: %s, %s%s%n", rua, numero, 
                complemento.isEmpty() ? "" : " (" + complemento + ")");
        System.out.printf("CEP: %s - %s, %s%n", cep, cidade, estado);
        System.out.println("─".repeat(50));
        System.out.printf("Valor da Compra: R$ %.2f%n", valorTotal);
        System.out.printf("Tipo de Entrega: %s%n", tipoEntrega);
        System.out.printf("Taxa de Entrega: R$ %.2f%n", taxa);
        System.out.printf("Prazo: %d dia(s) uteis%n", (int)prazo);
        System.out.println("─".repeat(50));
        System.out.printf("VALOR TOTAL A PAGAR: R$ %.2f%n", valorTotal + taxa);
        System.out.println("═".repeat(50));
        System.out.println("\nPedido confirmado com sucesso!");
        System.out.println("Um e-mail de confirmacao foi enviado para voce.\n");
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