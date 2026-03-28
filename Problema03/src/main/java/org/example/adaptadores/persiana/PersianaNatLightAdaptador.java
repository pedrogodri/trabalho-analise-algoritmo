package org.example.adaptadores.persiana;

import br.furb.analise.algoritmos.PersianaNatLight;
import org.example.interfaces.Persiana;

/**
 * Adaptador da {@link PersianaNatLight} (fabricante NatLight) para a interface universal {@link Persiana}.
 *
 * <p>A NatLight possui uma mecânica de dois estados independentes:</p>
 * <ul>
 *   <li><b>Palheta</b>: aberta ou fechada</li>
 *   <li><b>Persiana</b>: erguida ou descida</li>
 * </ul>
 *
 * <p>As seguintes restrições do fabricante são gerenciadas internamente por este adaptador:</p>
 * <ul>
 *   <li>{@code subirPalheta()} lança exceção se as palhetas estiverem fechadas</li>
 *   <li>{@code fecharPalheta()} lança exceção se a persiana estiver erguida</li>
 * </ul>
 *
 * <p>Para garantir operações seguras sem expor essa complexidade ao chamador:</p>
 * <ul>
 *   <li>{@code abrir()} → {@code abrirPalheta()} primeiro, depois {@code subirPalheta()}</li>
 *   <li>{@code fechar()} → {@code descerPalheta()} primeiro, depois {@code fecharPalheta()}</li>
 * </ul>
 *
 * <p>As exceções checadas dos métodos internos são capturadas e relançadas como
 * {@link IllegalStateException} para manter o contrato da interface universal sem exceções checadas.</p>
 *
 * <p>Padrão aplicado: <b>Adapter (Objeto)</b> — além de adaptar a API, encapsula a
 * lógica de ordenação de operações exigida pelo fabricante.</p>
 */
public final class PersianaNatLightAdaptador implements Persiana {

    private final PersianaNatLight persiana;

    /**
     * @param persiana instância da persiana NatLight fornecida pela biblioteca do fabricante
     */
    public PersianaNatLightAdaptador(PersianaNatLight persiana) {
        this.persiana = persiana;
    }

    /**
     * Abre a persiana: garante que as palhetas estejam abertas antes de erguer.
     */
    @Override
    public void abrir() {
        persiana.abrirPalheta();
        tentarSubirPalheta();
    }

    /**
     * Fecha a persiana: desce primeiro, depois fecha as palhetas.
     */
    @Override
    public void fechar() {
        persiana.descerPalheta();
        tentarFecharPalheta();
    }

    private void tentarSubirPalheta() {
        try {
            persiana.subirPalheta();
        } catch (Exception e) {
            throw new IllegalStateException("Falha ao subir palheta da NatLight: " + e.getMessage(), e);
        }
    }

    private void tentarFecharPalheta() {
        try {
            persiana.fecharPalheta();
        } catch (Exception e) {
            throw new IllegalStateException("Falha ao fechar palheta da NatLight: " + e.getMessage(), e);
        }
    }
}
