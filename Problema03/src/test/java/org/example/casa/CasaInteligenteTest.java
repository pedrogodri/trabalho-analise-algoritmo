package org.example.casa;

import br.furb.analise.algoritmos.*;
import org.example.adaptadores.arcondicionado.ArCondicionadoGellaKazaAdaptador;
import org.example.adaptadores.arcondicionado.ArCondicionadoVentoBaumnAdaptador;
import org.example.adaptadores.lampada.LampadaPhellipesAdaptador;
import org.example.adaptadores.lampada.LampadaShoyuMiAdaptador;
import org.example.adaptadores.persiana.PersianaNatLightAdaptador;
import org.example.adaptadores.persiana.PersianaSolariusAdaptador;
import org.example.models.ArsCondicionados;
import org.example.models.Lampadas;
import org.example.models.Persianas;
import org.example.modos.ModoSono;
import org.example.modos.ModoTrabalho;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CasaInteligenteTest {

    private LampadaShoyuMi shoyuMiNativa;
    private LampadaPhellipes phellipesNativa;
    private PersianaSolarius solariusNativa;
    private PersianaNatLight natLightNativa;
    private ArCondicionadoVentoBaumn ventoBaumnNativo;
    private ArCondicionadoGellaKaza gellaKazaNativa;
    private CasaInteligente casa;

    @BeforeEach
    void setUp() {
        shoyuMiNativa = new LampadaShoyuMi();
        phellipesNativa = new LampadaPhellipes();
        solariusNativa = new PersianaSolarius();
        natLightNativa = new PersianaNatLight();
        ventoBaumnNativo = new ArCondicionadoVentoBaumn();
        gellaKazaNativa = new ArCondicionadoGellaKaza();

        casa = new CasaInteligente(
                new Lampadas(List.of(
                        new LampadaShoyuMiAdaptador(shoyuMiNativa),
                        new LampadaPhellipesAdaptador(phellipesNativa)
                )),
                new Persianas(List.of(
                        new PersianaSolariusAdaptador(solariusNativa),
                        new PersianaNatLightAdaptador(natLightNativa)
                )),
                new ArsCondicionados(List.of(
                        new ArCondicionadoVentoBaumnAdaptador(ventoBaumnNativo),
                        new ArCondicionadoGellaKazaAdaptador(gellaKazaNativa)
                ))
        );
    }

    @Test
    void modoTrabalho_deveLigarLuzes() {
        casa.ativarModo(new ModoTrabalho());
        assertTrue(shoyuMiNativa.estaLigada());
        assertEquals(100, phellipesNativa.getIntensidade());
    }

    @Test
    void modoTrabalho_deveAbrirPersianas() {
        casa.ativarModo(new ModoSono());
        casa.ativarModo(new ModoTrabalho());
        assertTrue(solariusNativa.estaAberta());
        assertTrue(natLightNativa.estaPalhetaErguida());
    }

    @Test
    void modoTrabalho_deveDefinirTemperatura25() {
        casa.ativarModo(new ModoTrabalho());
        assertEquals(25, ventoBaumnNativo.getTemperatura());
        assertEquals(25, gellaKazaNativa.getTemperatura());
    }

    @Test
    void modoTrabalho_deveLigarOsArs() {
        casa.ativarModo(new ModoTrabalho());
        assertTrue(ventoBaumnNativo.estaLigado());
        assertTrue(gellaKazaNativa.estaLigado());
    }

    @Test
    void modoSono_deveDesligarLuzes() {
        casa.ativarModo(new ModoTrabalho());
        casa.ativarModo(new ModoSono());
        assertFalse(shoyuMiNativa.estaLigada());
        assertEquals(0, phellipesNativa.getIntensidade());
    }

    @Test
    void modoSono_deveFecharPersianas() {
        casa.ativarModo(new ModoSono());
        assertFalse(solariusNativa.estaAberta());
        assertFalse(natLightNativa.estaPalhetaErguida());
        assertFalse(natLightNativa.estaPalhetaAberta());
    }

    @Test
    void modoSono_deveDesligarOsArs() {
        casa.ativarModo(new ModoTrabalho());
        casa.ativarModo(new ModoSono());
        assertFalse(ventoBaumnNativo.estaLigado());
        assertFalse(gellaKazaNativa.estaLigado());
    }

    @Test
    void modoTrabalhoSeguidoDeSono_deveRestaurarEstadoDeRepouso() {
        casa.ativarModo(new ModoTrabalho());
        casa.ativarModo(new ModoSono());
        assertFalse(shoyuMiNativa.estaLigada());
        assertFalse(ventoBaumnNativo.estaLigado());
        assertFalse(gellaKazaNativa.estaLigado());
        assertFalse(solariusNativa.estaAberta());
    }
}
