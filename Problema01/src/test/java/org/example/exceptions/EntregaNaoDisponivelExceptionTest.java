package org.example.exceptions;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class EntregaNaoDisponivelExceptionTest {

    @Test
    void deveArmazenarMensagemCorretamente() {
        EntregaNaoDisponivelException excecao = new EntregaNaoDisponivelException("entrega indisponível");
        assertEquals("entrega indisponível", excecao.getMessage());
    }

    @Test
    void deveEstenderRuntimeException() {
        EntregaNaoDisponivelException excecao = new EntregaNaoDisponivelException("erro");
        assertInstanceOf(RuntimeException.class, excecao);
    }

    @Test
    void devePoderSerCapturadaComoRuntimeException() {
        assertThrows(RuntimeException.class, () -> {
            throw new EntregaNaoDisponivelException("peso excedido");
        });
    }

    @Test
    void deveSerLancadaSemChecagemObrigatoria() {
        // RuntimeException não exige declaração no throws, basta instanciar
        assertDoesNotThrow(() -> {
            EntregaNaoDisponivelException e = new EntregaNaoDisponivelException("ok");
            assertNotNull(e);
        });
    }
}
