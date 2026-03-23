package org.example.dominio.ordem;

/**
 * Ciclo de vida de uma ordem no livro de ordens.
 *
 * <ul>
 *   <li>{@code PENDENTE} — registrada mas aguardando contraparte compatível.</li>
 *   <li>{@code EXECUTADA} — totalmente processada; quantidade zerada.</li>
 * </ul>
 */
public enum StatusOrdem {
    PENDENTE,
    EXECUTADA
}
