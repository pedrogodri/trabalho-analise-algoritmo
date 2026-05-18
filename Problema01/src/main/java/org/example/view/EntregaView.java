package org.example.view;

import org.example.exceptions.EntregaNaoDisponivelException;
import org.example.implementation.entrega.PacEntrega;
import org.example.implementation.entrega.RetiradaLocalEntrega;
import org.example.implementation.entrega.SedexEntrega;
import org.example.interfaces.IFormatoEntrega;
import org.example.model.EnderecoEntrega;
import org.example.model.Pedido;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Responsável por coletar dados de entrega e exibir informações relacionadas ao frete.
 *
 * <p>Separa a responsabilidade de interação com o usuário da lógica de negócio,
 * seguindo o princípio da responsabilidade única (SRP).</p>
 */
public class EntregaView {

    private static final List<IFormatoEntrega> MODALIDADES = List.of(
            new PacEntrega(),
            new SedexEntrega(),
            new RetiradaLocalEntrega()
    );

    private EntregaView() {}

    /**
     * Exibe o cabeçalho da tela de entrega.
     */
    public static void exibirCabecalho() {
        System.out.println("+" + "-".repeat(48) + "+");
        System.out.printf("|%-48s|%n", "");
        System.out.printf("|%16s%-32s|%n", "", "DADOS DE ENTREGA");
        System.out.printf("|%-48s|%n", "");
        System.out.println("+" + "-".repeat(48) + "+");
    }

    /**
     * Coleta o endereço de entrega do usuário via Scanner.
     *
     * @param scanner leitor de entrada do usuário
     * @return {@link EnderecoEntrega} com os dados informados
     */
    public static EnderecoEntrega coletarEndereco(Scanner scanner) {
        PreenchimentoDados.solicitarCampoObrigatorio(scanner, "Seu nome completo: ", "Nome do cliente");
        var cep = PreenchimentoDados.solicitarCep(scanner);
        String rua = PreenchimentoDados.solicitarCampoObrigatorio(scanner, "Rua: ", "Logradouro");
        String numero = PreenchimentoDados.solicitarCampoObrigatorio(scanner, "Numero: ", "Número do endereço");
        System.out.print("Complemento (opcional): ");
        String complemento = scanner.nextLine();
        String cidade = PreenchimentoDados.solicitarCampoObrigatorio(scanner, "Cidade: ", "Cidade");
        var estado = PreenchimentoDados.solicitarEstado(scanner);
        return new EnderecoEntrega(cep, rua, numero, complemento, cidade, estado);
    }

    /**
     * Lista as modalidades de entrega disponíveis para o peso do pedido
     * e solicita a escolha do usuário.
     *
     * @param scanner leitor de entrada do usuário
     * @param pedido  pedido cujo peso define a disponibilidade de cada modalidade
     * @return modalidade de entrega escolhida pelo usuário
     */
    public static IFormatoEntrega selecionarModalidade(Scanner scanner, Pedido pedido) {
        System.out.println("\n" + "─".repeat(50));
        System.out.println("MODALIDADES DE ENTREGA");
        System.out.println("─".repeat(50));

        List<IFormatoEntrega> disponiveis = new ArrayList<>();
        int numeracao = 1;

        for (IFormatoEntrega modalidade : MODALIDADES) {
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
            try {
                int opcao = Integer.parseInt(scanner.nextLine());
                if (opcao >= 1 && opcao <= disponiveis.size()) {
                    return disponiveis.get(opcao - 1);
                }
            } catch (NumberFormatException ignored) {}
            System.out.print("Opção inválida! Tente novamente: ");
        }
    }

    /**
     * Exibe a confirmação final da compra com endereço, frete e total.
     *
     * @param endereco      endereço de entrega
     * @param pedido        pedido com os itens e valores
     * @param modalidade    modalidade de entrega escolhida
     * @param frete         custo do frete calculado
     */
    public static void exibirConfirmacao(EnderecoEntrega endereco, Pedido pedido,
                                         IFormatoEntrega modalidade, double frete) {
        System.out.println("\n" + "═".repeat(50));
        System.out.println("CONFIRMAÇÃO DE COMPRA");
        System.out.println("═".repeat(50));
        System.out.printf("Endereço: %s%n", endereco.formatarLogradouro());
        System.out.printf("CEP:      %s%n", endereco.concatenarCepCidadeEstado());
        System.out.println("─".repeat(50));
        System.out.printf("Valor da Compra:  R$ %.2f%n", pedido.valorTotalProdutos());
        System.out.printf("Tipo de Entrega:  %s%n", modalidade.descricao());
        System.out.printf("Taxa de Entrega:  R$ %.2f%n", frete);
        System.out.println("─".repeat(50));
        System.out.printf("VALOR TOTAL:      R$ %.2f%n", pedido.valorTotalProdutos() + frete);
        System.out.println("═".repeat(50));
        System.out.println("\nPedido confirmado com sucesso!");
        System.out.println("Um e-mail de confirmação foi enviado para você.\n");
    }
}
