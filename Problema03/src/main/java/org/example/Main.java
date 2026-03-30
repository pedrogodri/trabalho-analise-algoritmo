package org.example;

import br.furb.analise.algoritmos.*;
import org.example.adaptadores.arcondicionado.ArCondicionadoGellaKazaAdaptador;
import org.example.adaptadores.arcondicionado.ArCondicionadoVentoBaumnAdaptador;
import org.example.adaptadores.lampada.LampadaPhellipesAdaptador;
import org.example.adaptadores.lampada.LampadaShoyuMiAdaptador;
import org.example.adaptadores.persiana.PersianaNatLightAdaptador;
import org.example.adaptadores.persiana.PersianaSolariusAdaptador;
import org.example.casa.CasaInteligente;
import org.example.excecoes.DispositivoNuloException;
import org.example.excecoes.FalhaDispositivoException;
import org.example.excecoes.ModoInvalidoException;
import org.example.models.ArsCondicionados;
import org.example.models.Lampadas;
import org.example.models.Persianas;
import org.example.modos.ModoFilme;
import org.example.modos.ModoSono;
import org.example.modos.ModoTrabalho;

import java.util.List;

/**
 * Ponto de entrada da aplicação Casa Inteligente IoT.
 *
 * <p>Demonstra o funcionamento dos padrões <b>Adapter</b>, <b>Facade</b> e <b>Strategy</b>
 * na orquestração de dispositivos de múltiplos fabricantes (ShoyuMi, Phellipes,
 * Solarius, NatLight, VentoBaumn, GellaKaza).</p>
 *
 * <p>Cada bloco de operação é isolado com tratamento de exceções individuais,
 * garantindo que uma falha pontual não interrompa o restante da demonstração.</p>
 */
public class Main {

    private static final String SEPARADOR = "=".repeat(60);
    private static final String SEPARADOR_SECAO = "-".repeat(60);

    public static void main(String[] args) {
        cabecalho();

        CasaInteligente casa;
        try {
            casa = construirCasa();
        } catch (DispositivoNuloException e) {
            System.err.println("[ERRO CRÍTICO] Falha ao inicializar a casa: " + e.getMessage());
            System.err.println("Verifique se todos os dispositivos foram fornecidos corretamente.");
            return;
        } catch (Exception e) {
            System.err.println("[ERRO CRÍTICO] Erro inesperado ao construir a casa: " + e.getMessage());
            System.err.println("Tipo: " + e.getClass().getSimpleName());
            if (e.getCause() != null) {
                System.err.println("Causa: " + e.getCause().getMessage());
            }
            return;
        }

        executarModoTrabalho(casa);
        executarModoFilme(casa);
        executarModoSono(casa);
        executarControleIndividual(casa);

        rodape();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Execução dos modos
    // ─────────────────────────────────────────────────────────────────────────

    private static void executarModoTrabalho(CasaInteligente casa) {
        secao("MODO TRABALHO", "Luzes ligadas | Persianas abertas | ACs a 25°C");
        try {
            casa.ativarModo(new ModoTrabalho());
            sucesso("Modo Trabalho ativado com sucesso!");
        } catch (ModoInvalidoException e) {
            System.err.println("[ERRO] Modo inválido: " + e.getMessage());
        } catch (FalhaDispositivoException e) {
            System.err.println("[ERRO] Falha em dispositivo durante Modo Trabalho: " + e.getMessage());
            System.err.println("       Causa: " + (e.getCause() != null ? e.getCause().getMessage() : "desconhecida"));
        } catch (Exception e) {
            System.err.println("[ERRO INESPERADO] " + e.getMessage());
        }
    }

    private static void executarModoFilme(CasaInteligente casa) {
        secao("MODO FILME", "Luzes apagadas | Persianas fechadas | ACs a 22°C");
        try {
            casa.ativarModo(new ModoFilme());
            sucesso("Modo Filme ativado com sucesso!");
        } catch (ModoInvalidoException e) {
            System.err.println("[ERRO] Modo inválido: " + e.getMessage());
        } catch (FalhaDispositivoException e) {
            System.err.println("[ERRO] Falha em dispositivo durante Modo Filme: " + e.getMessage());
            System.err.println("       Causa: " + (e.getCause() != null ? e.getCause().getMessage() : "desconhecida"));
        } catch (Exception e) {
            System.err.println("[ERRO INESPERADO] " + e.getMessage());
        }
    }

    private static void executarModoSono(CasaInteligente casa) {
        secao("MODO SONO", "Luzes apagadas | Persianas fechadas | ACs desligados");
        try {
            casa.ativarModo(new ModoSono());
            sucesso("Modo Sono ativado com sucesso!");
        } catch (ModoInvalidoException e) {
            System.err.println("[ERRO] Modo inválido: " + e.getMessage());
        } catch (FalhaDispositivoException e) {
            System.err.println("[ERRO] Falha em dispositivo durante Modo Sono: " + e.getMessage());
            System.err.println("       Causa: " + (e.getCause() != null ? e.getCause().getMessage() : "desconhecida"));
        } catch (Exception e) {
            System.err.println("[ERRO INESPERADO] " + e.getMessage());
        }
    }

    private static void executarControleIndividual(CasaInteligente casa) {
        secao("CONTROLE INDIVIDUAL", "Operações diretas sobre grupos de dispositivos");

        try {
            System.out.println("  → Ligando apenas as lâmpadas...");
            casa.ligarLampadas();
            sucesso("Lâmpadas ligadas.");
        } catch (FalhaDispositivoException e) {
            System.err.println("[ERRO] Falha ao ligar lâmpadas: " + e.getMessage());
        }

        try {
            System.out.println("  → Fechando apenas as persianas...");
            casa.fecharPersianas();
            sucesso("Persianas fechadas.");
        } catch (FalhaDispositivoException e) {
            System.err.println("[ERRO] Falha ao fechar persianas: " + e.getMessage());
        }

        try {
            System.out.println("  → Ligando apenas os ar-condicionados...");
            casa.ligarArsCondicionados();
            sucesso("Ar-condicionados ligados.");
        } catch (FalhaDispositivoException e) {
            System.err.println("[ERRO] Falha ao ligar ar-condicionados: " + e.getMessage());
        }

        try {
            System.out.println("  → Desligando apenas os ar-condicionados...");
            casa.desligarArsCondicionados();
            sucesso("Ar-condicionados desligados.");
        } catch (FalhaDispositivoException e) {
            System.err.println("[ERRO] Falha ao desligar ar-condicionados: " + e.getMessage());
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Construção da casa
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Constrói a {@link CasaInteligente} com todos os dispositivos de múltiplos
     * fabricantes adaptados para as interfaces universais.
     *
     * <p>Clean Code: extração de método com nome revelador de intenção,
     * reduzindo o tamanho de {@code main} e tornando a composição legível.</p>
     *
     * @return instância configurada da casa inteligente
     * @throws DispositivoNuloException se algum dispositivo for nulo
     */
    private static CasaInteligente construirCasa() {
        System.out.println("[Main] Inicializando dispositivos da casa...");

        var lampadas = new Lampadas(List.of(
                new LampadaShoyuMiAdaptador(new LampadaShoyuMi()),
                new LampadaPhellipesAdaptador(new LampadaPhellipes())
        ));

        var persianas = new Persianas(List.of(
                new PersianaSolariusAdaptador(new PersianaSolarius()),
                new PersianaNatLightAdaptador(new PersianaNatLight())
        ));

        var arsCondicionados = new ArsCondicionados(List.of(
                new ArCondicionadoVentoBaumnAdaptador(new ArCondicionadoVentoBaumn()),
                new ArCondicionadoGellaKazaAdaptador(new ArCondicionadoGellaKaza())
        ));

        return new CasaInteligente(lampadas, persianas, arsCondicionados);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Utilitários de apresentação
    // ─────────────────────────────────────────────────────────────────────────

    private static void cabecalho() {
        System.out.println(SEPARADOR);
        System.out.println("       SISTEMA DE CASA INTELIGENTE IoT");
        System.out.println("  Padrões: Adapter + Facade + Strategy");
        System.out.println(SEPARADOR);
        System.out.println();
    }

    private static void rodape() {
        System.out.println();
        System.out.println(SEPARADOR);
        System.out.println("  Demonstração concluída. Todos os modos foram executados.");
        System.out.println(SEPARADOR);
    }

    private static void secao(String titulo, String descricao) {
        System.out.println();
        System.out.println(SEPARADOR_SECAO);
        System.out.println("  " + titulo);
        System.out.println("  " + descricao);
        System.out.println(SEPARADOR_SECAO);
    }

    private static void sucesso(String mensagem) {
        System.out.println("  ✔ " + mensagem);
    }
}
