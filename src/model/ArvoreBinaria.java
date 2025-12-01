package src.model;

import java.util.*;

/**
 * Implementação de uma Árvore Binária de Busca (BST) genérica.
 * Usa recursividade onde apropriado e provê:
 * inserir, buscar, remover, pré/em/pós-ordem, altura, profundidade,
 * caminho da raiz até um nó, caminho entre dois nós e LCA (ancestral comum).
 */
public class ArvoreBinaria<T extends Comparable<T>> {
    private No<T> raiz;

    // -------- Inserção --------
    public void inserir(T valor) {
        raiz = inserirRec(raiz, valor);
    }

    private No<T> inserirRec(No<T> atual, T valor) {
        if (valor == null) return atual;
        if (atual == null) return new No<>(valor);
        int cmp = valor.compareTo(atual.valor);
        if (cmp < 0) atual.esquerdo = inserirRec(atual.esquerdo, valor);
        else if (cmp > 0) atual.direito = inserirRec(atual.direito, valor);
        // se igual => não insere (evita duplicados por chave comparável)
        return atual;
    }

    // -------- Busca --------
    public boolean buscar(T valor) {
        return buscarRec(raiz, valor) != null;
    }

    private No<T> buscarRec(No<T> atual, T valor) {
        if (atual == null || valor == null) return null;
        int cmp = valor.compareTo(atual.valor);
        if (cmp == 0) return atual;
        if (cmp < 0) return buscarRec(atual.esquerdo, valor);
        else return buscarRec(atual.direito, valor);
    }

    // -------- Remoção --------
    public void remover(T valor) {
        raiz = removerRec(raiz, valor);
    }

    private No<T> removerRec(No<T> atual, T valor) {
        if (atual == null || valor == null) return atual;
        int cmp = valor.compareTo(atual.valor);
        if (cmp < 0) atual.esquerdo = removerRec(atual.esquerdo, valor);
        else if (cmp > 0) atual.direito = removerRec(atual.direito, valor);
        else {
            // Nó encontrado
            if (atual.esquerdo == null) return atual.direito;
            else if (atual.direito == null) return atual.esquerdo;
            // Dois filhos: substituir pelo menor da subárvore direita
            T menor = encontrarMenorValor(atual.direito);
            atual.valor = menor;
            atual.direito = removerRec(atual.direito, menor);
        }
        return atual;
    }

    // Encontra o menor valor na subárvore
    private T encontrarMenorValor(No<T> no) {
        No<T> atual = no;
        while (atual.esquerdo != null) atual = atual.esquerdo;
        return atual.valor;
    }

    // -------- Percursos --------

    // -------- Pré-Ordem --------
    public List<T> preOrdem() {
        List<T> res = new ArrayList<>();
        preOrdemRec(raiz, res);
        return res;
    }

    private void preOrdemRec(No<T> no, List<T> res) {
        if (no != null) {
            res.add(no.valor);
            preOrdemRec(no.esquerdo, res);
            preOrdemRec(no.direito, res);
        }
    }

    // -------- Em Ordem --------
    public List<T> emOrdem() {
        List<T> res = new ArrayList<>();
        emOrdemRec(raiz, res);
        return res;
    }

    private void emOrdemRec(No<T> no, List<T> res) {
        if (no != null) {
            emOrdemRec(no.esquerdo, res);
            res.add(no.valor);
            emOrdemRec(no.direito, res);
        }
    }
    
    // -------- Pós-Ordem --------
    public List<T> posOrdem() {
        List<T> res = new ArrayList<>();
        posOrdemRec(raiz, res);
        return res;
    }

    private void posOrdemRec(No<T> no, List<T> res) {
        if (no != null) {
            posOrdemRec(no.esquerdo, res);
            posOrdemRec(no.direito, res);
            res.add(no.valor);
        }
    }

    // -------- Altura --------
    /**
     * Retorna a altura da árvore.
     * Convenção: altura de árvore vazia = -1 (pode ajustar para 0 se preferir).
     */
    public int altura() {
        return alturaRec(raiz);
    }

    private int alturaRec(No<T> no) {
        if (no == null) return -1;
        return 1 + Math.max(alturaRec(no.esquerdo), alturaRec(no.direito));
    }

    // -------- Profundidade (distância da raiz) --------
    public int profundidade(T valor) {
        return profundidadeRec(raiz, valor, 0);
    }

    private int profundidadeRec(No<T> no, T valor, int nivel) {
        if (no == null || valor == null) return -1;
        int cmp = valor.compareTo(no.valor);
        if (cmp == 0) return nivel;
        if (cmp < 0) return profundidadeRec(no.esquerdo, valor, nivel + 1);
        else return profundidadeRec(no.direito, valor, nivel + 1);
    }

    // -------- Caminho da raiz até um nó --------
    public List<T> caminhoAte(T valor) {
        List<T> caminho = new ArrayList<>();
        if (valor == null) return Collections.emptyList();
        No<T> atual = raiz;
        while (atual != null) {
            caminho.add(atual.valor);
            int cmp = valor.compareTo(atual.valor);
            if (cmp == 0) return caminho;
            if (cmp < 0) atual = atual.esquerdo;
            else atual = atual.direito;
        }
        return Collections.emptyList(); // não encontrado
    }

    // -------- Caminho entre dois nós --------
    public List<T> caminhoEntre(T v1, T v2) {
        if (v1 == null || v2 == null) return Collections.emptyList();
        // Encontre LCA e então caminho
        No<T> lca = encontrarLCA(raiz, v1, v2);
        if (lca == null) return Collections.emptyList();

        // caminho da LCA até v1 (inclusivo)
        List<T> caminho1 = new ArrayList<>();
        No<T> atual = lca;
        while (atual != null) {
            caminho1.add(atual.valor);
            int cmp = v1.compareTo(atual.valor);
            if (cmp == 0) break;
            if (cmp < 0) atual = atual.esquerdo;
            else atual = atual.direito;
        }
        if (caminho1.isEmpty() || !caminho1.get(caminho1.size()-1).equals(v1)) return Collections.emptyList();

        // caminho da LCA até v2 (inclusivo)
        List<T> caminho2 = new ArrayList<>();
        atual = lca;
        while (atual != null) {
            caminho2.add(atual.valor);
            int cmp = v2.compareTo(atual.valor);
            if (cmp == 0) break;
            if (cmp < 0) atual = atual.esquerdo;
            else atual = atual.direito;
        }
        if (caminho2.isEmpty() || !caminho2.get(caminho2.size()-1).equals(v2)) return Collections.emptyList();

        // Construir caminho v1 -> ... -> v2 via LCA:
        List<T> caminho = new ArrayList<>();
        // adicionar caminho1 de v1 até LCA (invertendo), exceto LCA para evitar duplicar
        for (int i = caminho1.size() - 1; i >= 0; i--) {
            if (i == 0) { // último é LCA
                caminho.add(caminho1.get(i));
            } else {
                caminho.add(caminho1.get(i));
            }
        }
        // adicionar caminho2 da LCA (excluindo LCA já inserido) até v2
        for (int i = 1; i < caminho2.size(); i++) {
            caminho.add(caminho2.get(i));
        }
        return caminho;
    }

    // -------- LCA - ancestral comum mais próximo (para BST de forma eficiente) --------
    public T ancestralComum(T v1, T v2) {
        No<T> lca = encontrarLCA(raiz, v1, v2);
        return (lca != null) ? lca.valor : null;
    }

    private No<T> encontrarLCA(No<T> no, T v1, T v2) {
        if (no == null || v1 == null || v2 == null) return null;
        No<T> atual = no;
        while (atual != null) {
            int cmpV1 = v1.compareTo(atual.valor);
            int cmpV2 = v2.compareTo(atual.valor);
            if (cmpV1 < 0 && cmpV2 < 0) {
                atual = atual.esquerdo;
            } else if (cmpV1 > 0 && cmpV2 > 0) {
                atual = atual.direito;
            } else {
                // eles estão em lados diferentes ou um é igual ao atual => atual é LCA
                return atual;
            }
        }
        return null;
    }
}
