package org.example.simulacao;

import org.example.dominio.acao.PrecoAcao;
import org.example.dominio.acao.QuantidadeAcao;
import org.example.dominio.empresa.Empresa;
import org.example.dominio.empresa.ListaDeEmpresas;
import org.example.dominio.investidor.Investidor;
import org.example.dominio.investidor.ListaDeInvestidores;
import org.example.dominio.ordem.OrdemDeCompra;
import org.example.dominio.ordem.OrdemDeVenda;

/**
 * Orquestra o cenário de simulação do mercado de ações com dados fictícios.
 *
 * <p>Cenário:
 * <ol>
 *   <li>Dave vende 100 PetrobrasSA @ R$30 → sem contraparte ainda.</li>
 *   <li>Alice compra 100 PetrobrasSA @ R$35 → match! Preço estabelecido em R$35.
 *       Alice e Bob são notificados.</li>
 *   <li>Bob vende 50 PetrobrasSA @ R$38 → sem contraparte.</li>
 *   <li>Alice compra 50 PetrobrasSA @ R$38 → match! Preço atualizado para R$38.
 *       Alice e Bob são notificados.</li>
 *   <li>Carol compra 200 ValeSA @ R$20 → sem contraparte ainda.</li>
 *   <li>Dave vende 200 ValeSA @ R$18 → match! Preço estabelecido em R$20.
 *       Carol é notificada.</li>
 *   <li>Exibe portfólios e preços finais.</li>
 * </ol>
 * </p>
 */
public final class SimulacaoDoMercado {

    private final Empresa petrobrasSA;
    private final Empresa valeSA;
    private final Investidor alice;
    private final Investidor bob;
    private final Investidor carol;
    private final Investidor dave;
    private final ListaDeEmpresas empresas;
    private final ListaDeInvestidores investidores;

    public SimulacaoDoMercado() {
        petrobrasSA = FabricaDeDadosMock.criarPetrobrasSA();
        valeSA = FabricaDeDadosMock.criarValeSA();
        alice = FabricaDeDadosMock.criarAlice();
        bob = FabricaDeDadosMock.criarBob();
        carol = FabricaDeDadosMock.criarCarol();
        dave = FabricaDeDadosMock.criarDave();

        empresas = new ListaDeEmpresas();
        empresas.adicionar(petrobrasSA);
        empresas.adicionar(valeSA);

        investidores = new ListaDeInvestidores();
        investidores.adicionar(alice);
        investidores.adicionar(bob);
        investidores.adicionar(carol);
        investidores.adicionar(dave);
    }

    public void executar() {
        configurarInscricoes();
        configurarCarteiraInicialDeDave();

        System.out.println("\n========== INICIO DA SIMULACAO ==========\n");

        executarPassoUm();
        executarPassoDois();
        executarPassoTres();
        executarPassoQuatro();
        executarPassoCinco();
        executarPassoSeis();

        exibirResultadosFinais();
    }

    private void configurarInscricoes() {
        // Alice e Bob acompanham PetrobrasSA em tempo real
        petrobrasSA.inscrever(alice);
        petrobrasSA.inscrever(bob);

        // Carol acompanha ValeSA em tempo real
        valeSA.inscrever(carol);
    }

    /**
     * Dave já possuía ações antes da simulação começar (carteira pré-existente).
     * Isso representa ações adquiridas fora do escopo desta simulação.
     */
    private void configurarCarteiraInicialDeDave() {
        dave.receberAcoes(petrobrasSA.obterNome(), new QuantidadeAcao(100));
        dave.receberAcoes(valeSA.obterNome(), new QuantidadeAcao(200));
        bob.receberAcoes(petrobrasSA.obterNome(), new QuantidadeAcao(50));
    }

    private void executarPassoUm() {
        System.out.println("--- Passo 1: Dave registra venda de 100 PetrobrasSA @ R$30 ---");
        OrdemDeVenda ordemDave = new OrdemDeVenda(dave,
            new PrecoAcao("30.00"), new QuantidadeAcao(100));
        petrobrasSA.registrarOrdemDeVenda(ordemDave);
        System.out.println();
    }

    private void executarPassoDois() {
        System.out.println("--- Passo 2: Alice registra compra de 100 PetrobrasSA @ R$35 ---");
        OrdemDeCompra ordemAlice = new OrdemDeCompra(alice,
            new PrecoAcao("35.00"), new QuantidadeAcao(100));
        petrobrasSA.registrarOrdemDeCompra(ordemAlice);
        System.out.println();
    }

    private void executarPassoTres() {
        System.out.println("--- Passo 3: Bob registra venda de 50 PetrobrasSA @ R$38 ---");
        OrdemDeVenda ordemBob = new OrdemDeVenda(bob,
            new PrecoAcao("38.00"), new QuantidadeAcao(50));
        petrobrasSA.registrarOrdemDeVenda(ordemBob);
        System.out.println();
    }

    private void executarPassoQuatro() {
        System.out.println("--- Passo 4: Alice registra compra de 50 PetrobrasSA @ R$38 ---");
        OrdemDeCompra ordemAlice = new OrdemDeCompra(alice,
            new PrecoAcao("38.00"), new QuantidadeAcao(50));
        petrobrasSA.registrarOrdemDeCompra(ordemAlice);
        System.out.println();
    }

    private void executarPassoCinco() {
        System.out.println("--- Passo 5: Carol registra compra de 200 ValeSA @ R$20 ---");
        OrdemDeCompra ordemCarol = new OrdemDeCompra(carol,
            new PrecoAcao("20.00"), new QuantidadeAcao(200));
        valeSA.registrarOrdemDeCompra(ordemCarol);
        System.out.println();
    }

    private void executarPassoSeis() {
        System.out.println("--- Passo 6: Dave registra venda de 200 ValeSA @ R$18 ---");
        OrdemDeVenda ordemDave = new OrdemDeVenda(dave,
            new PrecoAcao("18.00"), new QuantidadeAcao(200));
        valeSA.registrarOrdemDeVenda(ordemDave);
        System.out.println();
    }

    private void exibirResultadosFinais() {
        System.out.println("========== CARTEIRAS FINAIS ==========");
        investidores.exibirCarteiras();
        System.out.println();
        System.out.println("========== PRECOS DAS EMPRESAS ==========");
        empresas.exibirPrecos();
        System.out.println();
        System.out.println("========== FIM DA SIMULACAO ==========");
    }
}
