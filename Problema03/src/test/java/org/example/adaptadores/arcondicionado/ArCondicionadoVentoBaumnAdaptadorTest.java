package org.example.adaptadores.arcondicionado;

import br.furb.analise.algoritmos.ArCondicionadoVentoBaumn;
import org.example.excecoes.FalhaDispositivoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArCondicionadoVentoBaumnAdaptadorTest {

    private ArCondicionadoVentoBaumn arNativo;
    private ArCondicionadoVentoBaumnAdaptador adaptador;

    @BeforeEach
    void setUp() {
        arNativo = new ArCondicionadoVentoBaumn();
        adaptador = new ArCondicionadoVentoBaumnAdaptador(arNativo);
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
    void definirTemperatura_deveAlterarATemperatura() {
        adaptador.ligar();
        adaptador.definirTemperatura(20);
        assertEquals(20, arNativo.getTemperatura());
    }

    @Test
    void definirTemperatura_semEstarLigado_deveLancarExcecao() {
        assertThrows(FalhaDispositivoException.class, () -> adaptador.definirTemperatura(20));
    }

    @Test
    void aumentarTemperatura_deveIncrementarEm1() {
        adaptador.ligar();
        int temperaturaAntes = arNativo.getTemperatura();
        adaptador.aumentarTemperatura();
        assertEquals(temperaturaAntes + 1, arNativo.getTemperatura());
    }

    @Test
    void diminuirTemperatura_deveDecrementarEm1() {
        adaptador.ligar();
        int temperaturaAntes = arNativo.getTemperatura();
        adaptador.diminuirTemperatura();
        assertEquals(temperaturaAntes - 1, arNativo.getTemperatura());
    }

    @Test
    void temperaturaInicial_deveSer24() {
        assertEquals(24, arNativo.getTemperatura());
    }
}
