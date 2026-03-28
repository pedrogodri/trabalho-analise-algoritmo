# Casa Inteligente IoT

Sistema de controle de dispositivos domésticos inteligentes desenvolvido em Java 21, demonstrando o uso combinado dos padrões de projeto **Adapter**, **Facade** e **Strategy**, com aplicação de princípios de **Object Calisthenics**.

---

## Visão Geral

O sistema permite controlar lâmpadas, persianas e ar-condicionados de diferentes fabricantes por meio de uma interface unificada. Cada fabricante possui uma API própria e incompatível — os adaptadores resolvem essa heterogeneidade, e a fachada (`CasaInteligente`) oferece um único ponto de controle para toda a casa.

---

## Arquitetura

### Adapter (Estrutural)

**Problema:** cada fabricante expõe uma API diferente. Não é possível controlar uma `LampadaShoyuMi` e uma `LampadaPhellipes` da mesma forma sem uma camada de adaptação.

**Solução:** para cada fabricante foi criado um adaptador que implementa a interface universal correspondente e delega as chamadas ao objeto do fabricante, realizando as conversões necessárias.

```
Interface Universal        Adaptador                     API do Fabricante
──────────────────         ──────────────────────────    ──────────────────────
Lampada.ligar()     →      LampadaPhellipesAdaptador →   setIntensidade(100)
Lampada.desligar()  →      LampadaPhellipesAdaptador →   setIntensidade(0)

Persiana.abrir()    →      PersianaNatLightAdaptador  →  abrirPalheta() + subirPalheta()
Persiana.fechar()   →      PersianaNatLightAdaptador  →  descerPalheta() + fecharPalheta()

ArCondicionado      →      ArCondicionadoGellaKaza    →  loop aumentar/diminuirTemperatura()
  .definirTemp(25)          Adaptador
```

Implementações em:
- `src/main/java/org/example/adaptadores/lampada/`
- `src/main/java/org/example/adaptadores/persiana/`
- `src/main/java/org/example/adaptadores/arcondicionado/`

---

### Facade (Estrutural)

**Problema:** sem a fachada, o cliente precisaria conhecer e gerenciar três coleções separadas para executar qualquer operação na casa.

**Solução:** `CasaInteligente` fornece um único ponto de acesso que coordena internamente as coleções `Lampadas`, `Persianas` e `ArsCondicionados`.

```
Cliente
  └── CasaInteligente.ativarModo(new ModoTrabalho())
        ├── Lampadas.ligarTodas()
        ├── Persianas.abrirTodas()
        ├── ArsCondicionados.ligarTodos()
        └── ArsCondicionados.definirTemperaturaEmTodos(25)
```

Implementação em:
- `src/main/java/org/example/casa/CasaInteligente.java`

---

### Strategy (Comportamental)

**Problema:** cada modo de operação (Sono, Trabalho) tem comportamento diferente. Sem o padrão, `CasaInteligente` precisaria de condicionais para cada modo, e adicionar novos modos exigiria modificar a classe existente.

**Solução:** a interface `Modo` define o contrato de ativação. Cada modo é uma classe independente que encapsula suas próprias regras. `CasaInteligente` aceita qualquer implementação de `Modo` sem conhecer seus detalhes.

```java
// Adicionar um novo modo sem modificar CasaInteligente:
public final class ModoFesta implements Modo {
    public void ativar(Lampadas l, Persianas p, ArsCondicionados a) { ... }
}
casa.ativarModo(new ModoFesta()); // funciona imediatamente
```

Implementações em:
- `src/main/java/org/example/modos/`

---

## Estrutura de Pacotes

```
org.example/
├── interfaces/           → Contratos universais (Lampada, Persiana, ArCondicionado, Modo)
├── adaptadores/
│   ├── lampada/          → LampadaShoyuMiAdaptador, LampadaPhellipesAdaptador
│   ├── persiana/         → PersianaSolariusAdaptador, PersianaNatLightAdaptador
│   └── arcondicionado/   → ArCondicionadoVentoBaumnAdaptador, ArCondicionadoGellaKazaAdaptador
├── models/               → Lampadas, Persianas, ArsCondicionados (coleções de primeira classe)
├── modos/                → ModoSono, ModoTrabalho
├── casa/                 → CasaInteligente (Facade)
└── Main.java             → Demonstração de uso
```

---

## Comportamento dos Dispositivos

### Lâmpadas

| Fabricante | Comportamento |
|-----------|---------------|
| ShoyuMi | `ligar()` e `desligar()` diretos |
| Phellipes | Sem ligar/desligar — usa intensidade: 0 = desligada, 100 = totalmente ligada |

### Persianas

| Fabricante | Comportamento |
|-----------|---------------|
| Solarius | `subir` = abrir, `descer` = fechar. Sem restrições de ordem. |
| NatLight | As palhetas **devem estar abertas** para subir a persiana. Não é possível fechar as palhetas com a persiana erguida. A ordem correta de operação é gerenciada pelo adaptador. |

### Ar-Condicionados

| Fabricante | Comportamento |
|-----------|---------------|
| VentoBaumn | Temperatura inicial: 24°C. **Deve estar ligado** para chamar `definirTemperatura()`. Range: 15°C–35°C. |
| GellaKaza | Temperatura inicial: 28°C. Temperatura ajustada **1°C por vez**. Range: 15°C–35°C. Não requer verificação de estado para ajustar temperatura. |

### Modos de Operação

| Modo | Sequência de Ações |
|------|-------------------|
| **ModoSono** | 1. Desliga todas as lâmpadas → 2. Fecha todas as persianas → 3. Desliga todos os ACs |
| **ModoTrabalho** | 1. Liga todas as lâmpadas → 2. Abre todas as persianas → 3. Liga todos os ACs → 4. Define temperatura em 25°C |

> **Atenção:** no `ModoTrabalho`, os ACs são ligados **antes** de definir a temperatura. O `VentoBaumn` lança exceção se `definirTemperatura()` for chamado com o aparelho desligado.

---

## Restrições dos Dispositivos

| Dispositivo | Restrição | Exceção Lançada |
|------------|-----------|----------------|
| `ArCondicionadoVentoBaumn` | `definirTemperatura()` com aparelho desligado | `IllegalArgumentException` |
| `ArCondicionadoVentoBaumn` | Temperatura fora de [15, 35] | `IllegalArgumentException` |
| `ArCondicionadoGellaKaza` | `aumentarTemperatura()` com temperatura em 35°C | `IllegalArgumentException` |
| `ArCondicionadoGellaKaza` | `diminuirTemperatura()` com temperatura em 15°C | `IllegalArgumentException` |
| `PersianaNatLight` | `subirPalheta()` com palhetas fechadas | `Exception` (checada) |
| `PersianaNatLight` | `fecharPalheta()` com persiana erguida | `Exception` (checada) |
| `LampadaPhellipes` | `setIntensidade()` fora de [0, 100] | `IllegalArgumentException` |

---

## Object Calisthenics Aplicados

| Regra | Aplicação no Código |
|-------|---------------------|
| **Coleções de primeira classe** | `Lampadas`, `Persianas` e `ArsCondicionados` encapsulam `List<T>`. Nenhuma lista fica exposta diretamente em outras classes. |
| **Um nível de indentação por método** | Métodos nas coleções usam `forEach`. Nos adaptadores, blocos try-catch foram extraídos para métodos privados (`tentarSubirPalheta`, `tentarFecharPalheta`). |
| **Sem else** | Nenhum bloco `else` no código de produção. |
| **Sem números mágicos** | Constantes nomeadas: `INTENSIDADE_LIGADA = 100`, `INTENSIDADE_DESLIGADA = 0`, `TEMPERATURA_TRABALHO = 25`. |
| **Métodos pequenos com responsabilidade única** | Cada método faz exatamente uma coisa: `ligarTodas()`, `fecharTodas()`, `tentarSubirPalheta()`. |

---

## Como Executar

**Compilar:**
```bash
mvn compile
```

**Executar testes:**
```bash
mvn test
```

**Executar demonstração:**
```bash
mvn exec:java -Dexec.mainClass="org.example.Main"
```
