package org.example.dominio.ordem;

import org.example.dominio.acao.PrecoAcao;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * First-class collection de ordens (compra ou venda).
 *
 * <p>Oferece operações de busca e ordenação usadas pelo {@code CombinadorDeOrdens}
 * sem expor a lista interna. Toda filtragem respeita o status {@code PENDENTE}.</p>
 */
public final class ListaDeOrdens {

    private final List<Ordem> ordens;

    public ListaDeOrdens() {
        this.ordens = new ArrayList<>();
    }

    public void adicionar(Ordem ordem) {
        ordens.add(ordem);
    }

    /** Retorna nova lista com ordens pendentes ordenadas por preço decrescente (maior lance primeiro). */
    public ListaDeOrdens ordenadosPorPrecoDecrescente() {
        List<Ordem> pendentes = filtrarPendentes();
        pendentes.sort(Comparator.comparing(Ordem::getPrecoAlvo).reversed());
        ListaDeOrdens resultado = new ListaDeOrdens();
        pendentes.forEach(resultado::adicionar);
        return resultado;
    }

    /**
     * Busca a primeira ordem de venda pendente cujo preço seja menor ou igual ao máximo informado.
     * Usado pelo combinador para encontrar contrapartes elegíveis.
     */
    public Optional<OrdemDeVenda> encontrarVendaComPrecoAteMáximo(PrecoAcao precoMaximo) {
        return filtrarPendentes().stream()
            .filter(OrdemDeVenda.class::isInstance)
            .map(OrdemDeVenda.class::cast)
            .filter(venda -> precoMaximo.eMaiorOuIgualA(venda.getPrecoAlvo()))
            .findFirst();
    }

    public List<Ordem> comoLista() {
        return filtrarPendentes();
    }

    public boolean estaVazia() {
        return filtrarPendentes().isEmpty();
    }

    private List<Ordem> filtrarPendentes() {
        return ordens.stream()
            .filter(Ordem::estaPendente)
            .collect(Collectors.toList());
    }
}
