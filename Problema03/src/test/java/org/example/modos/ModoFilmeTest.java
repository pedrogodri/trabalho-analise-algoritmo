package org.example.modos;

import org.example.models.ArsCondicionados;
import org.example.models.Lampadas;
import org.example.models.Persianas;
import org.example.values.Temperatura;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class ModoFilmeTest {

    private Lampadas lampadas;
    private Persianas persianas;
    private ArsCondicionados arsCondicionados;
    private ModoFilme modoFilme;

    @BeforeEach
    void setUp() {
        lampadas = mock(Lampadas.class);
        persianas = mock(Persianas.class);
        arsCondicionados = mock(ArsCondicionados.class);
        modoFilme = new ModoFilme();
    }

    @Test
    void ativar_deveDesligarTodasAsLampadas() {
        modoFilme.ativar(lampadas, persianas, arsCondicionados);
        verify(lampadas).desligarTodas();
    }

    @Test
    void ativar_deveFecharTodasAsPersianas() {
        modoFilme.ativar(lampadas, persianas, arsCondicionados);
        verify(persianas).fecharTodas();
    }

    @Test
    void ativar_deveLigarTodosOsArs() {
        modoFilme.ativar(lampadas, persianas, arsCondicionados);
        verify(arsCondicionados).ligarTodos();
    }

    @Test
    void ativar_deveDefinirTemperatura22EmTodosOsArs() {
        modoFilme.ativar(lampadas, persianas, arsCondicionados);
        verify(arsCondicionados).definirTemperaturaEmTodos(new Temperatura(22));
    }
}
