package org.example.dominio.empresa;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.example.dominio.acao.PrecoAcao;
import org.example.dominio.ordem.OrdemDeCompra;
import org.example.dominio.ordem.OrdemDeVenda;
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
 * <p><b>Evolução do preço</b>: o preço começa como {@link Optional#empty()}.
 * Ele é definido pela primeira transação executada no livro de ordens.
 * A partir daí, cada nova transação substituirá o preço anterior e
 * notificará todos os observadores inscritos.</p>
 */
public final class Empresa implements SujeitoDePreco, AlvoDeAtualizacaoDePreco {

    private final NomeDaEmpresa nome;
    private Optional<PrecoAcao> precoAtual;
    private final List<ObservadorDePreco> observadores;
    private final LivroDeOrdens livroDeOrdens;

    public Empresa(NomeDaEmpresa nome) {
        this.nome = nome;
        this.precoAtual = Optional.empty();
        this.observadores = new ArrayList<>();
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
        precoAtual.ifPresentOrElse(
            preco -> System.out.println("  " + nome.getNome() + ": " + preco.exibir()),
            () -> System.out.println("  " + nome.getNome() + ": (preco ainda nao estabelecido)")
        );
    }

    // --- AlvoDeAtualizacaoDePreco ---

    /**
     * Atualiza o preço da ação e notifica todos os observadores inscritos.
     * Chamado pelo {@link LivroDeOrdens} após cada transação.
     */
    @Override
    public void atualizarPreco(PrecoAcao novoPreco) {
        String estado = possuiPrecoEstabelecido() ? "atualizado para" : "estabelecido em";
        precoAtual = Optional.of(novoPreco);
        System.out.println("[PRECO] " + nome.getNome() + " " + estado + " " + novoPreco.exibir());
        notificarObservadores(novoPreco);
    }

    @Override
    public NomeDaEmpresa obterNome() {
        return nome;
    }

    @Override
    public boolean possuiPrecoEstabelecido() {
        return precoAtual.isPresent();
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

    /**
     * Notifica cada observador inscrito com o novo preço.
     * Chamado internamente após cada atualização; não deve ser invocado externamente.
     */
    @Override
    public void notificarObservadores(PrecoAcao novoPreco) {
        observadores.forEach(observador -> observador.aoAtualizarPreco(nome, novoPreco));
    }
}
