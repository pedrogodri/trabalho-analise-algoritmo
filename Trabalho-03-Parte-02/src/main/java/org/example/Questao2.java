package org.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Questao2 {

    static class Lugar {
        String nome;
        int valor;
        int dias;

        Lugar(String nome, int valor, int dias) {
            this.nome = nome;
            this.valor = valor;
            this.dias = dias;
        }

        double ratio() {
            return (double) valor / dias;
        }
    }

    public static void resolver() {
        System.out.println("=== Questão 2: Planejamento de Viagem Guloso ===");

        int diasDisponiveis = 7;
        List<Lugar> lugares = new ArrayList<>();
        lugares.add(new Lugar("Paris",      10, 3));
        lugares.add(new Lugar("Roma",        8, 2));
        lugares.add(new Lugar("Londres",     9, 3));
        lugares.add(new Lugar("Amsterdã",    6, 2));
        lugares.add(new Lugar("Barcelona",   7, 2));
        lugares.add(new Lugar("Viena",       5, 1));
        lugares.add(new Lugar("Praga",       4, 1));

        // Estratégia gulosa: ordenar por maior razão valor/dia e adicionar se couber
        lugares.sort(Comparator.comparingDouble(Lugar::ratio).reversed());

        List<Lugar> selecionados = new ArrayList<>();
        int diasUsados = 0;
        int pontosTotal = 0;

        for (Lugar lugar : lugares) {
            if (diasUsados + lugar.dias <= diasDisponiveis) {
                selecionados.add(lugar);
                diasUsados += lugar.dias;
                pontosTotal += lugar.valor;
            }
        }

        System.out.println("Dias disponíveis: " + diasDisponiveis);
        System.out.println("Lugares e razão valor/dia (critério guloso):");
        for (Lugar l : lugares) {
            System.out.printf("  %-12s valor=%2d  dias=%d  ratio=%.2f%n",
                    l.nome, l.valor, l.dias, l.ratio());
        }
        System.out.println();
        System.out.println("Lugares selecionados pelo algoritmo guloso:");
        for (Lugar l : selecionados) {
            System.out.println("  " + l.nome + " (" + l.valor + " pts, " + l.dias + " dias)");
        }
        System.out.println("Total de pontos: " + pontosTotal);
        System.out.println("Dias utilizados: " + diasUsados);
        System.out.println();
        System.out.println("O algoritmo é ótimo? NÃO.");
        System.out.println("Solução gulosa: " + nomesLista(selecionados) + " = " + pontosTotal + " pts em " + diasUsados + " dias.");
        System.out.println("Solução ótima:  Paris + Roma + Viena + Praga = 10+8+5+4 = 27 pts em 7 dias.");
        System.out.println("O greedy baseado em ratio pode perder combinações de maior valor total,");
        System.out.println("pois otimiza localmente sem considerar o impacto global das escolhas.");
        System.out.println();
    }

    private static String nomesLista(List<Lugar> lista) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lista.size(); i++) {
            if (i > 0) sb.append(" + ");
            sb.append(lista.get(i).nome);
        }
        return sb.toString();
    }
}
