package org.example.adaptadores.persiana;

import br.furb.analise.algoritmos.PersianaNatLight;
import org.example.excecoes.DispositivoNuloException;
import org.example.excecoes.FalhaDispositivoException;
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
 * {@link FalhaDispositivoException} para manter o contrato da interface universal.</p>
 *
 * <p>Padrão aplicado: <b>Adapter (Objeto)</b> — além de adaptar a API, encapsula a
 * lógica de ordenação de operações exigida pelo fabricante.</p>
 */
public final class PersianaNatLightAdaptador implements Persiana {

    private static final String NOME = "Persiana NatLight";

    private final PersianaNatLight persiana;

    /**
     * @param persiana instância da persiana NatLight fornecida pela biblioteca do fabricante
     * @throws DispositivoNuloException se {@code persiana} for nula
     */
    public PersianaNatLightAdaptador(PersianaNatLight persiana) {
        if (persiana == null) throw new DispositivoNuloException(NOME + ": dispositivo não pode ser nulo.");
        this.persiana = persiana;
    }

    /**
     * Abre a persiana: garante que as palhetas estejam abertas antes de erguer.
     *
     * @throws FalhaDispositivoException se falhar ao abrir palheta ou subir a persiana
     */
    @Override
    public void abrir() {
        System.out.println("[" + NOME + "] Iniciando abertura: abrindo palhetas...");
        persiana.abrirPalheta();
        tentarSubirPalheta();
        System.out.println("[" + NOME + "] Aberta (palhetas abertas + persiana erguida).");
    }

    /**
     * Fecha a persiana: desce primeiro, depois fecha as palhetas.
     *
     * @throws FalhaDispositivoException se falhar ao descer ou fechar as palhetas
     */
    @Override
    public void fechar() {
        System.out.println("[" + NOME + "] Iniciando fechamento: descendo persiana...");
        persiana.descerPalheta();
        tentarFecharPalheta();
        System.out.println("[" + NOME + "] Fechada (persiana descida + palhetas fechadas).");
    }

    private void tentarSubirPalheta() {
        try {
            persiana.subirPalheta();
        } catch (Exception e) {
            throw new FalhaDispositivoException(
                    "[" + NOME + "] Falha ao subir palheta: " + e.getMessage()
                    + " — verifique se as palhetas estavam abertas antes de subir.", e);
        }
    }

    private void tentarFecharPalheta() {
        try {
            persiana.fecharPalheta();
        } catch (Exception e) {
            throw new FalhaDispositivoException(
                    "[" + NOME + "] Falha ao fechar palheta: " + e.getMessage()
                    + " — a persiana precisa estar descida antes de fechar as palhetas.", e);
        }
    }
}
