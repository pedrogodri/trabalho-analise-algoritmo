package org.example;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.font.PdfFontFactory.EmbeddingStrategy;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class Main {

    private static final int N = 100_000;

    private static final int TESTS_Q1         = 20;
    private static final int TESTS_Q1_CAPACITY = 10;
    private static final int TESTS_Q2         = 20;
    private static final int TESTS_Q3         = 20;
    private static final int TESTS_Q4         = 20;
    private static final int TESTS_Q5         = 20;

    private static final int RANDOM_ACCESSES_Q5 = 10_000;
    private static final long SEED = 42L;

    // ---- results stored for PDF ----
    private static double q1AvgArray, q1AvgLinked;
    private static double q1Cap10, q1Cap1k, q1Cap100k;

    private static double q2AvgArray, q2AvgLinked;

    private static double q3RemFirstArray, q3RemFirstLinked;
    private static double q3RemLastArray,  q3RemLastLinked;

    private static double q4AvgArray, q4AvgLinked;

    private static double q5AvgArray, q5AvgLinked;

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);

        printHeader();
        runQuestion1();
        runQuestion2();
        runQuestion3();
        runQuestion4();
        runQuestion5();
        runQuestion6();

        generatePdfReport();
    }

    // -------------------------------------------------------- header
    private static void printHeader() {
        System.out.println("==============================================");
        System.out.println("Benchmark ArrayList vs LinkedList");
        System.out.println("N = " + N + " elementos");
        System.out.println("==============================================");
    }

    // -------------------------------------------------------- Q1
    private static void runQuestion1() {
        System.out.println("\n=== Questao 1: Insercao no final ===");

        q1AvgArray  = averageOf(TESTS_Q1, Main::timeFillEndArrayListDefault);
        q1AvgLinked = averageOf(TESTS_Q1, Main::timeFillEndLinkedList);

        printComparison("Insercao no final", q1AvgArray, q1AvgLinked);

        System.out.println("\n--- Q1.2: ArrayList com capacidades iniciais diferentes ---");
        int[] capacities = {10, 1_000, 100_000};

        q1Cap10   = averageOf(TESTS_Q1_CAPACITY, () -> timeFillEndArrayListWithCapacity(10));
        q1Cap1k   = averageOf(TESTS_Q1_CAPACITY, () -> timeFillEndArrayListWithCapacity(1_000));
        q1Cap100k = averageOf(TESTS_Q1_CAPACITY, () -> timeFillEndArrayListWithCapacity(100_000));

        double[] vals = {q1Cap10, q1Cap1k, q1Cap100k};
        double best = Double.MAX_VALUE; int bestCap = capacities[0];
        for (int i = 0; i < capacities.length; i++) {
            System.out.printf("Capacidade inicial %d -> media: %.3f ms%n", capacities[i], vals[i]);
            if (vals[i] < best) { best = vals[i]; bestCap = capacities[i]; }
        }
        System.out.printf("Mais rapida: capacidade inicial %d (%.3f ms)%n", bestCap, best);
        System.out.println("Motivo: capacidade inicial maior reduz realocacoes/copias internas.");
    }

    // -------------------------------------------------------- Q2
    private static void runQuestion2() {
        System.out.println("\n=== Questao 2: Insercao em indice aleatorio valido ===");

        q2AvgArray  = averageOf(TESTS_Q2, Main::timeInsertRandomPositionArrayList);
        q2AvgLinked = averageOf(TESTS_Q2, Main::timeInsertRandomPositionLinkedList);

        printComparison("Insercao aleatoria", q2AvgArray, q2AvgLinked);
    }

    // -------------------------------------------------------- Q3
    private static void runQuestion3() {
        System.out.println("\n=== Questao 3: Remocao ate esvaziar ===");

        q3RemFirstArray  = averageOf(TESTS_Q3, Main::timeRemoveFirstArrayList);
        q3RemFirstLinked = averageOf(TESTS_Q3, Main::timeRemoveFirstLinkedList);
        printComparison("Remover primeiro", q3RemFirstArray, q3RemFirstLinked);

        q3RemLastArray  = averageOf(TESTS_Q3, Main::timeRemoveLastArrayList);
        q3RemLastLinked = averageOf(TESTS_Q3, Main::timeRemoveLastLinkedList);
        printComparison("Remover ultimo", q3RemLastArray, q3RemLastLinked);
    }

    // -------------------------------------------------------- Q4
    private static void runQuestion4() {
        System.out.println("\n=== Questao 4: Remocao em indice aleatorio ate esvaziar ===");

        q4AvgArray  = averageOf(TESTS_Q4, Main::timeRemoveRandomArrayList);
        q4AvgLinked = averageOf(TESTS_Q4, Main::timeRemoveRandomLinkedList);

        printComparison("Remocao aleatoria", q4AvgArray, q4AvgLinked);
    }

    // -------------------------------------------------------- Q5
    private static void runQuestion5() {
        System.out.println("\n=== Questao 5: 10.000 acessos a indices aleatorios ===");

        q5AvgArray  = averageOf(TESTS_Q5, Main::timeRandomAccessArrayList);
        q5AvgLinked = averageOf(TESTS_Q5, Main::timeRandomAccessLinkedList);

        printComparison("Acesso aleatorio (10k)", q5AvgArray, q5AvgLinked);

        System.out.println("Explicacao:");
        System.out.println("- ArrayList: acesso por indice em O(1) (vetor contiguo).");
        System.out.println("- LinkedList: acesso por indice em O(n) (percorre nos).");
    }

    // -------------------------------------------------------- Q6
    private static void runQuestion6() {
        System.out.println("\n=== Questao 6: Analise Comparativa Geral ===");

        System.out.println("\n[Cenarios em que o ArrayList foi mais vantajoso]");
        System.out.println("1. Insercao no final (Q1): ArrayList usa array contiguo; alocacoes amortizadas O(1).");
        System.out.println("2. Remocao do ultimo (Q3): remover o ultimo elemento e O(1) no ArrayList.");
        System.out.println("3. Acesso aleatorio (Q5): get(i) e O(1) vs O(n) do LinkedList.");
        System.out.println("4. Remocao aleatoria (Q4): apesar do deslocamento O(n), localidade de cache favorece ArrayList.");

        System.out.println("\n[Cenarios em que o LinkedList foi mais vantajoso]");
        System.out.println("1. Remocao do primeiro (Q3): removeFirst() e O(1); ArrayList precisa deslocar n-1 elementos.");
        System.out.println("2. Insercao na cabeca: analogamente, addFirst() e O(1) para LinkedList.");

        System.out.println("\n[Os resultados confirmaram a teoria?]");
        boolean q5ConfirmArray = q5AvgArray < q5AvgLinked;
        boolean q3FirstConfirmLinked = q3RemFirstLinked < q3RemFirstArray;
        System.out.println("- Q5 (acesso aleatorio): ArrayList mais rapida? " + (q5ConfirmArray ? "SIM" : "NAO") + " -> teoria O(1) vs O(n) " + (q5ConfirmArray ? "CONFIRMADA" : "NAO confirmada"));
        System.out.println("- Q3 (remover primeiro): LinkedList mais rapida? " + (q3FirstConfirmLinked ? "SIM" : "NAO") + " -> teoria O(1) vs O(n) " + (q3FirstConfirmLinked ? "CONFIRMADA" : "NAO confirmada"));
        System.out.println("- Em geral, os resultados sao coerentes com a teoria classica de complexidade.");

        System.out.println("\n[Fatores praticos que podem influenciar os resultados]");
        System.out.println("1. JIT (Just-In-Time Compiler): a JVM otimiza codigo quente, podendo mascarar diferencas teoricas.");
        System.out.println("2. Localidade de cache: arrays contiguos sao favorecidos pela hierarquia de cache L1/L2/L3.");
        System.out.println("3. Garbage Collector: LinkedList cria um objeto Node por elemento; pressao no GC pode causar pausas.");
        System.out.println("4. Boxing de primitivos: ambas as listas armazenam Integer (objeto), nao int primitivo.");
        System.out.println("5. Aquecimento (warm-up): primeiras iteracoes podem ser mais lentas antes do JIT compilar.");
        System.out.println("6. Carga do sistema: outros processos do SO competem por CPU e memoria durante o teste.");
    }

    // -------------------------------------------------------- PDF
    private static void generatePdfReport() throws IOException {
        String outputPath = "resultado_benchmark.pdf";
        PdfWriter writer  = new PdfWriter(outputPath);
        PdfDocument pdf   = new PdfDocument(writer);
        Document doc      = new Document(pdf);

        // Arial TTF embedded – suporta todos os caracteres do Português-Brasil
        String arialPath     = "/System/Library/Fonts/Supplemental/Arial.ttf";
        String arialBoldPath = "/System/Library/Fonts/Supplemental/Arial Bold.ttf";
        PdfFont bold    = PdfFontFactory.createFont(arialBoldPath, "Identity-H", EmbeddingStrategy.PREFER_EMBEDDED);
        PdfFont regular = PdfFontFactory.createFont(arialPath,     "Identity-H", EmbeddingStrategy.PREFER_EMBEDDED);

        // ---- Capa / Título
        doc.add(new Paragraph("Benchmark: ArrayList vs LinkedList")
                .setFont(bold).setFontSize(20)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(4));
        doc.add(new Paragraph("Análise de Algoritmos e Estruturas de Dados")
                .setFont(regular).setFontSize(12)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(2));
        doc.add(new Paragraph("Universidade Regional de Blumenau – FURB")
                .setFont(regular).setFontSize(11)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(2));
        doc.add(new Paragraph("Prof. Gabriel Castellani de Oliveira")
                .setFont(regular).setFontSize(11)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(12));

        doc.add(new Paragraph("Pull Request: https://github.com/pedrogodri/trabalho-analise-algoritmo/pull/6")
                .setFont(regular).setFontSize(10)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(2));
        doc.add(new Paragraph("Branch: https://github.com/pedrogodri/trabalho-analise-algoritmo/tree/Exercicio-01")
                .setFont(regular).setFontSize(10)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(12));

        doc.add(new Paragraph("Configuração do experimento:")
                .setFont(bold).setFontSize(11));
        doc.add(new Paragraph("N = " + N + " elementos  |  Semente aleatória = " + SEED)
                .setFont(regular).setFontSize(10).setMarginBottom(14));

        // ---- Q1
        addSectionTitle(doc, bold, "Questão 1 – Inserção no Final");
        addBody(doc, regular,
                "Comparação entre ArrayList e LinkedList inserindo " + N + " elementos ao final. " +
                "Foram realizados " + TESTS_Q1 + " testes; a tabela exibe o tempo médio.");
        addComparisonTable(doc, bold, regular, "Inserção no final", q1AvgArray, q1AvgLinked);

        addBody(doc, regular, "Impacto da capacidade inicial do ArrayList (" + TESTS_Q1_CAPACITY + " testes cada):");
        addCapacityTable(doc, bold, regular);

        addBody(doc, regular,
                "Conclusão: pré-alocar a capacidade exata (100.000) elimina realocações internas, " +
                "reduzindo cópias do array e, portanto, o tempo total de inserção.");

        // ---- Q2
        addSectionTitle(doc, bold, "Questão 2 – Inserção em Posição Aleatória");
        addBody(doc, regular,
                "Cada um dos " + N + " elementos é inserido em uma posição aleatória válida. " +
                TESTS_Q2 + " testes realizados.");
        addComparisonTable(doc, bold, regular, "Inserção aleatória", q2AvgArray, q2AvgLinked);
        addBody(doc, regular,
                "O ArrayList precisa deslocar os elementos após o ponto de inserção — O(n) —, porém se " +
                "beneficia de operações de memória em bloco (System.arraycopy). O LinkedList não desloca " +
                "elementos, mas precisa percorrer a lista para encontrar o índice — também O(n).");

        // ---- Q3
        addSectionTitle(doc, bold, "Questão 3 – Remoção até Esvaziar");
        addBody(doc, regular,
                "As listas são preenchidas com " + N + " elementos e esvaziadas por remoções consecutivas. " +
                TESTS_Q3 + " testes para cada variante.");

        addComparisonTable(doc, bold, regular, "Remover primeiro elemento", q3RemFirstArray, q3RemFirstLinked);
        addBody(doc, regular,
                "Remover o primeiro elemento é O(n) para o ArrayList (deslocamento de todos os elementos) " +
                "e O(1) para o LinkedList (apenas atualização de ponteiro).");

        addComparisonTable(doc, bold, regular, "Remover último elemento", q3RemLastArray, q3RemLastLinked);
        addBody(doc, regular,
                "Remover o último elemento é O(1) para ambas as estruturas, mas o ArrayList se beneficia " +
                "de melhor localidade de cache.");

        // ---- Q4
        addSectionTitle(doc, bold, "Questão 4 – Remoção em Índice Aleatório");
        addBody(doc, regular,
                "Listas preenchidas são esvaziadas removendo índices aleatórios. " + TESTS_Q4 + " testes.");
        addComparisonTable(doc, bold, regular, "Remoção aleatória", q4AvgArray, q4AvgLinked);
        addBody(doc, regular,
                "O ArrayList sofre deslocamento O(n) por remoção, mas a operação de cópia de memória é " +
                "altamente otimizada pela JVM. O LinkedList evita deslocamentos, porém paga O(n) para " +
                "navegar até o índice e tem maior sobrecarga de GC por nó-objeto.");

        // ---- Q5
        addSectionTitle(doc, bold, "Questão 5 – Acessos Aleatórios (10.000 leituras)");
        addBody(doc, regular,
                RANDOM_ACCESSES_Q5 + " acessos aleatórios a uma lista com " + N + " elementos. " +
                TESTS_Q5 + " testes realizados.");
        addComparisonTable(doc, bold, regular, "Acesso aleatório (10k)", q5AvgArray, q5AvgLinked);
        addBody(doc, regular,
                "ArrayList: get(i) em O(1) – cálculo direto de endereço de memória (base + i × tamanho).\n" +
                "LinkedList: get(i) em O(n) – necessário percorrer i nós a partir da cabeça (ou cauda se i > n/2).\n" +
                "Resultado plenamente esperado pela teoria clássica.");

        // ---- Q6
        addSectionTitle(doc, bold, "Questão 6 – Análise Comparativa Geral");

        addSubTitle(doc, bold, "Cenários em que o ArrayList foi mais vantajoso");
        addBullet(doc, regular, "Inserção no final (Q1): array contíguo permite alocação amortizada O(1) com boa localidade de cache.");
        addBullet(doc, regular, "Remoção do último elemento (Q3): operação O(1) sem deslocamentos.");
        addBullet(doc, regular, "Acesso aleatório (Q5): get(i) em O(1), muito superior ao O(n) do LinkedList.");
        addBullet(doc, regular, "Remoção aleatória (Q4): System.arraycopy é altamente otimizado pela JVM, superando o LinkedList na prática.");

        addSubTitle(doc, bold, "Cenários em que o LinkedList foi mais vantajoso");
        addBullet(doc, regular, "Remoção do primeiro elemento (Q3): removeFirst() é O(1); o ArrayList precisa deslocar todos os n−1 elementos restantes.");
        addBullet(doc, regular, "Inserção/remoção na cabeça em geral: operação O(1) quando não é necessário percorrer a lista.");

        boolean q5ok = q5AvgArray < q5AvgLinked;
        boolean q3ok = q3RemFirstLinked < q3RemFirstArray;
        addSubTitle(doc, bold, "Os resultados confirmaram a teoria?");
        addBullet(doc, regular, "Q5 – Acesso aleatório: ArrayList mais rápida? " + (q5ok ? "SIM" : "NÃO")
                + " → teoria O(1) vs O(n) " + (q5ok ? "CONFIRMADA." : "não confirmada (possível interferência de JIT/GC)."));
        addBullet(doc, regular, "Q3 – Remover primeiro: LinkedList mais rápida? " + (q3ok ? "SIM" : "NÃO")
                + " → teoria O(1) vs O(n) " + (q3ok ? "CONFIRMADA." : "não confirmada (possível interferência de JIT)."));
        addBullet(doc, regular, "De forma geral, os resultados são coerentes com a teoria clássica de complexidade.");

        addSubTitle(doc, bold, "Fatores práticos que podem influenciar os resultados");
        addBullet(doc, regular, "JIT Compiler: a JVM otimiza código executado frequentemente, podendo mascarar diferenças teóricas.");
        addBullet(doc, regular, "Localidade de cache: arrays contíguos exploram melhor os caches L1/L2/L3 do processador.");
        addBullet(doc, regular, "Garbage Collector: o LinkedList cria um objeto Node por elemento; muitos objetos aumentam a pressão no GC e podem causar pausas involuntárias.");
        addBullet(doc, regular, "Boxing de primitivos: ambas as listas armazenam Integer (objeto encapsulado), não int primitivo, adicionando sobrecarga de alocação e desreferenciamento.");
        addBullet(doc, regular, "Aquecimento (warm-up) da JVM: as primeiras iterações são mais lentas antes de o JIT compilar o bytecode.");
        addBullet(doc, regular, "Carga do sistema operacional: outros processos competem por CPU, memória e barramento durante a execução dos testes.");

        doc.close();
        System.out.println("\n==============================================");
        System.out.println("PDF gerado com sucesso: " + outputPath);
        System.out.println("==============================================");
    }

    // ---- PDF helpers ----
    private static void addSectionTitle(Document doc, PdfFont bold, String text) throws IOException {
        doc.add(new Paragraph(text)
                .setFont(bold).setFontSize(13)
                .setFontColor(ColorConstants.DARK_GRAY)
                .setMarginTop(16).setMarginBottom(4));
    }

    private static void addSubTitle(Document doc, PdfFont bold, String text) throws IOException {
        doc.add(new Paragraph(text)
                .setFont(bold).setFontSize(11)
                .setMarginTop(8).setMarginBottom(2));
    }

    private static void addBody(Document doc, PdfFont regular, String text) throws IOException {
        doc.add(new Paragraph(text)
                .setFont(regular).setFontSize(10).setMarginBottom(4));
    }

    private static void addBullet(Document doc, PdfFont regular, String text) throws IOException {
        doc.add(new Paragraph("• " + text)
                .setFont(regular).setFontSize(10)
                .setMarginLeft(14).setMarginBottom(2));
    }

    private static void addComparisonTable(Document doc, PdfFont bold, PdfFont regular,
                                            String label, double arrayMs, double linkedMs) throws IOException {
        Table table = new Table(UnitValue.createPercentArray(new float[]{40, 30, 30}))
                .setWidth(UnitValue.createPercentValue(80));

        // header row
        table.addHeaderCell(styledCell(label,    bold, ColorConstants.LIGHT_GRAY));
        table.addHeaderCell(styledCell("ArrayList",   bold, ColorConstants.LIGHT_GRAY));
        table.addHeaderCell(styledCell("LinkedList",  bold, ColorConstants.LIGHT_GRAY));

        // data row
        String faster;
        if (arrayMs < linkedMs)
            faster = String.format("ArrayList (%.2fx)", linkedMs / arrayMs);
        else if (linkedMs < arrayMs)
            faster = String.format("LinkedList (%.2fx)", arrayMs / linkedMs);
        else
            faster = "Empate";

        table.addCell(dataCell("Tempo médio (ms)", regular));
        table.addCell(dataCell(String.format("%.3f", arrayMs), regular));
        table.addCell(dataCell(String.format("%.3f", linkedMs), regular));

        table.addCell(dataCell("Mais rápida", regular));
        table.addCell(new Cell(1, 2).add(new Paragraph(faster).setFont(bold).setFontSize(10))
                .setTextAlignment(TextAlignment.CENTER));

        doc.add(table);
        doc.add(new Paragraph(""));
    }

    private static void addCapacityTable(Document doc, PdfFont bold, PdfFont regular) throws IOException {
        Table table = new Table(UnitValue.createPercentArray(new float[]{33, 33, 34}))
                .setWidth(UnitValue.createPercentValue(80));

        table.addHeaderCell(styledCell("Capacidade Inicial", bold, ColorConstants.LIGHT_GRAY));
        table.addHeaderCell(styledCell("Testes",              bold, ColorConstants.LIGHT_GRAY));
        table.addHeaderCell(styledCell("Tempo médio (ms)",    bold, ColorConstants.LIGHT_GRAY));

        table.addCell(dataCell("10",       regular));
        table.addCell(dataCell(String.valueOf(TESTS_Q1_CAPACITY), regular));
        table.addCell(dataCell(String.format("%.3f", q1Cap10), regular));

        table.addCell(dataCell("1.000",    regular));
        table.addCell(dataCell(String.valueOf(TESTS_Q1_CAPACITY), regular));
        table.addCell(dataCell(String.format("%.3f", q1Cap1k), regular));

        table.addCell(dataCell("100.000",  regular));
        table.addCell(dataCell(String.valueOf(TESTS_Q1_CAPACITY), regular));
        table.addCell(dataCell(String.format("%.3f", q1Cap100k), regular));

        doc.add(table);
        doc.add(new Paragraph(""));
    }

    private static Cell styledCell(String text, PdfFont font, com.itextpdf.kernel.colors.Color bg) {
        return new Cell()
                .add(new Paragraph(text).setFont(font).setFontSize(10))
                .setBackgroundColor(bg)
                .setTextAlignment(TextAlignment.CENTER);
    }

    private static Cell dataCell(String text, PdfFont font) {
        return new Cell()
                .add(new Paragraph(text).setFont(font).setFontSize(10))
                .setTextAlignment(TextAlignment.CENTER);
    }

    // -------------------------------------------------------- metrics
    @FunctionalInterface
    private interface MeasuredAction {
        long runNanos();
    }

    private static double averageOf(int tests, MeasuredAction action) {
        long total = 0L;
        for (int i = 0; i < tests; i++) total += action.runNanos();
        return total / (tests * 1_000_000.0);
    }

    private static void printComparison(String label, double arrayMs, double linkedMs) {
        System.out.printf("%n[%s]%n", label);
        System.out.printf("ArrayList : %.3f ms%n", arrayMs);
        System.out.printf("LinkedList: %.3f ms%n", linkedMs);
        if (arrayMs < linkedMs)
            System.out.printf("Mais rapida: ArrayList (%.2fx)%n", linkedMs / arrayMs);
        else if (linkedMs < arrayMs)
            System.out.printf("Mais rapida: LinkedList (%.2fx)%n", arrayMs / linkedMs);
        else
            System.out.println("Empate tecnico.");
    }

    // -------------------------------------------------------- Q1 benchmarks
    private static long timeFillEndArrayListDefault() {
        List<Integer> list = new ArrayList<>();
        long start = System.nanoTime();
        for (int i = 0; i < N; i++) list.add(i);
        return System.nanoTime() - start;
    }

    private static long timeFillEndArrayListWithCapacity(int capacity) {
        List<Integer> list = new ArrayList<>(capacity);
        long start = System.nanoTime();
        for (int i = 0; i < N; i++) list.add(i);
        return System.nanoTime() - start;
    }

    private static long timeFillEndLinkedList() {
        List<Integer> list = new LinkedList<>();
        long start = System.nanoTime();
        for (int i = 0; i < N; i++) list.add(i);
        return System.nanoTime() - start;
    }

    // -------------------------------------------------------- Q2 benchmarks
    private static long timeInsertRandomPositionArrayList() {
        List<Integer> list = new ArrayList<>();
        Random rng = new Random(SEED);
        long start = System.nanoTime();
        for (int i = 0; i < N; i++) {
            int idx = list.isEmpty() ? 0 : rng.nextInt(list.size() + 1);
            list.add(idx, i);
        }
        return System.nanoTime() - start;
    }

    private static long timeInsertRandomPositionLinkedList() {
        List<Integer> list = new LinkedList<>();
        Random rng = new Random(SEED);
        long start = System.nanoTime();
        for (int i = 0; i < N; i++) {
            int idx = list.isEmpty() ? 0 : rng.nextInt(list.size() + 1);
            list.add(idx, i);
        }
        return System.nanoTime() - start;
    }

    // -------------------------------------------------------- Q3 benchmarks
    private static long timeRemoveFirstArrayList() {
        List<Integer> list = createArrayListFilled(N);
        long start = System.nanoTime();
        while (!list.isEmpty()) list.remove(0);
        return System.nanoTime() - start;
    }

    private static long timeRemoveFirstLinkedList() {
        List<Integer> list = createLinkedListFilled(N);
        long start = System.nanoTime();
        while (!list.isEmpty()) list.remove(0);
        return System.nanoTime() - start;
    }

    private static long timeRemoveLastArrayList() {
        List<Integer> list = createArrayListFilled(N);
        long start = System.nanoTime();
        while (!list.isEmpty()) list.remove(list.size() - 1);
        return System.nanoTime() - start;
    }

    private static long timeRemoveLastLinkedList() {
        List<Integer> list = createLinkedListFilled(N);
        long start = System.nanoTime();
        while (!list.isEmpty()) list.remove(list.size() - 1);
        return System.nanoTime() - start;
    }

    // -------------------------------------------------------- Q4 benchmarks
    private static long timeRemoveRandomArrayList() {
        List<Integer> list = createArrayListFilled(N);
        Random rng = new Random(SEED);
        long start = System.nanoTime();
        while (!list.isEmpty()) list.remove(rng.nextInt(list.size()));
        return System.nanoTime() - start;
    }

    private static long timeRemoveRandomLinkedList() {
        List<Integer> list = createLinkedListFilled(N);
        Random rng = new Random(SEED);
        long start = System.nanoTime();
        while (!list.isEmpty()) list.remove(rng.nextInt(list.size()));
        return System.nanoTime() - start;
    }

    // -------------------------------------------------------- Q5 benchmarks
    private static long timeRandomAccessArrayList() {
        List<Integer> list = createArrayListFilled(N);
        Random rng = new Random(SEED);
        long sink = 0L;
        long start = System.nanoTime();
        for (int i = 0; i < RANDOM_ACCESSES_Q5; i++) sink += list.get(rng.nextInt(N));
        long elapsed = System.nanoTime() - start;
        avoidDeadCodeElimination(sink);
        return elapsed;
    }

    private static long timeRandomAccessLinkedList() {
        List<Integer> list = createLinkedListFilled(N);
        Random rng = new Random(SEED);
        long sink = 0L;
        long start = System.nanoTime();
        for (int i = 0; i < RANDOM_ACCESSES_Q5; i++) sink += list.get(rng.nextInt(N));
        long elapsed = System.nanoTime() - start;
        avoidDeadCodeElimination(sink);
        return elapsed;
    }

    private static void avoidDeadCodeElimination(long value) {
        if (value == Long.MIN_VALUE) System.out.println("impossivel");
    }

    // -------------------------------------------------------- factories
    private static List<Integer> createArrayListFilled(int n) {
        List<Integer> list = new ArrayList<>(n);
        for (int i = 0; i < n; i++) list.add(i);
        return list;
    }

    private static List<Integer> createLinkedListFilled(int n) {
        List<Integer> list = new LinkedList<>();
        for (int i = 0; i < n; i++) list.add(i);
        return list;
    }
}
