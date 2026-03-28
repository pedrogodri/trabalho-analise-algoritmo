package org.example.adaptadores.arcondicionado;

import br.furb.analise.algoritmos.ArCondicionadoGellaKaza;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArCondicionadoGellaKazaAdaptadorTest {

    private ArCondicionadoGellaKaza arNativo;
    private ArCondicionadoGellaKazaAdaptador adaptador;

    @BeforeEach
    void setUp() {
        arNativo = new ArCondicionadoGellaKaza();
        adaptador = new ArCondicionadoGellaKazaAdaptador(arNativo);
    }

    @Test
    void ligar_deveLigarOAparelho() {
        adaptador.ligar();
        assertTrue(arNativo.estaLigado());
    }

    @Test
    void desligar_deveDesligarOAparelho() {
        adaptador.ligar();
        adaptador.desligar();
        assertFalse(arNativo.estaLigado());
    }

    @Test
    void temperaturaInicial_deveSer28() {
        assertEquals(28, arNativo.getTemperatura());
    }

    @Test
    void definirTemperatura_acimaDaAtual_deveAumentarAteOAlvo() {
        adaptador.definirTemperatura(30);
        assertEquals(30, arNativo.getTemperatura());
    }

    @Test
    void definirTemperatura_abaixoDaAtual_deveDiminuirAteOAlvo() {
        adaptador.definirTemperatura(25);
        assertEquals(25, arNativo.getTemperatura());
    }

    @Test
    void definirTemperatura_igualAAtual_naoDeveAlterarTemperatura() {
        adaptador.definirTemperatura(28);
        assertEquals(28, arNativo.getTemperatura());
    }

    @Test
    void definirTemperatura_noLimiteMaximo_deveDefinir35() {
        adaptador.definirTemperatura(35);
        assertEquals(35, arNativo.getTemperatura());
    }

    @Test
    void definirTemperatura_noLimiteMinimo_deveDefinir15() {
        adaptador.definirTemperatura(15);
        assertEquals(15, arNativo.getTemperatura());
    }

    @Test
    void aumentarTemperatura_deveIncrementarEm1() {
        int temperaturaAntes = arNativo.getTemperatura();
        adaptador.aumentarTemperatura();
        assertEquals(temperaturaAntes + 1, arNativo.getTemperatura());
    }

    @Test
    void diminuirTemperatura_deveDecrementarEm1() {
        int temperaturaAntes = arNativo.getTemperatura();
        adaptador.diminuirTemperatura();
        assertEquals(temperaturaAntes - 1, arNativo.getTemperatura());
    }
}
