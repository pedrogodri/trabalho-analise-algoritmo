package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
//        Questão 2: Dado um vetor ordenado A encontre um valor i onde A[i]=i, ou seja, encontre um
//        valor no vetor A que seu elemento tenha o mesmo valor que o seu índice. Exemplo de entrada
//                [-1,-1,1,3,8,9], valor esperado
        var vetorOrdenado = new int[]{0, 1, 2, 5, 3, 4, 8, 5, 6, 7, 8, 9, 10};
        var numero = 3;

        for (int i = 0; i < vetorOrdenado.length; i++) {
            if(i == vetorOrdenado[i]) {
                IO.println(String.format("O valor %d existe no vetor e seu indice é %d", vetorOrdenado[i], i));
            }
        }

//        Questão 1: Dado um vetor ordenado A você precisa verificar se um determinado valor existe e
//        qual é o indice dele no vetor. Utilize um algoritmo de divisão e conquista para solucionar este
//        problema

        for (int i = 0; i < vetorOrdenado.length; i++) {
            var meio = vetorOrdenado.length / 2;
            var inicio = 0;
            var fim = 0;
            if(vetorOrdenado[meio] == numero) {
                IO.println(String.format("O valor %d existe no vetor e seu indice é %d", vetorOrdenado[meio], meio));
            }
            else if(vetorOrdenado[meio] > numero) {
                fim = meio;
                meio =  fim / 2;
            }else{
                fim = vetorOrdenado.length - 1;
                inicio = meio;
                meio = (fim - inicio) / 2 + inicio;
                if(vetorOrdenado[meio] == numero) {
                    IO.println(String.format("O valor %d existe no vetor e seu indice é %d", vetorOrdenado[meio], meio));
                }
            }
        }
    }
}
