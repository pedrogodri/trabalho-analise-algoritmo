public static class PreenchimentoDados{

    // Retorna null quando o usuário quer encerrar (digita 0)
    // Loop até receber entrada válida — validação centralizada no VO Quantidade
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

    public static String solicitarCampoObrigatorio(Scanner scanner, String prompt, String nomeCampo) {
        while (true) {
            System.out.print(prompt);
            String valor = scanner.nextLine();
            if (valor != null && !valor.isBlank()) {
                return valor;
            }
            System.out.println(nomeCampo + " não pode ser vazio");
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
}