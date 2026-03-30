package org.example.modos;

import org.example.models.ArsCondicionados;
import org.example.models.Lampadas;
import org.example.models.Persianas;
import org.example.values.Temperatura;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class ModoTrabalhoTest {

    private Lampadas lampadas;
    private Persianas persianas;
    private ArsCondicionados arsCondicionados;
    private ModoTrabalho modoTrabalho;

    @BeforeEach
    void setUp() {
        lampadas = mock(Lampadas.class);
        persianas = mock(Persianas.class);
        arsCondicionados = mock(ArsCondicionados.class);
        modoTrabalho = new ModoTrabalho();
    }

    @Test
    void ativar_deveLigarTodasAsLampadas() {
        modoTrabalho.ativar(lampadas, persianas, arsCondicionados);
        verify(lampadas).ligarTodas();
    }

    @Test
    void ativar_deveAbrirTodasAsPersianas() {
        modoTrabalho.ativar(lampadas, persianas, arsCondicionados);
        verify(persianas).abrirTodas();
    }

    @Test
    void ativar_deveLigarTodosOsArs() {
        modoTrabalho.ativar(lampadas, persianas, arsCondicionados);
        verify(arsCondicionados).ligarTodos();
    }

    @Test
    void ativar_deveDefinirTemperatura25EmTodosOsArs() {
        modoTrabalho.ativar(lampadas, persianas, arsCondicionados);
        verify(arsCondicionados).definirTemperaturaEmTodos(new Temperatura(25));
    }

    @Test
    void ativar_develigarArsAntesDeDefinirTemperatura() {
        // verifica a ordem: ligarTodos ANTES de definirTemperaturaEmTodos
        var ordem = inOrder(arsCondicionados);
        modoTrabalho.ativar(lampadas, persianas, arsCondicionados);
        ordem.verify(arsCondicionados).ligarTodos();
        ordem.verify(arsCondicionados).definirTemperaturaEmTodos(any(Temperatura.class));
    }
}
