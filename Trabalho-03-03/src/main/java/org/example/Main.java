import java.util.ArrayList;
import java.util.List;

public class Main {

    record Produto(String descricao, int peso, int valor) {
    }

    public static void main(String[] args) {
        questao1();
        System.out.println("\n---------------------------------\n");
        questao2();
    }

    private static void questao1() {

        List<Produto> produtos = List.of(
                new Produto("Violão", 1, 1500),
                new Produto("Rádio", 4, 3000),
                new Produto("Notebook", 2, 2000),
                new Produto("iPhone", 1, 2000),
                new Produto("MP3", 1, 1000)
        );

        System.out.println("QUESTÃO 1");
        calcularMelhorOpcao(produtos, 4);
    }

    private static void questao2() {

        List<Produto> produtos = List.of(
                new Produto("Água", 3, 10),
                new Produto("Livro", 1, 3),
                new Produto("Comida", 2, 9),
                new Produto("Casaco", 2, 5),
                new Produto("Câmera", 1, 6)
        );

        System.out.println("QUESTÃO 2");
        calcularMelhorOpcao(produtos, 6);
    }

    private static void calcularMelhorOpcao(List<Produto> produtos, int capacidadeMaxima) {

        List<Produto> itensEscolhidos = new ArrayList<>();

        int maiorValorEncontrado = 0;
        int pesoFinal = 0;

        int quantidadeCombinacoes = (int) Math.pow(2, produtos.size());

        for (int combinacao = 0; combinacao < quantidadeCombinacoes; combinacao++) {

            int pesoAtual = 0;
            int valorAtual = 0;

            List<Produto> itensDaCombinacao = new ArrayList<>();

            for (int posicao = 0; posicao < produtos.size(); posicao++) {

                if ((combinacao & (1 << posicao)) != 0) {

                    Produto produto = produtos.get(posicao);

                    pesoAtual += produto.peso();
                    valorAtual += produto.valor();

                    itensDaCombinacao.add(produto);
                }
            }

            if (pesoAtual <= capacidadeMaxima && valorAtual > maiorValorEncontrado) {
                maiorValorEncontrado = valorAtual;
                pesoFinal = pesoAtual;
                itensEscolhidos = itensDaCombinacao;
            }
        }

        System.out.println("Itens selecionados:");

        itensEscolhidos.forEach(item ->
                System.out.println(
                        item.descricao()
                                + " | Peso: " + item.peso()
                                + "kg | Valor: " + item.valor()
                )
        );

        System.out.println("\nPeso utilizado: " + pesoFinal + "kg");
        System.out.println("Valor obtido: " + maiorValorEncontrado);
    }
}