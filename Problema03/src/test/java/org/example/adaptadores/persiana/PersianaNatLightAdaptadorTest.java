package org.example.adaptadores.persiana;

import br.furb.analise.algoritmos.PersianaNatLight;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersianaNatLightAdaptadorTest {

    private PersianaNatLight persianaNativa;
    private PersianaNatLightAdaptador adaptador;

    @BeforeEach
    void setUp() {
        persianaNativa = new PersianaNatLight();
        adaptador = new PersianaNatLightAdaptador(persianaNativa);
    }

    @Test
    void abrir_deveErguerAPersiana() {
        adaptador.fechar();
        adaptador.abrir();
        assertTrue(persianaNativa.estaPalhetaErguida());
        assertTrue(persianaNativa.estaPalhetaAberta());
    }

    @Test
    void fechar_deveDescerEFecharAsPalhetas() {
        adaptador.fechar();
        assertFalse(persianaNativa.estaPalhetaErguida());
        assertFalse(persianaNativa.estaPalhetaAberta());
    }

    @Test
    void fecharDepoisDeAbrir_deveExecutarSemExcecao() {
        assertDoesNotThrow(() -> {
            adaptador.abrir();
            adaptador.fechar();
        });
    }

    @Test
    void abrirDepoisDeFechar_deveExecutarSemExcecao() {
        assertDoesNotThrow(() -> {
            adaptador.fechar();
            adaptador.abrir();
        });
    }

    @Test
    void multiplosAbrirEFechar_naoDeveLancarExcecao() {
        assertDoesNotThrow(() -> {
            adaptador.fechar();
            adaptador.abrir();
            adaptador.fechar();
            adaptador.abrir();
        });
    }
}
