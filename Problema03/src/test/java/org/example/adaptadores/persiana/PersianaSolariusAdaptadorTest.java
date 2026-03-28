package org.example.adaptadores.persiana;

import br.furb.analise.algoritmos.PersianaSolarius;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersianaSolariusAdaptadorTest {

    private PersianaSolarius persianaNativa;
    private PersianaSolariusAdaptador adaptador;

    @BeforeEach
    void setUp() {
        persianaNativa = new PersianaSolarius();
        adaptador = new PersianaSolariusAdaptador(persianaNativa);
    }

    @Test
    void abrir_deveSubirAPersiana() {
        adaptador.abrir();
        assertTrue(persianaNativa.estaAberta());
    }

    @Test
    void fechar_deveDescerAPersiana() {
        adaptador.abrir();
        adaptador.fechar();
        assertFalse(persianaNativa.estaAberta());
    }

    @Test
    void abrirEFechar_deveAlternarEstado() {
        adaptador.abrir();
        assertTrue(persianaNativa.estaAberta());

        adaptador.fechar();
        assertFalse(persianaNativa.estaAberta());

        adaptador.abrir();
        assertTrue(persianaNativa.estaAberta());
    }
}
