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