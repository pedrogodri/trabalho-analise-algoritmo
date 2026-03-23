package org.example.dominio.ordem;

import org.example.dominio.acao.PrecoAcao;
import org.example.dominio.acao.QuantidadeAcao;
import org.example.dominio.investidor.Investidor;

/**
 * Representa uma ordem de negociação (compra ou venda) registrada por um investidor.
 *
 * <p><b>Ciclo de vida</b>: toda ordem nasce com status {@code PENDENTE}.
 * Ao ser combinada com uma contraparte no {@code LivroDeOrdens}, sua
 * quantidade é decrementada. Quando a quantidade chega a zero, o status
 * muda para {@code EXECUTADA}.</p>
 *
 * <p><b>Execução parcial</b>: se uma ordem de compra de 100 ações for
 * combinada com uma venda de 60, apenas 60 são executadas; a ordem de
 * compra permanece {@code PENDENTE} com 40 ações restantes.</p>
 */
public abstract class Ordem {

    private final Investidor investidor;
    private final PrecoAcao precoAlvo;
    private QuantidadeAcao quantidadeRestante;
    private StatusOrdem status;
    private final TipoOrdem tipoOrdem;

    protected Ordem(Investidor investidor, PrecoAcao precoAlvo,
                    QuantidadeAcao quantidade, TipoOrdem direcao) {
        this.investidor = investidor;
        this.precoAlvo = precoAlvo;
        this.quantidadeRestante = quantidade;
        this.tipoOrdem = direcao;
        this.status = StatusOrdem.PENDENTE;
    }

    public Investidor getInvestidor() {
        return investidor;
    }

    public PrecoAcao getPrecoAlvo() {
        return precoAlvo;
    }

    public QuantidadeAcao getQuantidadeRestante() {
        return quantidadeRestante;
    }

    public TipoOrdem getDirecao() {
        return tipoOrdem;
    }

    public boolean estaPendente() {
        return status == StatusOrdem.PENDENTE;
    }

    /**
     * Deduz a quantidade executada da ordem.
     * Se toda a quantidade for consumida, marca a ordem como {@code EXECUTADA}.
     */
    public void deduzirQuantidade(QuantidadeAcao quantidadeExecutada) {
        if (quantidadeRestante.eIgualA(quantidadeExecutada)) {
            marcarComoExecutada();
            return;
        }
        quantidadeRestante = quantidadeRestante.subtrair(quantidadeExecutada);
    }

    private void marcarComoExecutada() {
        status = StatusOrdem.EXECUTADA;
    }

    @Override
    public String toString() {
        return tipoOrdem + " " + investidor.getNomeCompleto()
            + " @ " + precoAlvo.exibir()
            + " x" + quantidadeRestante + " [" + status + "]";
    }
}
