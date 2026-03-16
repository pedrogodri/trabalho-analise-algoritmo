package org.example.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class DadoInvalidoExceptionTest {

    @Test
    void deveArmazenarMensagemCorretamente() {
        DadoInvalidoException excecao = new DadoInvalidoException("mensagem de teste");
        assertEquals("mensagem de teste", excecao.getMessage());
    }

    @Test
    void deveEstenderIllegalArgumentException() {
        DadoInvalidoException excecao = new DadoInvalidoException("erro");
        assertInstanceOf(IllegalArgumentException.class, excecao);
    }

    @Test
    void devePoderSerCapturadaComoIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            throw new DadoInvalidoException("campo inválido");
        });
    }
}
