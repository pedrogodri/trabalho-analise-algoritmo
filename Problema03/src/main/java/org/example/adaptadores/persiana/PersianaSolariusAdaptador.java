package org.example.adaptadores.persiana;

import br.furb.analise.algoritmos.PersianaSolarius;
import org.example.excecoes.DispositivoNuloException;
import org.example.excecoes.FalhaDispositivoException;
import org.example.interfaces.Persiana;

/**
 * Adaptador da {@link PersianaSolarius} (fabricante Solarius) para a interface universal {@link Persiana}.
 *
 * <p>A API do fabricante usa {@code subirPersiana()} e {@code descerPersiana()}, que são mapeados
 * semanticamente para os conceitos universais de abrir e fechar:</p>
 * <ul>
 *   <li>{@code abrir()} → {@code subirPersiana()}</li>
 *   <li>{@code fechar()} → {@code descerPersiana()}</li>
 * </ul>
 *
 * <p>Nenhum dos métodos da Solarius lança exceções, portanto não há restrições de ordem
 * a serem gerenciadas por este adaptador.</p>
 *
 * <p>Padrão aplicado: <b>Adapter (Objeto)</b> — delegação direta com renomeação semântica.</p>
 */
public final class PersianaSolariusAdaptador implements Persiana {

    private static final String NOME = "Persiana Solarius";

    private final PersianaSolarius persiana;

    /**
     * @param persiana instância da persiana Solarius fornecida pela biblioteca do fabricante
     * @throws DispositivoNuloException se {@code persiana} for nula
     */
    public PersianaSolariusAdaptador(PersianaSolarius persiana) {
        if (persiana == null) throw new DispositivoNuloException(NOME + ": dispositivo não pode ser nulo.");
        this.persiana = persiana;
    }

    /**
     * Abre a persiana (sobe).
     *
     * @throws FalhaDispositivoException se o dispositivo falhar ao subir
     */
    @Override
    public void abrir() {
        try {
            persiana.subirPersiana();
            System.out.println("[" + NOME + "] Aberta (persiana subida).");
        } catch (Exception e) {
            throw new FalhaDispositivoException("[" + NOME + "] Falha ao abrir: " + e.getMessage(), e);
        }
    }

    /**
     * Fecha a persiana (desce).
     *
     * @throws FalhaDispositivoException se o dispositivo falhar ao descer
     */
    @Override
    public void fechar() {
        try {
            persiana.descerPersiana();
            System.out.println("[" + NOME + "] Fechada (persiana descida).");
        } catch (Exception e) {
            throw new FalhaDispositivoException("[" + NOME + "] Falha ao fechar: " + e.getMessage(), e);
        }
    }
}
