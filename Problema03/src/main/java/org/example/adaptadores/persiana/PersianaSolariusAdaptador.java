package org.example.adaptadores.persiana;

import br.furb.analise.algoritmos.PersianaSolarius;
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

    private final PersianaSolarius persiana;

    /**
     * @param persiana instância da persiana Solarius fornecida pela biblioteca do fabricante
     */
    public PersianaSolariusAdaptador(PersianaSolarius persiana) {
        this.persiana = persiana;
    }

    /**
     * Abre a persiana (sobe).
     */
    @Override
    public void abrir() {
        persiana.subirPersiana();
    }

    /**
     * Fecha a persiana (desce).
     */
    @Override
    public void fechar() {
        persiana.descerPersiana();
    }
}
