package org.example;

public class Main {
    public static void main(String[] args) {
        questao1();
        questao2();
        questao3();
        questao4();
    }

    static void questao1() {
        int[] vetorOrdenado = new int[]{-10, -3, 0, 1, 2, 3, 5, 8, 9, 10};
        int numero = 3;
        int idx = binarySearch(vetorOrdenado, numero, 0, vetorOrdenado.length - 1);
        if (idx >= 0) {
            System.out.printf("Questao 1: O valor %d existe no vetor e seu indice e %d%n", numero, idx);
        } else {
            System.out.printf("Questao 1: O valor %d nao existe no vetor%n", numero);
        }
    }

    static void questao2() {
        int[] vetorFixedPoint = new int[]{-1, -1, 1, 3, 8, 9};
        int fixed = findFixedPoint(vetorFixedPoint, 0, vetorFixedPoint.length - 1);
        if (fixed >= 0) {
            System.out.printf("Questao 2: Fixed point encontrado: A[%d] = %d%n", fixed, vetorFixedPoint[fixed]);
        } else {
            System.out.println("Questao 2: Nenhum fixed point encontrado");
        }
    }

    static void questao3() {
        String original = "FURB";
        String invertida = reverseString(original);
        System.out.printf("Questao 3: String original: %s | Invertida: %s%n", original, invertida);
    }

    static void questao4() {
        int[] vetorMaioria = new int[]{1, 2, 1, 1, 3};
        int maioria = findMajority(vetorMaioria, 0, vetorMaioria.length - 1);
        if (maioria != -1) {
            System.out.printf("Questao 4: Elemento majoritario: %d%n", maioria);
        } else {
            System.out.println("Questao 4: Nenhum elemento majoritario encontrado");
        }
    }

    // -------------------------------------------------------------------------
    // Questão 1: Busca Binária recursiva (divisão e conquista)
    // Retorna o índice do target ou -1 se não encontrado.
    // -------------------------------------------------------------------------
    public static int binarySearch(int[] arr, int target, int left, int right) {
        if (left > right) return -1;                        // caso base: não encontrado
        int mid = left + (right - left) / 2;
        if (arr[mid] == target) return mid;                 // caso base: encontrado
        if (arr[mid] < target)
            return binarySearch(arr, target, mid + 1, right); // conquista: metade direita
        else
            return binarySearch(arr, target, left, mid - 1);  // conquista: metade esquerda
    }

    // -------------------------------------------------------------------------
    // Questão 2: Fixed Point recursivo (divisão e conquista)
    // Encontra i tal que arr[i] == i em um vetor ordenado.
    // -------------------------------------------------------------------------
    public static int findFixedPoint(int[] arr, int left, int right) {
        if (left > right) return -1;                        // caso base: não encontrado
        int mid = left + (right - left) / 2;
        if (arr[mid] == mid) return mid;                    // caso base: fixed point encontrado
        if (arr[mid] < mid)
            return findFixedPoint(arr, mid + 1, right);     // conquista: metade direita
        else
            return findFixedPoint(arr, left, mid - 1);      // conquista: metade esquerda
    }

    // -------------------------------------------------------------------------
    // Questão 3: Inversão de String (divisão e conquista)
    // Divide a string ao meio, inverte cada metade recursivamente e as concatena
    // em ordem trocada: reverse(direita) + reverse(esquerda).
    // -------------------------------------------------------------------------
    public static String reverseString(String s) {
        if (s == null || s.length() <= 1) return s;        // caso base
        int mid = s.length() / 2;
        String esquerda = s.substring(0, mid);
        String direita  = s.substring(mid);
        // conquista: inverte cada metade e concatena em ordem invertida
        return reverseString(direita) + reverseString(esquerda);
    }

    // -------------------------------------------------------------------------
    // Questão 4: Elemento Majoritário (divisão e conquista)
    // Divide o vetor ao meio, encontra o candidato majoritário de cada metade e
    // verifica qual deles (se algum) é majoritário no vetor completo.
    // Restrição: só é permitido comparar igualdade entre elementos.
    // -------------------------------------------------------------------------
    public static int findMajority(int[] arr, int left, int right) {
        if (left == right) return arr[left];               // caso base: único elemento

        int mid = left + (right - left) / 2;
        int leftMajority  = findMajority(arr, left, mid);       // candidato da metade esquerda
        int rightMajority = findMajority(arr, mid + 1, right);  // candidato da metade direita

        // Se ambos os lados retornam o mesmo candidato, ele é o majoritário deste intervalo
        if (leftMajority == rightMajority) return leftMajority;

        // Conta ocorrências de cada candidato no intervalo completo (usando apenas ==)
        int leftCount  = countOccurrences(arr, left, right, leftMajority);
        int rightCount = countOccurrences(arr, left, right, rightMajority);

        int total = right - left + 1;
        if (leftCount  > total / 2) return leftMajority;
        if (rightCount > total / 2) return rightMajority;
        return -1; // nenhum majoritário neste intervalo
    }

    // Conta ocorrências de value no subvetor arr[left..right] usando apenas igualdade
    private static int countOccurrences(int[] arr, int left, int right, int value) {
        int count = 0;
        for (int i = left; i <= right; i++) {
            if (arr[i] == value) count++;
        }
        return count;
    }
}
