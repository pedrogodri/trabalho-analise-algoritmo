package org.example;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class Main {

    // Ajuste aqui se quiser aumentar/diminuir o tamanho do experimento.
    private static final int N = 100_000;

    private static final int TESTS_Q1 = 20;
    private static final int TESTS_Q1_CAPACITY = 10;
    private static final int TESTS_Q2 = 20;
    private static final int TESTS_Q3 = 20;
    private static final int TESTS_Q4 = 20;
    private static final int TESTS_Q5 = 20;

    private static final int RANDOM_ACCESSES_Q5 = 10_000;

    // Semente fixa para reduzir variacao e facilitar reproducao.
    private static final long SEED = 42L;

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);

        printHeader();
        runQuestion1();
        runQuestion2();
        runQuestion3();
        runQuestion4();
        runQuestion5();
    }

    private static void printHeader() {
        System.out.println("==============================================");
        System.out.println("Benchmark ArrayList vs LinkedList");
        System.out.println("N = " + N + " elementos");
        System.out.println("==============================================");
    }

    // ----------------------------------------------------
    // Questao 1
    // ----------------------------------------------------
    private static void runQuestion1() {
        System.out.println("\n=== Questao 1: Insercao no final ===");

        double avgArray = averageOf(TESTS_Q1, Main::timeFillEndArrayListDefault);
        double avgLinked = averageOf(TESTS_Q1, Main::timeFillEndLinkedList);

        printComparison("Insercao no final", avgArray, avgLinked);

        System.out.println("\n--- Q1.2: ArrayList com capacidades iniciais diferentes ---");
        int[] capacities = {10, 1_000, 100_000};

        double best = Double.MAX_VALUE;
        int bestCap = capacities[0];

        for (int cap : capacities) {
            double avg = averageOf(TESTS_Q1_CAPACITY, () -> timeFillEndArrayListWithCapacity(cap));
            System.out.printf("Capacidade inicial %d -> media: %.3f ms%n", cap, avg);

            if (avg < best) {
                best = avg;
                bestCap = cap;
            }
        }

        System.out.printf("Mais rapida: capacidade inicial %d (%.3f ms)%n", bestCap, best);
        System.out.println(
                "Motivo: capacidade inicial maior reduz realocacoes/copias internas durante crescimento do ArrayList.");
    }

    // ----------------------------------------------------
    // Questao 2
    // ----------------------------------------------------
    private static void runQuestion2() {
        System.out.println("\n=== Questao 2: Insercao em indice aleatorio valido ===");

        double avgArray = averageOf(TESTS_Q2, Main::timeInsertRandomPositionArrayList);
        double avgLinked = averageOf(TESTS_Q2, Main::timeInsertRandomPositionLinkedList);

        printComparison("Insercao aleatoria", avgArray, avgLinked);
    }

    // ----------------------------------------------------
    // Questao 3
    // ----------------------------------------------------
    private static void runQuestion3() {
        System.out.println("\n=== Questao 3: Remocao ate esvaziar ===");

        double avgRemoveFirstArray = averageOf(TESTS_Q3, Main::timeRemoveFirstArrayList);
        double avgRemoveFirstLinked = averageOf(TESTS_Q3, Main::timeRemoveFirstLinkedList);
        printComparison("Remover primeiro", avgRemoveFirstArray, avgRemoveFirstLinked);

        double avgRemoveLastArray = averageOf(TESTS_Q3, Main::timeRemoveLastArrayList);
        double avgRemoveLastLinked = averageOf(TESTS_Q3, Main::timeRemoveLastLinkedList);
        printComparison("Remover ultimo", avgRemoveLastArray, avgRemoveLastLinked);
    }

    // ----------------------------------------------------
    // Questao 4
    // ----------------------------------------------------
    private static void runQuestion4() {
        System.out.println("\n=== Questao 4: Remocao em indice aleatorio ate esvaziar ===");

        double avgArray = averageOf(TESTS_Q4, Main::timeRemoveRandomArrayList);
        double avgLinked = averageOf(TESTS_Q4, Main::timeRemoveRandomLinkedList);

        printComparison("Remocao aleatoria", avgArray, avgLinked);
    }

    // ----------------------------------------------------
    // Questao 5
    // ----------------------------------------------------
    private static void runQuestion5() {
        System.out.println("\n=== Questao 5: 10.000 acessos a indices aleatorios ===");

        double avgArray = averageOf(TESTS_Q5, Main::timeRandomAccessArrayList);
        double avgLinked = averageOf(TESTS_Q5, Main::timeRandomAccessLinkedList);

        printComparison("Acesso aleatorio (10k)", avgArray, avgLinked);

        System.out.println("Explicacao esperada:");
        System.out.println("- ArrayList: acesso por indice em O(1) (vetor contiguo).");
        System.out.println("- LinkedList: acesso por indice em O(n) (precisa percorrer nos).");
    }

    // ----------------------------------------------------
    // Utilitarios de metrica
    // ----------------------------------------------------
    @FunctionalInterface
    private interface MeasuredAction {
        long runNanos();
    }

    private static double averageOf(int tests, MeasuredAction action) {
        long total = 0L;
        for (int i = 0; i < tests; i++) {
            total += action.runNanos();
        }
        return total / (tests * 1_000_000.0);
    }

    private static void printComparison(String label, double arrayMs, double linkedMs) {
        System.out.printf("%n[%s]%n", label);
        System.out.printf("ArrayList : %.3f ms%n", arrayMs);
        System.out.printf("LinkedList: %.3f ms%n", linkedMs);

        if (arrayMs < linkedMs) {
            double factor = linkedMs / arrayMs;
            System.out.printf("Mais rapida: ArrayList (%.2fx)%n", factor);
        } else if (linkedMs < arrayMs) {
            double factor = arrayMs / linkedMs;
            System.out.printf("Mais rapida: LinkedList (%.2fx)%n", factor);
        } else {
            System.out.println("Empate tecnico.");
        }
    }

    // ----------------------------------------------------
    // Metodos de benchmark - Q1
    // ----------------------------------------------------
    private static long timeFillEndArrayListDefault() {
        List<Integer> list = new ArrayList<>();
        long start = System.nanoTime();
        for (int i = 0; i < N; i++) {
            list.add(i);
        }
        return System.nanoTime() - start;
    }

    private static long timeFillEndArrayListWithCapacity(int capacity) {
        List<Integer> list = new ArrayList<>(capacity);
        long start = System.nanoTime();
        for (int i = 0; i < N; i++) {
            list.add(i);
        }
        return System.nanoTime() - start;
    }

    private static long timeFillEndLinkedList() {
        List<Integer> list = new LinkedList<>();
        long start = System.nanoTime();
        for (int i = 0; i < N; i++) {
            list.add(i);
        }
        return System.nanoTime() - start;
    }

    // ----------------------------------------------------
    // Metodos de benchmark - Q2
    // ----------------------------------------------------
    private static long timeInsertRandomPositionArrayList() {
        List<Integer> list = new ArrayList<>();
        Random random = new Random(SEED);

        long start = System.nanoTime();
        for (int i = 0; i < N; i++) {
            int index = list.isEmpty() ? 0 : random.nextInt(list.size() + 1);
            list.add(index, i);
        }
        return System.nanoTime() - start;
    }

    private static long timeInsertRandomPositionLinkedList() {
        List<Integer> list = new LinkedList<>();
        Random random = new Random(SEED);

        long start = System.nanoTime();
        for (int i = 0; i < N; i++) {
            int index = list.isEmpty() ? 0 : random.nextInt(list.size() + 1);
            list.add(index, i);
        }
        return System.nanoTime() - start;
    }

    // ----------------------------------------------------
    // Metodos de benchmark - Q3
    // ----------------------------------------------------
    private static long timeRemoveFirstArrayList() {
        List<Integer> list = createArrayListFilled(N);
        long start = System.nanoTime();
        while (!list.isEmpty()) {
            list.remove(0);
        }
        return System.nanoTime() - start;
    }

    private static long timeRemoveFirstLinkedList() {
        List<Integer> list = createLinkedListFilled(N);
        long start = System.nanoTime();
        while (!list.isEmpty()) {
            list.remove(0);
        }
        return System.nanoTime() - start;
    }

    private static long timeRemoveLastArrayList() {
        List<Integer> list = createArrayListFilled(N);
        long start = System.nanoTime();
        while (!list.isEmpty()) {
            list.remove(list.size() - 1);
        }
        return System.nanoTime() - start;
    }

    private static long timeRemoveLastLinkedList() {
        List<Integer> list = createLinkedListFilled(N);
        long start = System.nanoTime();
        while (!list.isEmpty()) {
            list.remove(list.size() - 1);
        }
        return System.nanoTime() - start;
    }

    // ----------------------------------------------------
    // Metodos de benchmark - Q4
    // ----------------------------------------------------
    private static long timeRemoveRandomArrayList() {
        List<Integer> list = createArrayListFilled(N);
        Random random = new Random(SEED);

        long start = System.nanoTime();
        while (!list.isEmpty()) {
            int index = random.nextInt(list.size());
            list.remove(index);
        }
        return System.nanoTime() - start;
    }

    private static long timeRemoveRandomLinkedList() {
        List<Integer> list = createLinkedListFilled(N);
        Random random = new Random(SEED);

        long start = System.nanoTime();
        while (!list.isEmpty()) {
            int index = random.nextInt(list.size());
            list.remove(index);
        }
        return System.nanoTime() - start;
    }

    // ----------------------------------------------------
    // Metodos de benchmark - Q5
    // ----------------------------------------------------
    private static long timeRandomAccessArrayList() {
        List<Integer> list = createArrayListFilled(N);
        Random random = new Random(SEED);
        long sink = 0L;

        long start = System.nanoTime();
        for (int i = 0; i < RANDOM_ACCESSES_Q5; i++) {
            int index = random.nextInt(N);
            sink += list.get(index);
        }
        long elapsed = System.nanoTime() - start;

        avoidDeadCodeElimination(sink);
        return elapsed;
    }

    private static long timeRandomAccessLinkedList() {
        List<Integer> list = createLinkedListFilled(N);
        Random random = new Random(SEED);
        long sink = 0L;

        long start = System.nanoTime();
        for (int i = 0; i < RANDOM_ACCESSES_Q5; i++) {
            int index = random.nextInt(N);
            sink += list.get(index);
        }
        long elapsed = System.nanoTime() - start;

        avoidDeadCodeElimination(sink);
        return elapsed;
    }

    private static void avoidDeadCodeElimination(long value) {
        if (value == Long.MIN_VALUE) {
            System.out.println("impossivel");
        }
    }

    // ----------------------------------------------------
    // Fabrica de listas preenchidas
    // ----------------------------------------------------
    private static List<Integer> createArrayListFilled(int n) {
        List<Integer> list = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            list.add(i);
        }
        return list;
    }

    private static List<Integer> createLinkedListFilled(int n) {
        List<Integer> list = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            list.add(i);
        }
        return list;
    }
}
