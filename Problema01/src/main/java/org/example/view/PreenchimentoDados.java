package org.example.view;

import org.example.exceptions.DadoInvalidoException;
import org.example.model.vo.Cep;
import org.example.model.vo.Estado;
import org.example.model.vo.Quantidade;

import java.util.Scanner;

/**
 * Responsável por coletar e validar dados digitados pelo usuário.
 *
 * <p>Cada método entra em loop até receber uma entrada válida,
 * exibindo a mensagem de erro do domínio quando necessário.</p>
 */
public class PreenchimentoDados {

    private PreenchimentoDados() {}

    /**
     * Solicita uma quantidade ao usuário. Retorna {@code null} se o usuário
     * digitar 0 (sinalizando intenção de encerrar).
     */
    public static Quantidade solicitarQuantidade(Scanner scanner) {
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

    /** Solicita um campo de texto obrigatório, repetindo até receber valor não-vazio. */
    public static String solicitarCampoObrigatorio(Scanner scanner, String prompt, String nomeCampo) {
        while (true) {
            System.out.print(prompt);
            String valor = scanner.nextLine();
            if (valor != null && !valor.isBlank()) {
                return valor.trim();
            }
            System.out.println(nomeCampo + " não pode ser vazio");
        }
    }

    /** Solicita um CEP ao usuário, repetindo até receber um valor válido. */
    public static Cep solicitarCep(Scanner scanner) {
        while (true) {
            System.out.print("CEP: ");
            try {
                return new Cep(scanner.nextLine());
            } catch (DadoInvalidoException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /** Solicita a sigla de um estado ao usuário, repetindo até receber um valor válido. */
    public static Estado solicitarEstado(Scanner scanner) {
        while (true) {
            System.out.print("Estado (sigla, ex: SC): ");
            try {
                return new Estado(scanner.nextLine());
            } catch (DadoInvalidoException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}