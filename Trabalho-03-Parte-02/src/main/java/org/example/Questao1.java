package org.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Questao1 {

    static class Item {
        int id;
        int volume;

        Item(int id, int volume) {
            this.id = id;
            this.volume = volume;
        }
    }

    public static void resolver() {
        System.out.println("=== Questão 1: Carregamento Guloso do Caminhão ===");

        int capacidade = 100;
        List<Item> itens = new ArrayList<>();
        itens.add(new Item(1, 40));
        itens.add(new Item(2, 30));
        itens.add(new Item(3, 25));
        itens.add(new Item(4, 20));
        itens.add(new Item(5, 15));

        // Estratégia gulosa: ordenar por volume decrescente e adicionar o maior item que cabe
        itens.sort(Comparator.comparingInt((Item i) -> i.volume).reversed());

        List<Item> selecionados = new ArrayList<>();
        int volumeUsado = 0;

        for (Item item : itens) {
            if (volumeUsado + item.volume <= capacidade) {
                selecionados.add(item);
                volumeUsado += item.volume;
            }
        }

        System.out.println("Capacidade do caminhão: " + capacidade + " litros");
        System.out.println("Itens selecionados pelo algoritmo guloso:");
        for (Item item : selecionados) {
            System.out.println("  Item " + item.id + " -> " + item.volume + "L");
        }
        System.out.println("Volume total utilizado: " + volumeUsado + "L");
        System.out.println("Espaço restante: " + (capacidade - volumeUsado) + "L");
        System.out.println();
        System.out.println("O algoritmo é ótimo? NÃO.");
        System.out.println("O algoritmo guloso selecionou itens 1+2+3 = 95L (restam 5L inutilizados).");
        System.out.println("A solução ótima seria itens 1+3+4+5 = 40+25+20+15 = 100L (caminhão cheio).");
        System.out.println("O greedy falha porque ao escolher o maior item disponível em cada passo,");
        System.out.println("pode bloquear combinações menores que juntas encheriam melhor o caminhão.");
        System.out.println();
    }
}
