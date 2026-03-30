package org.example.modos;

import org.example.models.ArsCondicionados;
import org.example.models.Lampadas;
import org.example.models.Persianas;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

class ModoSonoTest {

    private Lampadas lampadas;
    private Persianas persianas;
    private ArsCondicionados arsCondicionados;
    private ModoSono modoSono;

    @BeforeEach
    void setUp() {
        lampadas = mock(Lampadas.class);
        persianas = mock(Persianas.class);
        arsCondicionados = mock(ArsCondicionados.class);
        modoSono = new ModoSono();
    }

    @Test
    void ativar_deveDesligarTodasAsLampadas() {
        modoSono.ativar(lampadas, persianas, arsCondicionados);
        verify(lampadas).desligarTodas();
    }

    @Test
    void ativar_deveFecharTodasAsPersianas() {
        modoSono.ativar(lampadas, persianas, arsCondicionados);
        verify(persianas).fecharTodas();
    }

    @Test
    void ativar_deveDesligarTodosOsArs() {
        modoSono.ativar(lampadas, persianas, arsCondicionados);
        verify(arsCondicionados).desligarTodos();
    }

    @Test
    void ativar_naoDeveLigarOsArs() {
        modoSono.ativar(lampadas, persianas, arsCondicionados);
        verify(arsCondicionados, never()).ligarTodos();
    }

    @Test
    void ativar_naoDeveDefinirTemperatura() {
        modoSono.ativar(lampadas, persianas, arsCondicionados);
        verify(arsCondicionados, never()).definirTemperaturaEmTodos(any());
    }
}
