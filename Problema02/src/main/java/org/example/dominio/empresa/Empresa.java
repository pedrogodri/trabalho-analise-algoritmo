package org.example.dominio.empresa;

import java.util.HashSet;
import java.util.Set;

import org.example.dominio.acao.PrecoAcao;
import org.example.dominio.ordem.OrdemDeCompra;
import org.example.dominio.ordem.OrdemDeVenda;
import org.example.infra.LogMercado;
import org.example.mediador.AlvoDeAtualizacaoDePreco;
import org.example.mediador.LivroDeOrdens;
import org.example.observer.ObservadorDePreco;
import org.example.observer.SujeitoDePreco;

/**
 * Representa uma empresa no mercado de ações.
 *
 * <p>Implementa {@link SujeitoDePreco} (Observer pattern) para notificar
 * investidores inscritos a cada mudança de preço, e {@link AlvoDeAtualizacaoDePreco}
 * para receber atualizações do {@link LivroDeOrdens} sem criar dependência circular.</p>
 *
 * <p><b>Evolução do preço</b>: o preço começa como {@code null} (sem valor de mercado).
 * Ele é definido pela primeira transação executada no livro de ordens.
 * A partir daí, cada nova transação substituirá o preço anterior e
 * notificará todos os observadores inscritos.</p>
 */
public final class Empresa implements SujeitoDePreco, AlvoDeAtualizacaoDePreco {

    private final NomeDaEmpresa nome;
    private PrecoAcao precoAtual;
    private final Set<ObservadorDePreco> observadores;
    private final LivroDeOrdens livroDeOrdens;

    public Empresa(NomeDaEmpresa nome) {
        this.nome = nome;
        this.precoAtual = null;
        this.observadores = new HashSet<>();
        this.livroDeOrdens = new LivroDeOrdens(this);
    }

    /** Registra uma ordem de compra no livro de ordens desta empresa. */
    public void registrarOrdemDeCompra(OrdemDeCompra ordem) {
        livroDeOrdens.registrarOrdemDeCompra(ordem);
    }

    /** Registra uma ordem de venda no livro de ordens desta empresa. */
    public void registrarOrdemDeVenda(OrdemDeVenda ordem) {
        livroDeOrdens.registrarOrdemDeVenda(ordem);
    }

    /** Exibe o preço atual no console. */
    public void exibirPrecoAtual() {
        if (possuiPrecoEstabelecido()) {
            LogMercado.precoAtualizado(nome, precoAtual);
            return;
        }
        LogMercado.semPrecoEstabelecido(nome);
    }

    // --- AlvoDeAtualizacaoDePreco ---

    @Override
    public void atualizarPreco(PrecoAcao novoPreco) {
        registrarAtualizacaoDePreco(novoPreco);
        precoAtual = novoPreco;
        notificarObservadores(novoPreco);
    }

    private void registrarAtualizacaoDePreco(PrecoAcao novoPreco) {
        if (possuiPrecoEstabelecido()) {
            LogMercado.precoAtualizado(nome, novoPreco);
            return;
        }
        LogMercado.precoEstabelecido(nome, novoPreco);
    }

    @Override
    public NomeDaEmpresa obterNome() {
        return nome;
    }

    @Override
    public boolean possuiPrecoEstabelecido() {
        return precoAtual != null;
    }

    // --- SujeitoDePreco ---

    @Override
    public void inscrever(ObservadorDePreco observador) {
        observadores.add(observador);
    }

    @Override
    public void desinscrever(ObservadorDePreco observador) {
        observadores.remove(observador);
    }

    @Override
    public void notificarObservadores(PrecoAcao novoPreco) {
        observadores.forEach(observador -> observador.aoAtualizarPreco(nome, novoPreco));
    }
}
