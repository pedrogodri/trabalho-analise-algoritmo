package org.example;

import br.furb.analise.algoritmos.*;
import org.example.adaptadores.arcondicionado.ArCondicionadoGellaKazaAdaptador;
import org.example.adaptadores.arcondicionado.ArCondicionadoVentoBaumnAdaptador;
import org.example.adaptadores.lampada.LampadaPhellipesAdaptador;
import org.example.adaptadores.lampada.LampadaShoyuMiAdaptador;
import org.example.adaptadores.persiana.PersianaNatLightAdaptador;
import org.example.adaptadores.persiana.PersianaSolariusAdaptador;
import org.example.casa.CasaInteligente;
import org.example.models.ArsCondicionados;
import org.example.models.Lampadas;
import org.example.models.Persianas;
import org.example.modos.ModoSono;
import org.example.modos.ModoTrabalho;

import java.util.List;

public class Main {

    public static void main(String[] args) {
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

        var casa = new CasaInteligente(lampadas, persianas, arsCondicionados);

        System.out.println("=== Ativando Modo Trabalho ===");
        casa.ativarModo(new ModoTrabalho());
        System.out.println("Luzes ligadas, persianas abertas, ACs a 25°C.");

        System.out.println("\n=== Ativando Modo Sono ===");
        casa.ativarModo(new ModoSono());
        System.out.println("Luzes desligadas, persianas fechadas, ACs desligados.");

        System.out.println("\n=== Controle individual ===");
        casa.ligarLampadas();
        System.out.println("Apenas as luzes foram ligadas.");
        casa.fecharPersianas();
        System.out.println("Apenas as persianas foram fechadas.");
    }
}
