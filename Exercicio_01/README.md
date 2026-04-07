# Exercício 01

Aplicação Java para comparar experimentalmente o desempenho de `ArrayList` e `LinkedList`.

## O que o programa mede

- inserção no final
- inserção em índice aleatório
- remoção do primeiro elemento
- remoção do último elemento
- remoção em índice aleatório
- 10.000 acessos aleatórios

Cada experimento calcula a média do tempo de execução em milissegundos e informa qual estrutura foi mais rápida.

## Requisitos

- Java 21
- Maven 3.9+

## Como executar

No diretório `Exercicio_01`, rode:

```bash
mvn clean compile exec:java
```

## Saída esperada

O programa imprime um bloco para cada questão, incluindo:

- tempo médio do `ArrayList`
- tempo médio do `LinkedList`
- estrutura mais rápida
- fator de velocidade entre elas

## Arquivo principal

- `src/main/java/org/example/Main.java`


## Resultado do Console
=== Questao 1: Insercao no final ===

[Insercao no final]
ArrayList : 0.653 ms
LinkedList: 0.849 ms
Mais rapida: ArrayList (1.30x)

--- Q1.2: ArrayList com capacidades iniciais diferentes ---
Capacidade inicial 10 -> media: 0.742 ms
Capacidade inicial 1000 -> media: 0.491 ms
Capacidade inicial 100000 -> media: 0.364 ms
Mais rapida: capacidade inicial 100000 (0.364 ms)
Motivo: capacidade inicial maior reduz realocacoes/copias internas durante crescimento do ArrayList.

=== Questao 2: Insercao em indice aleatorio valido ===

[Insercao aleatoria]
ArrayList : 158.938 ms
LinkedList: 7593.172 ms
Mais rapida: ArrayList (47.77x)

=== Questao 3: Remocao ate esvaziar ===

[Remover primeiro]
ArrayList : 351.759 ms
LinkedList: 0.370 ms
Mais rapida: LinkedList (951.14x)

[Remover ultimo]
ArrayList : 0.322 ms
LinkedList: 0.566 ms
Mais rapida: ArrayList (1.76x)

=== Questao 4: Remocao em indice aleatorio ate esvaziar ===

[Remocao aleatoria]
ArrayList : 157.115 ms
LinkedList: 2043.932 ms
Mais rapida: ArrayList (13.01x)

=== Questao 5: 10.000 acessos a indices aleatorios ===

[Acesso aleatorio (10k)]
ArrayList : 0.197 ms
LinkedList: 363.008 ms
Mais rapida: ArrayList (1847.19x)
Explicacao esperada:
- ArrayList: acesso por indice em O(1) (vetor contiguo).
- LinkedList: acesso por indice em O(n) (precisa percorrer nos).