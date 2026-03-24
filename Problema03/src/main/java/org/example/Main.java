package org.example;

import br.furb.analise.algoritmos.ArCondicionadoGellaKaza;
import br.furb.analise.algoritmos.ArCondicionadoVentoBaumn;
import br.furb.analise.algoritmos.LampadaPhellipes;
import br.furb.analise.algoritmos.LampadaShoyuMi;
import br.furb.analise.algoritmos.PersianaNatLight;
import br.furb.analise.algoritmos.PersianaSolarius;

public class Main {
    public static void main(String[] args) throws Exception {

        // Lâmpada ShoyuMi - ligar e desligar
        System.out.println("=== Lâmpada ShoyuMi ===");
        LampadaShoyuMi shoyuMi = new LampadaShoyuMi();
        shoyuMi.ligar();
        System.out.println("ShoyuMi ligada: " + shoyuMi.estaLigada());
        shoyuMi.desligar();
        System.out.println("ShoyuMi ligada: " + shoyuMi.estaLigada());
        
        // Lâmpada Phellipes - controle de intensidade 0-100
        System.out.println("\n=== Lâmpada Phellipes ===");
        LampadaPhellipes phellipes = new LampadaPhellipes();
        phellipes.setIntensidade(75);
        System.out.println("Phellipes intensidade: " + phellipes.getIntensidade());
        phellipes.setIntensidade(0);
        System.out.println("Phellipes intensidade: " + phellipes.getIntensidade());

        // Persiana Solarius - subir e descer
        System.out.println("\n=== Persiana Solarius ===");
        PersianaSolarius solarius = new PersianaSolarius();
        solarius.subirPersiana();
        System.out.println("Solarius aberta: " + solarius.estaAberta());
        solarius.descerPersiana();
        System.out.println("Solarius aberta: " + solarius.estaAberta());

        // Persiana NatLight - palhetas e persiana
        System.out.println("\n=== Persiana NatLight ===");
        PersianaNatLight natLight = new PersianaNatLight();
        natLight.abrirPalheta();
        System.out.println("Palheta aberta: " + natLight.estaPalhetaAberta());
        natLight.subirPalheta(); // palheta precisa estar aberta
        System.out.println("Palheta erguida: " + natLight.estaPalhetaErguida());
        natLight.descerPalheta();
        System.out.println("Palheta erguida: " + natLight.estaPalhetaErguida());
        natLight.fecharPalheta(); // persiana precisa estar descida
        System.out.println("Palheta aberta: " + natLight.estaPalhetaAberta());

        // Ar Condicionado VentoBaumn - inicia em 24
        System.out.println("\n=== Ar Condicionado VentoBaumn ===");
        ArCondicionadoVentoBaumn ventoBaumn = new ArCondicionadoVentoBaumn();
        ventoBaumn.ligar();
        System.out.println("VentoBaumn temperatura inicial: " + ventoBaumn.getTemperatura());
        ventoBaumn.definirTemperatura(20);
        System.out.println("VentoBaumn temperatura: " + ventoBaumn.getTemperatura());
        ventoBaumn.desligar();

        // Ar Condicionado GellaKaza - inicia em 28
        System.out.println("\n=== Ar Condicionado GellaKaza ===");
        ArCondicionadoGellaKaza gellaKaza = new ArCondicionadoGellaKaza();
        gellaKaza.ativar();
        System.out.println("GellaKaza temperatura inicial: " + gellaKaza.getTemperatura());
        gellaKaza.aumentarTemperatura();
        System.out.println("GellaKaza temperatura após aumentar: " + gellaKaza.getTemperatura());
        gellaKaza.diminuirTemperatura();
        System.out.println("GellaKaza temperatura após diminuir: " + gellaKaza.getTemperatura());
        gellaKaza.desativar();
        System.out.println("GellaKaza ligado: " + gellaKaza.estaLigado());
    }
}