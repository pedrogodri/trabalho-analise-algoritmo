package org.example.models;

import br.furb.analise.algoritmos.ArCondicionadoGellaKaza;
import br.furb.analise.algoritmos.ArCondicionadoVentoBaumn;
import org.example.adaptadores.arcondicionado.ArCondicionadoGellaKazaAdaptador;
import org.example.adaptadores.arcondicionado.ArCondicionadoVentoBaumnAdaptador;
import org.example.excecoes.DispositivoNuloException;
import org.example.values.Temperatura;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ArsCondicionadosTest {

    private ArCondicionadoVentoBaumn ventoBaumnNativo;
    private ArCondicionadoGellaKaza gellaKazaNativa;
    private ArsCondicionados arsCondicionados;

    @BeforeEach
    void setUp() {
        ventoBaumnNativo = new ArCondicionadoVentoBaumn();
        gellaKazaNativa = new ArCondicionadoGellaKaza();
        arsCondicionados = new ArsCondicionados(List.of(
                new ArCondicionadoVentoBaumnAdaptador(ventoBaumnNativo),
                new ArCondicionadoGellaKazaAdaptador(gellaKazaNativa)
        ));
    }

    @Test
    void ligarTodos_deveLigarTodosOsArs() {
        arsCondicionados.ligarTodos();
        assertTrue(ventoBaumnNativo.estaLigado());
        assertTrue(gellaKazaNativa.estaLigado());
    }

    @Test
    void desligarTodos_deveDesligarTodosOsArs() {
        arsCondicionados.ligarTodos();
        arsCondicionados.desligarTodos();
        assertFalse(ventoBaumnNativo.estaLigado());
        assertFalse(gellaKazaNativa.estaLigado());
    }

    @Test
    void definirTemperaturaEmTodos_deveDefinirAMesmaTemperaturaEmTodos() {
        arsCondicionados.ligarTodos();
        arsCondicionados.definirTemperaturaEmTodos(new Temperatura(25));
        assertEquals(25, ventoBaumnNativo.getTemperatura());
        assertEquals(25, gellaKazaNativa.getTemperatura());
    }

    @Test
    void construtor_comListaNula_deveLancarNullPointerException() {
        assertThrows(DispositivoNuloException.class, () -> new ArsCondicionados(null));
    }
}
