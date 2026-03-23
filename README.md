# Mercado de Ações

Sistema de simulação de mercado de ações desenvolvido em Java 21, aplicando **Clean Code**, **Object Calisthenics** e os padrões de projeto **Observer** e **Mediator**.

---

## Sumário

- [Visão Geral](#visão-geral)
- [Regras de Negócio](#regras-de-negócio)
- [Padrões de Projeto](#padrões-de-projeto)
- [Object Calisthenics](#object-calisthenics)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Funcionamento da Simulação](#funcionamento-da-simulação)
- [Como Executar](#como-executar)
- [Testes](#testes)

---

## Visão Geral

O sistema modela um mercado de ações simplificado onde:

- Múltiplas **empresas** têm ações negociáveis.
- **Investidores** registram ordens de compra ou venda a um preço-alvo.
- Um **livro de ordens** (mediador) combina compradores e vendedores automaticamente.
- O **preço das ações** é atualizado a cada transação executada.
- Investidores podem se inscrever para receber **notificações em tempo real** de variações de preço.

---

## Regras de Negócio

### Empresas

- Cada empresa possui um **nome único** e um **preço de ação**.
- O preço **não é definido na criação** da empresa — ele é estabelecido pela primeira transação realizada.
- O preço é atualizado após cada transação executada no livro de ordens.

### Ordens de Compra e Venda

- Um investidor registra uma **ordem de compra** informando: empresa, quantidade e preço máximo que aceita pagar.
- Um investidor registra uma **ordem de venda** informando: empresa, quantidade e preço mínimo que aceita receber.
- Toda ordem nasce com status **PENDENTE** e aguarda uma contraparte compatível.
- Uma ordem permanece pendente até ser totalmente executada ou cancelada.

### Combinação de Ordens (Matching)

- Uma ordem de compra e uma de venda são **compatíveis** quando:
  > `preço do comprador >= preço do vendedor`
- Quando há compatibilidade, a transação é executada **imediatamente**.
- A combinação prioriza o **maior lance de compra** (comprador que paga mais é atendido primeiro).

### Preço de Execução

| Situação | Preço de execução |
|---|---|
| **Primeira transação** da empresa (sem preço estabelecido) | Lance do **comprador** |
| Transações **subsequentes** (preço já estabelecido) | Pedido do **vendedor** |

Essa regra reflete a semântica de ordens limitadas: o comprador define o valor inicial de mercado; nas negociações seguintes, o vendedor determina o preço de fechamento.

### Execução Parcial de Ordens

- Quando o comprador quer 100 ações mas o vendedor tem apenas 60, são executadas **60 ações**.
- A quantidade executada é sempre `min(quantidade_compra, quantidade_venda)`.
- A ordem maior continua **PENDENTE** com a quantidade restante, aguardando nova contraparte.
- Após cada execução, o sistema verifica automaticamente se novas combinações são possíveis (recursão).

### Transferência de Ações

- Ao executar uma transação, as ações são **deduzidas da carteira do vendedor** e **creditadas na carteira do comprador**.
- Um vendedor não pode registrar uma venda se não possuir ações suficientes na carteira.
- A carteira nunca pode ter saldo negativo — violações lançam exceção.

### Notificações (Observer)

- Investidores podem se **inscrever** em uma empresa para receber alertas de preço.
- Após cada atualização de preço, **todos os inscritos são notificados** com o novo valor.
- Investidores podem se **desinscrever** a qualquer momento, parando de receber notificações.
- A notificação ocorre apenas após transações executadas — ordens sem match não geram notificação.

---

## Padrões de Projeto

### Observer

```
Empresa (Subject) ──notifica──► Investidor (Observer)
     │                               │
  inscrever()               aoAtualizarPreco()
  desinscrever()
  notificarObservadores()
```

- `SujeitoDePreco` — interface do Subject: gerencia inscrições e dispara notificações.
- `ObservadorDePreco` — interface do Observer: recebe o evento `aoAtualizarPreco(empresa, preco)`.
- `Empresa` implementa `SujeitoDePreco`; `Investidor` implementa `ObservadorDePreco`.

### Mediator

```
Investidor ──registrar ordem──► LivroDeOrdens ──atualizar preço──► Empresa
                                      │
                              CombinadorDeOrdens
                           (algoritmo puro de matching)
```

- `LivroDeOrdens` — mediador central por empresa: recebe ordens, executa matching, aciona atualização de preço.
- `CombinadorDeOrdens` — componente puro e sem estado: recebe as listas de ordens e retorna o par compatível.
- `AlvoDeAtualizacaoDePreco` — interface que `Empresa` implementa, usada pelo `LivroDeOrdens` para quebrar dependência circular.

### Por que híbrido Observer + Mediator?

O Observer sozinho não é suficiente: é preciso um componente que coordene a negociação entre dois investidores antes de notificar os demais. O Mediator centraliza essa lógica no `LivroDeOrdens`, que depois aciona o Observer via `Empresa.atualizarPreco()`.

---

## Object Calisthenics

| Regra | Como foi aplicada |
|---|---|
| **1 nível de indentação por método** | Cada método delega sub-passos a métodos privados nomeados; sem aninhamento de loops/ifs |
| **Sem `else`** | Guard clauses antecipadas e `Optional.ifPresent` substituem todos os blocos `else` |
| **Primitivos encapsulados** | `PrecoAcao` (BigDecimal), `QuantidadeAcao` (int), `NomeDaEmpresa` (String), `NomeDoInvestidor` (String) |
| **Coleções first-class** | `ListaDeOrdens`, `Carteira`, `ListaDeTransacoes`, `ListaDeEmpresas`, `ListaDeInvestidores` — cada uma expõe apenas comportamentos, nunca a coleção interna |
| **Um ponto por linha** | Cadeias de chamadas são quebradas em variáveis intermediárias com nomes significativos |
| **Sem abreviações** | `quantidadeRestante` (não `qtd`), `ordemDeCompra` (não `oc`), `precoAlvo` (não `pa`) |
| **Entidades pequenas** | Nenhuma classe ultrapassa ~100 linhas; responsabilidades divididas em classes específicas |
| **Sem getters/setters que expõem estado** | Métodos de comportamento: `possuiPrecoEstabelecido()`, `receberAcoes()`, `deduzirAcoes()`, `estaPendente()` |

---

## Estrutura do Projeto

```
Problema02/src/main/java/org/example/
│
├── Main.java                            → Ponto de entrada; executa a simulação
│
├── observer/
│   ├── ObservadorDePreco.java           → Interface Observer (Investidor implementa)
│   └── SujeitoDePreco.java             → Interface Subject (Empresa implementa)
│
├── mediador/
│   ├── AlvoDeAtualizacaoDePreco.java   → Interface para Empresa receber updates do LivroDeOrdens
│   ├── LivroDeOrdens.java              → Mediator: matching, execução, histórico de transações
│   ├── CombinadorDeOrdens.java         → Algoritmo puro de busca de par compatível
│   └── ParDeOrdens.java                → Tupla imutável (OrdemDeCompra, OrdemDeVenda)
│
├── dominio/
│   ├── empresa/
│   │   ├── Empresa.java                → Subject Observer + AlvoDeAtualizacaoDePreco
│   │   ├── NomeDaEmpresa.java          → Value object (String)
│   │   └── ListaDeEmpresas.java        → First-class collection
│   │
│   ├── acao/
│   │   ├── PrecoAcao.java              → Value object (BigDecimal, 2 casas, Comparable)
│   │   └── QuantidadeAcao.java         → Value object (int positivo, add/subtract/min)
│   │
│   ├── ordem/
│   │   ├── Ordem.java                  → Classe abstrata: investidor, preço, quantidade, status
│   │   ├── OrdemDeCompra.java          → Concreta: direção COMPRA
│   │   ├── OrdemDeVenda.java           → Concreta: direção VENDA
│   │   ├── TipoOrdem.java              → Enum: COMPRA, VENDA
│   │   ├── StatusOrdem.java            → Enum: PENDENTE, EXECUTADA
│   │   └── ListaDeOrdens.java          → First-class collection com sorting e busca
│   │
│   ├── transacao/
│   │   ├── Transacao.java              → Registro imutável de uma transação concluída
│   │   └── ListaDeTransacoes.java      → First-class collection com histórico
│   │
│   └── investidor/
│       ├── Investidor.java             → Observer; possui Carteira; executa ordens
│       ├── NomeDoInvestidor.java       → Value object (String)
│       ├── Carteira.java               → First-class collection: Map<NomeDaEmpresa, QuantidadeAcao>
│       └── ListaDeInvestidores.java    → First-class collection
│
└── simulacao/
    ├── FabricaDeDadosMock.java         → Cria empresas e investidores com dados fictícios
    └── SimulacaoDoMercado.java         → Orquestra o cenário passo a passo
```

---

## Funcionamento da Simulação

A simulação usa 4 investidores e 2 empresas com dados fictícios:

| Investidor | Inscrições | Carteira inicial |
|---|---|---|
| Alice | PetrobrasSA | — |
| Bob | PetrobrasSA | 50 ações PetrobrasSA |
| Carol | ValeSA | — |
| Dave | — | 100 ações PetrobrasSA, 200 ações ValeSA |

### Passos executados

| Passo | Ação | Resultado |
|---|---|---|
| 1 | Dave vende 100 PetrobrasSA @ R$ 30,00 | Sem comprador → PENDENTE |
| 2 | Alice compra 100 PetrobrasSA @ R$ 35,00 | Match! Preço **estabelecido** em R$ 35,00. Alice e Bob notificados |
| 3 | Bob vende 50 PetrobrasSA @ R$ 38,00 | Sem comprador → PENDENTE |
| 4 | Alice compra 50 PetrobrasSA @ R$ 38,00 | Match! Preço **atualizado** para R$ 38,00. Alice e Bob notificados |
| 5 | Carol compra 200 ValeSA @ R$ 20,00 | Sem vendedor → PENDENTE |
| 6 | Dave vende 200 ValeSA @ R$ 18,00 | Match! Preço **estabelecido** em R$ 20,00. Carol notificada |

### Carteiras ao final

| Investidor | Ações |
|---|---|
| Alice | 150 PetrobrasSA |
| Bob | (zerada — vendeu tudo) |
| Carol | 200 ValeSA |
| Dave | (zerada — vendeu tudo) |

### Preços finais

| Empresa | Preço |
|---|---|
| PetrobrasSA | R$ 38,00 |
| ValeSA | R$ 20,00 |

### Saída esperada no console

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

========== CARTEIRAS FINAIS ==========
  Carteira de Alice:
    PetrobrasSA: 150 ações
========== PRECOS DAS EMPRESAS ==========
  PetrobrasSA: R$ 38.00
  ValeSA: R$ 20.00
```

---

## Como Executar

**Pré-requisitos:** Java 21, Maven 3.6+

```bash
cd Problema02

# Compilar e executar a simulação
mvn compile exec:java -Dexec.mainClass=org.example.Main

# Rodar os testes
mvn test
```

---

## Testes

34 testes unitários cobrindo as principais regras de negócio:

| Classe de teste | O que testa |
|---|---|
| `PrecoAcaoTest` | Validação de valor positivo, comparação, arredondamento, equality |
| `QuantidadeAcaoTest` | Criação, adição, subtração, subtração insuficiente, mínimo |
| `CarteiraTest` | Recebimento, acúmulo, dedução, saldo insuficiente, empresa ausente |
| `OrdemTest` | Status inicial, execução total, execução parcial, direção da ordem |
| `CombinadorDeOrdensTest` | Match quando compatível, sem match, preços iguais, prioridade do maior lance |
| `EmpresaTest` | Preço inicial ausente, estabelecimento na 1ª transação, notificação de observadores, desincrição |