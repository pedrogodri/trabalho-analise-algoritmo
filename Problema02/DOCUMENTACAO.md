# 📈 Sistema de Bolsa de Valores — Documentação Técnica

> Simulação de um pregão eletrônico inspirado na B3, implementado em Java 21 com Maven.

---

## Sumário

1. [Visão Geral do Sistema](#1-visão-geral-do-sistema)
2. [Estrutura de Pacotes](#2-estrutura-de-pacotes)
3. [Padrões de Projeto Aplicados](#3-padrões-de-projeto-aplicados)
   - 3.1 [Observer](#31-observer)
   - 3.2 [Mediator](#32-mediator)
   - 3.3 [Factory Method](#33-factory-method)
   - 3.4 [Facade](#34-facade)
4. [Clean Code](#4-clean-code)
5. [Refatoração e Object Calisthenics](#5-refatoração-e-object-calisthenics)
6. [Testes Unitários](#6-testes-unitários)
7. [Diagrama de Classes](#7-diagrama-de-classes)
8. [Como Executar](#8-como-executar)

---

## 1. Visão Geral do Sistema

O sistema simula o funcionamento básico de uma bolsa de valores:

- **Investidores** registram **ordens de compra e venda** de ações de empresas.
- Quando o preço de uma ordem de compra é **maior ou igual** ao de uma ordem de venda, ocorre um **match** (combinação).
- Ao executar o match, as ações são transferidas entre investidores, o preço da ação é **atualizado** e todos os investidores inscritos naquela empresa são **notificados em tempo real**.
- Ordens são executadas **totalmente ou parcialmente** (execução parcial suportada).

### Fluxo resumido

```
Investidor → registra Ordem → Empresa → LivroDeOrdens → CombinadorDeOrdens
                                                              ↓ (match encontrado)
                                                       executa Transação
                                                              ↓
                                              Empresa.atualizarPreco()
                                                              ↓
                                       notifica todos os Observadores inscritos
```

---

## 2. Estrutura de Pacotes

```
org.example
├── dominio
│   ├── acao          → Value Objects: PrecoAcao, QuantidadeAcao
│   ├── empresa       → Empresa, NomeDaEmpresa, ListaDeEmpresas
│   ├── investidor    → Investidor, Carteira, NomeDoInvestidor, ListaDeInvestidores
│   ├── ordem         → Ordem (abstract), OrdemDeCompra, OrdemDeVenda,
│   │                   ListaDeOrdens, TipoOrdem, StatusOrdem
│   └── transacao     → Transacao, ListaDeTransacoes
├── mediador          → LivroDeOrdens, CombinadorDeOrdens,
│                       ParDeOrdens, AlvoDeAtualizacaoDePreco
├── observer          → SujeitoDePreco, ObservadorDePreco
├── infra             → LogMercado (Facade)
└── simulacao         → SimulacaoDoMercado, FabricaDeDadosMock
```

---

## 3. Padrões de Projeto Aplicados

### 3.1 Observer

**Contexto:** investidores precisam ser notificados em tempo real quando o preço de uma ação muda.

**Como foi aplicado:**

| Papel | Classe |
|-------|--------|
| `Subject` (Sujeito) | `Empresa` implementa `SujeitoDePreco` |
| `Observer` (Observador) | `Investidor` implementa `ObservadorDePreco` |
| Interface do Sujeito | `SujeitoDePreco` — `inscrever()`, `desinscrever()`, `notificarObservadores()` |
| Interface do Observer | `ObservadorDePreco` — `aoAtualizarPreco()` |

```java
// Investidor se inscreve para receber notificações
petrobrasSA.inscrever(alice);
petrobrasSA.inscrever(bob);

// Quando ocorre um match, Empresa notifica automaticamente:
// → [NOTIFICACAO] Alice <- PetrobrasSA atualizada para R$ 35.00
// → [NOTIFICACAO] Bob  <- PetrobrasSA atualizada para R$ 35.00
```

**Benefício:** `Empresa` não conhece nada sobre `Investidor`; a dependência é invertida através da interface `ObservadorDePreco`. Novos tipos de observadores podem ser adicionados sem modificar o domínio.

---

### 3.2 Mediator

**Contexto:** ordens de compra e venda precisam ser combinadas sem que compradores e vendedores se conheçam diretamente.

**Como foi aplicado:**

| Papel | Classe |
|-------|--------|
| `Mediator` | `LivroDeOrdens` — orquestra todo o ciclo de vida de uma negociação |
| Algoritmo puro | `CombinadorDeOrdens` — encontra pares compatíveis (sem estado) |
| DTO de resultado | `ParDeOrdens` — transporta o par encontrado |
| Interface anti-circular | `AlvoDeAtualizacaoDePreco` — permite que `LivroDeOrdens` atualize o preço sem depender diretamente de `Empresa` |

```java
// LivroDeOrdens recebe ordens e orquestra tudo:
// 1. Delega ao CombinadorDeOrdens a busca de um par compatível
// 2. Executa a transferência de ações entre investidores
// 3. Notifica a Empresa para atualizar o preço
// 4. Registra a Transacao no histórico
// 5. Chama a si mesmo recursivamente para novos matches
```

**Benefício:** `Investidor` e `Empresa` nunca se comunicam diretamente no processo de negociação. Toda a lógica de matching fica isolada e testável separadamente.

---

### 3.3 Factory Method

**Contexto:** a simulação precisa criar múltiplos investidores e empresas com dados fictícios sem poluir o código de orquestração.

**Como foi aplicado:**

```java
// FabricaDeDadosMock — factory estática para dados de simulação
public final class FabricaDeDadosMock {
    public static Empresa   criarPetrobrasSA() { ... }
    public static Empresa   criarValeSA()      { ... }
    public static Investidor criarAlice()      { ... }
    public static Investidor criarBob()        { ... }
    public static Investidor criarCarol()      { ... }
    public static Investidor criarDave()       { ... }
}
```

**Benefício:** `SimulacaoDoMercado` recebe objetos prontos e se preocupa apenas com a orquestração do cenário, não com construção dos atores.

---

### 3.4 Facade

**Contexto:** `System.out.println` estava espalhado em 5 classes de domínio (`Empresa`, `Investidor`, `Carteira`, `Transacao`, `LivroDeOrdens`), violando o Princípio da Responsabilidade Única — domínio não deve ser responsável por saída de console.

**Como foi aplicado:**

```java
// Antes (violação de SRP em Empresa):
System.out.println("[PRECO] " + nome.getNome() + " atualizado para " + preco.exibir());

// Depois (delegação à Facade):
LogMercado.precoAtualizado(nome, novoPreco);
```

`LogMercado` é a **Facade** sobre toda a camada de saída. Agrupa todos os métodos de log em categorias organizadas:

| Categoria | Métodos |
|-----------|---------|
| Ordens | `registroDeOrdemDeCompra`, `registroDeOrdemDeVenda`, `nenhumaCombinacao` |
| Combinação | `combinacaoEncontrada`, `transacaoExecutada` |
| Preço | `precoEstabelecido`, `precoAtualizado`, `semPrecoEstabelecido` |
| Observer | `notificacaoDePreco` |
| Carteira | `cabecalhoCarteira`, `carteiraSemAcoes`, `linhaCarteira` |

**Benefício:** para trocar o mecanismo de saída (ex: SLF4J, arquivo de log), basta modificar `LogMercado` — zero impacto no domínio.

---

## 4. Clean Code

### Nomes significativos (Meaningful Names)

Todos os identificadores do projeto expressam **intenção**, sem abreviações:

| ❌ Evitado | ✅ Usado |
|-----------|---------|
| `p`, `q`, `i` | `precoAlvo`, `quantidadeRestante`, `investidor` |
| `calc()` | `resolverPrecoDeExecucao()` |
| `check()` | `possuiAcoesSuficientes()` |
| `do()` | `executarCombinacao()` |
| `update()` | `atualizarPreco()` |

### Funções pequenas com uma única responsabilidade (SRP)

`LivroDeOrdens.executarCombinacao()` foi decomposto em 4 métodos privados:

```java
private void executarCombinacao(ParDeOrdens par) {
    PrecoAcao preco     = resolverPrecoDeExecucao(compra, venda);  // 1. regra de preço
    QuantidadeAcao qtd  = resolverQuantidade(compra, venda);       // 2. regra de quantidade
    transferirAcoes(comprador, vendedor, qtd);                      // 3. movimentação
    marcarOrdens(compra, venda, qtd);                               // 4. ciclo de vida das ordens
}
```

`Carteira.deduzirAcoes()` foi decomposto em:

```java
public void deduzirAcoes(NomeDaEmpresa empresa, QuantidadeAcao quantidade) {
    validarSaldoSuficiente(empresa, quantidade);       // guarda da invariante
    atualizarSaldoAposVenda(empresa, saldo, quantidade); // lógica de remoção/atualização
}
```

### Sem números mágicos

```java
// ❌ Antes — bug com número mágico sem significado:
quantidadeRestante = new QuantidadeAcao(1);
status = StatusOrdem.EXECUTADA;

// ✅ Depois — intenção expressa pelo método:
marcarComoExecutada();
```

### Sem código morto

O pacote vazio `br/com/bolsa/observer/` foi **removido**.

### Value Objects imutáveis

`PrecoAcao` e `QuantidadeAcao` são imutáveis: toda operação retorna uma **nova instância**, nunca modifica o estado interno. Isso elimina bugs de compartilhamento de estado.

---

## 5. Refatoração e Object Calisthenics

As 9 regras do Object Calisthenics foram aplicadas ao longo de todo o projeto:

### Regra 1 — Um nível de indentação por método

Cada método possui no máximo um nível de aninhamento. Condições complexas foram extraídas para métodos privados com nomes descritivos.

### Regra 2 — Não usar `else`

```java
// ✅ Early return substitui else em toda a base de código:
private void registrarAtualizacaoDePreco(PrecoAcao novoPreco) {
    if (possuiPrecoEstabelecido()) {
        LogMercado.precoAtualizado(nome, novoPreco);
        return;  // ← early return, sem else
    }
    LogMercado.precoEstabelecido(nome, novoPreco);
}
```

### Regra 3 — Encapsular primitivos e Strings

Nenhuma `String` crua ou `int`/`BigDecimal` primitivo representa um conceito de domínio diretamente. Todos foram encapsulados em Value Objects:

| Primitivo | Value Object |
|-----------|-------------|
| `String` nome de empresa | `NomeDaEmpresa` |
| `String` nome de investidor | `NomeDoInvestidor` |
| `BigDecimal` preço | `PrecoAcao` |
| `int` quantidade | `QuantidadeAcao` |

### Regra 4 — First-class Collections

Nenhuma coleção nua (`List<>`, `Map<>`) é exposta publicamente. Todas foram encapsuladas:

| Coleção | First-class Collection |
|---------|----------------------|
| `List<Ordem>` | `ListaDeOrdens` |
| `List<Empresa>` | `ListaDeEmpresas` |
| `List<Investidor>` | `ListaDeInvestidores` |
| `List<Transacao>` | `ListaDeTransacoes` |
| `Map<NomeDaEmpresa, QuantidadeAcao>` | `Carteira` |

### Regra 5 — Lei de Demeter (Um ponto por linha)

```java
// ❌ Antes — violação da Lei de Demeter (cadeia de 3 níveis):
investidor.getNome().getNome()

// ✅ Depois — método que expressa a intenção diretamente:
investidor.getNomeCompleto()
```

A correção foi aplicada em todos os pontos: `LivroDeOrdens`, `Transacao`, `Ordem.toString()` e `Investidor.aoAtualizarPreco()`.

### Regra 6 — Não abreviar

Todos os nomes são completos e autoexplicativos. Nenhuma abreviação foi usada.

### Regra 7 — Manter entidades pequenas

Cada classe tem uma responsabilidade única e tamanho reduzido. O pacote com mais classes é `dominio.ordem` com 6 arquivos, todos dentro do limite.

### Regra 8 — Máximo de duas variáveis de instância

As classes de domínio principais respeitam esse limite:

| Classe | Variáveis de instância |
|--------|----------------------|
| `PrecoAcao` | 1 (`valor`) |
| `QuantidadeAcao` | 1 (`quantidade`) |
| `NomeDaEmpresa` | 1 (`nome`) |
| `NomeDoInvestidor` | 1 (`nome`) |
| `Investidor` | 2 (`nome`, `carteira`) |
| `ParDeOrdens` | 2 (`ordemDeCompra`, `ordemDeVenda`) |

### Regra 9 — Sem getters/setters desnecessários

O domínio expõe **comportamento**, não estado:

```java
// ❌ Getter que expõe estado para o chamador calcular:
carteira.getAcoes().get(empresa).getQuantidade()

// ✅ Método que expressa a regra de negócio:
carteira.possuiAcoesSuficientes(empresa, quantidade)
```

---

## 6. Testes Unitários

O projeto possui **34 testes unitários** com cobertura de todos os componentes críticos, todos passando:

```
Tests run: 34, Failures: 0, Errors: 0, Skipped: 0  ✅
```

| Classe de Teste | Qtd | O que testa |
|----------------|-----|-------------|
| `PrecoAcaoTest` | 8 | Criação, validação, comparação, arredondamento, igualdade |
| `QuantidadeAcaoTest` | 7 | Criação, adição, subtração, mínimo, validações |
| `CarteiraTest` | 5 | Receber, deduzir, acumular, saldo insuficiente, empresa ausente |
| `OrdemTest` | 4 | Status pendente, dedução total, dedução parcial, tipo de ordem |
| `EmpresaTest` | 5 | Preço inicial, primeira transação, notificação, sem match, desincrição |
| `CombinadorDeOrdensTest` | 5 | Match exato, comprador maior, comprador menor, listas vazias, prioridade de preço |

### Exemplo de teste — Observer

```java
@Test
void deveNotificarObservadoresAposAtualizacaoDePreco() {
    List<String> notificacoes = new ArrayList<>();
    empresa.inscrever((nomeEmpresa, novoPreco) ->
        notificacoes.add(nomeEmpresa.getNome() + "=" + novoPreco.exibir()));

    empresa.registrarOrdemDeVenda(new OrdemDeVenda(vendedor, new PrecoAcao("30.00"), new QuantidadeAcao(100)));
    empresa.registrarOrdemDeCompra(new OrdemDeCompra(comprador, new PrecoAcao("35.00"), new QuantidadeAcao(100)));

    assertEquals(1, notificacoes.size());
    assertTrue(notificacoes.get(0).contains("EmpresaTeste"));
}
```

### Exemplo de teste — Mediator / Combinador

```java
@Test
void deveEscolherMaiorCompradorPrimeiro() {
    // CompradorB oferece mais — deve ser o escolhido pelo algoritmo
    compras.adicionar(new OrdemDeCompra(compradorA, new PrecoAcao("25.00"), new QuantidadeAcao(100)));
    compras.adicionar(new OrdemDeCompra(compradorB, new PrecoAcao("32.00"), new QuantidadeAcao(100)));
    vendas.adicionar(new OrdemDeVenda(vendedor, new PrecoAcao("30.00"), new QuantidadeAcao(100)));

    Optional<ParDeOrdens> resultado = combinador.encontrarCombinacao(compras, vendas);

    assertTrue(resultado.isPresent());
    assertEquals("CompradorB", resultado.get().getOrdemDeCompra().getInvestidor().getNomeCompleto());
}
```

---

## 7. Diagrama de Classes

O diagrama de classes completo está disponível em dois formatos na raiz do projeto:

- **`diagrama-classes.puml`** — fonte PlantUML editável
- **`Diagrama de Classes - Sistema de Bolsa de Valores.png`** — imagem PNG
- **`Diagrama de Classes - Sistema de Bolsa de Valores.svg`** — imagem vetorial SVG

### Relações principais

```
«interface» SujeitoDePreco  ◄────────────────  Empresa  ────────────────►  «interface» AlvoDeAtualizacaoDePreco
«interface» ObservadorDePreco ◄── Investidor                                        ▲
                                      │                                    LivroDeOrdens
                                   Carteira                                     │         │
                             (Map<NomeDaEmpresa,                    CombinadorDeOrdens  ListaDeOrdens
                              QuantidadeAcao>)                              │
                                                                       ParDeOrdens
                                                                      /           \
                                                               OrdemDeCompra   OrdemDeVenda
                                                                      \           /
                                                                        Ordem (abstract)
```

---

## 8. Como Executar

### Pré-requisitos
- Java 21+
- Maven 3.8+

### Rodar a simulação

```bash
mvn compile exec:java -Dexec.mainClass="org.example.Main"
```

### Rodar os testes

```bash
mvn test
```

### Saída esperada da simulação

```
========== INICIO DA SIMULACAO ==========

--- Passo 1: Dave registra venda de 100 PetrobrasSA @ R$30 ---
[MERCADO] Dave registra VENDA 100 @ R$ 30.00
[MERCADO] Nenhuma combinacao encontrada para PetrobrasSA

--- Passo 2: Alice registra compra de 100 PetrobrasSA @ R$35 ---
[MERCADO] Alice registra COMPRA 100 @ R$ 35.00
[COMBINACAO] COMPRA Alice @ R$ 35.00 <-> VENDA Dave @ R$ 30.00
[PRECO] PetrobrasSA estabelecido em R$ 35.00
[NOTIFICACAO] Alice <- PetrobrasSA atualizada para R$ 35.00
[NOTIFICACAO] Bob <- PetrobrasSA atualizada para R$ 35.00
[TRANSACAO] HH:mm:ss | PetrobrasSA | 100 acoes @ R$ 35.00 | Comprador: Alice | Vendedor: Dave
...
```

---

## Tecnologias

| Tecnologia | Versão | Uso |
|-----------|--------|-----|
| Java | 21 | Linguagem principal |
| Maven | 3.8+ | Build e dependências |
| JUnit Jupiter | 5.10.2 | Testes unitários |
| PlantUML | 1.2026.2 | Diagrama de classes |
