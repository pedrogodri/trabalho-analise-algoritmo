package org.example;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Questao3 {

    public static void resolver() {
        System.out.println("=== Questão 3: Cobertura de Estações de Rádio (Set Cover Guloso) ===");

        // Estados alvo da campanha
        Set<String> estadosAlvo = new HashSet<>(Arrays.asList(
                "MT", "RJ", "ES", "SP", "SC", "RS", "PR", "MS"
        ));

        // Mapeamento de estações para os estados que cobrem (LinkedHashMap preserva ordem de inserção)
        Map<String, Set<String>> estacoes = new LinkedHashMap<>();
        estacoes.put("Kum",     new HashSet<>(Arrays.asList("SP", "SC", "RS")));
        estacoes.put("Kdois",   new HashSet<>(Arrays.asList("RJ", "SP", "MT")));
        estacoes.put("Ktres",   new HashSet<>(Arrays.asList("ES", "SC", "PR")));
        estacoes.put("Kquatro", new HashSet<>(Arrays.asList("SC", "RS")));
        estacoes.put("Kcinco",  new HashSet<>(Arrays.asList("PR", "MS")));

        System.out.println("Estados alvo: " + estadosAlvo);
        System.out.println();

        Set<String> naoCobertoAinda = new HashSet<>(estadosAlvo);
        List<String> estacoesSelecionadas = new java.util.ArrayList<>();

        // Algoritmo guloso: a cada rodada escolhe a estação que cobre mais estados ainda não cobertos
        while (!naoCobertoAinda.isEmpty()) {
            String melhorEstacao = null;
            Set<String> melhorCobertura = new HashSet<>();

            for (Map.Entry<String, Set<String>> entry : estacoes.entrySet()) {
                if (estacoesSelecionadas.contains(entry.getKey())) continue;

                Set<String> coberturaAtual = new HashSet<>(entry.getValue());
                coberturaAtual.retainAll(naoCobertoAinda); // interseção com ainda não cobertos

                if (coberturaAtual.size() > melhorCobertura.size()) {
                    melhorCobertura = coberturaAtual;
                    melhorEstacao = entry.getKey();
                }
            }

            if (melhorEstacao == null) break; // nenhuma estação cobre o que falta

            estacoesSelecionadas.add(melhorEstacao);
            naoCobertoAinda.removeAll(melhorCobertura);

            System.out.println("Rodada " + estacoesSelecionadas.size() + ":");
            System.out.println("  Estação escolhida: " + melhorEstacao
                    + " -> cobre novos estados: " + melhorCobertura);
            System.out.println("  Estados ainda não cobertos: " + naoCobertoAinda);
        }

        System.out.println();
        System.out.println("Estações contratadas: " + estacoesSelecionadas);
        System.out.println("Total de estações: " + estacoesSelecionadas.size());
        System.out.println();

        // Verificação final: todos os estados cobertos?
        Set<String> coberturFinal = new HashSet<>();
        for (String estacao : estacoesSelecionadas) {
            coberturFinal.addAll(estacoes.get(estacao));
        }
        coberturFinal.retainAll(estadosAlvo);
        System.out.println("Cobertura final dos estados alvo: " + coberturFinal);
        System.out.println("Todos os estados cobertos? " + coberturFinal.equals(estadosAlvo));
        System.out.println();
        System.out.println("O algoritmo é ótimo? SIM, neste caso.");
        System.out.println("Não existe nenhuma combinação de 3 estações que cubra todos os 8 estados.");
        System.out.println("O algoritmo guloso de set cover não garante otimalidade em geral,");
        System.out.println("mas para esta instância específica ele encontra a solução de menor custo.");
        System.out.println();
    }
}
