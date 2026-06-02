import java.util.ArrayList;
import java.util.List;

public class Questao1 {

    record Item(String nome, int peso, int valor) {}

    public static void main(String[] args) {

        List<Item> itens = List.of(
                new Item("Violão", 1, 1500),
                new Item("Rádio", 4, 3000),
                new Item("Notebook", 2, 2000),
                new Item("iPhone", 1, 2000),
                new Item("MP3", 1, 1000)
        );

        int capacidade = 4;

        int melhorValor = 0;
        List<Item> melhorCombinacao = new ArrayList<>();

        int totalCombinacoes = 1 << itens.size();

        for (int mascara = 0; mascara < totalCombinacoes; mascara++) {

            int peso = 0;
            int valor = 0;
            List<Item> combinacao = new ArrayList<>();

            for (int i = 0; i < itens.size(); i++) {
                if ((mascara & (1 << i)) != 0) {
                    Item item = itens.get(i);
                    peso += item.peso();
                    valor += item.valor();
                    combinacao.add(item);
                }
            }

            if (peso <= capacidade && valor > melhorValor) {
                melhorValor = valor;
                melhorCombinacao = combinacao;
            }
        }

        System.out.println("Melhor combinação:");
        melhorCombinacao.forEach(item ->
                System.out.println("- " + item.nome())
        );

        System.out.println("Valor total: R$" + melhorValor);
    }
}