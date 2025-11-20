package src.model;

import java.util.*;

// Classe genérica de árvore binária, que funciona para qualquer tipo T que implemente Comparable
public class ArvoreBinaria<T extends Comparable<T>> {
    private No<T> raiz; // Nó raiz da árvore

    // ===== INSERÇÃO =====
    // Método público para inserir um valor na árvore
    public void inserir(T valor) {
        raiz = inserirRec(raiz, valor);
    }

    // Inserção recursiva: se nó atual for null, cria novo nó; caso contrário, decide ir para esquerda ou direita
    private No<T> inserirRec(No<T> atual, T valor) {
        if (atual == null) return new No<>(valor);
        if (valor.compareTo(atual.valor) < 0)
            atual.esquerdo = inserirRec(atual.esquerdo, valor);
        else if (valor.compareTo(atual.valor) > 0)
            atual.direito = inserirRec(atual.direito, valor);
        return atual; // Retorna o nó atualizado
    }

    // ===== BUSCA =====
    // Verifica se um valor está presente na árvore
    public boolean buscar(T valor) {
        return buscarRec(raiz, valor) != null;
    }

    // Busca recursiva: se nó atual for null ou valor encontrado, retorna nó; senão, continua à esquerda ou direita
    private No<T> buscarRec(No<T> atual, T valor) {
        if (atual == null || atual.valor.equals(valor)) return atual;
        if (valor.compareTo(atual.valor) < 0)
            return buscarRec(atual.esquerdo, valor);
        else
            return buscarRec(atual.direito, valor);
    }

    // ===== REMOÇÃO =====
    // Remove um valor da árvore
    public void remover(T valor) {
        raiz = removerRec(raiz, valor);
    }

    // Remoção recursiva
    private No<T> removerRec(No<T> atual, T valor) {
        if (atual == null) return null;

        if (valor.compareTo(atual.valor) < 0)
            atual.esquerdo = removerRec(atual.esquerdo, valor);
        else if (valor.compareTo(atual.valor) > 0)
            atual.direito = removerRec(atual.direito, valor);
        else { // Encontrou o nó a remover
            if (atual.esquerdo == null) return atual.direito; // Nó com zero ou um filho à direita
            else if (atual.direito == null) return atual.esquerdo; // Nó com um filho à esquerda
            // Nó com dois filhos: substitui pelo menor valor da subárvore direita
            atual.valor = encontrarMenorValor(atual.direito);
            atual.direito = removerRec(atual.direito, atual.valor);
        }
        return atual;
    }

    // Encontra o menor valor de uma subárvore (nó mais à esquerda)
    private T encontrarMenorValor(No<T> no) {
        while (no.esquerdo != null) no = no.esquerdo;
        return no.valor;
    }

    // ===== PERCURSOS =====
    // Retorna lista de valores em pré-ordem
    public List<T> preOrdem() {
        List<T> resultado = new ArrayList<>();
        preOrdemRec(raiz, resultado);
        return resultado;
    }

    private void preOrdemRec(No<T> no, List<T> resultado) {
        if (no != null) {
            resultado.add(no.valor);
            preOrdemRec(no.esquerdo, resultado);
            preOrdemRec(no.direito, resultado);
        }
    }

    // Retorna lista de valores em ordem (in-order)
    public List<T> emOrdem() {
        List<T> resultado = new ArrayList<>();
        emOrdemRec(raiz, resultado);
        return resultado;
    }

    private void emOrdemRec(No<T> no, List<T> resultado) {
        if (no != null) {
            emOrdemRec(no.esquerdo, resultado);
            resultado.add(no.valor);
            emOrdemRec(no.direito, resultado);
        }
    }

    // Retorna lista de valores em pós-ordem
    public List<T> posOrdem() {
        List<T> resultado = new ArrayList<>();
        posOrdemRec(raiz, resultado);
        return resultado;
    }

    private void posOrdemRec(No<T> no, List<T> resultado) {
        if (no != null) {
            posOrdemRec(no.esquerdo, resultado);
            posOrdemRec(no.direito, resultado);
            resultado.add(no.valor);
        }
    }

    // ===== ALTURA =====
    // Retorna a altura da árvore (nível do nó mais profundo)
    public int altura() {
        return alturaRec(raiz);
    }

    private int alturaRec(No<T> no) {
        if (no == null) return -1;
        return 1 + Math.max(alturaRec(no.esquerdo), alturaRec(no.direito));
    }

    // ===== PROFUNDIDADE DE UM NÓ =====
    // Retorna a profundidade de um valor específico (distância da raiz)
    public int profundidade(T valor) {
        return profundidadeRec(raiz, valor, 0);
    }

    private int profundidadeRec(No<T> no, T valor, int nivel) {
        if (no == null) return -1;
        if (no.valor.equals(valor)) return nivel;
        int esquerda = profundidadeRec(no.esquerdo, valor, nivel + 1);
        if (esquerda != -1) return esquerda;
        return profundidadeRec(no.direito, valor, nivel + 1);
    }

    // ===== CAMINHO DA RAIZ ATÉ UM NÓ =====
    public List<T> caminhoAte(T valor) {
        List<T> caminho = new ArrayList<>();
        if (encontrarCaminho(raiz, valor, caminho))
            return caminho;
        return Collections.emptyList(); // Retorna lista vazia se valor não encontrado
    }

    private boolean encontrarCaminho(No<T> no, T valor, List<T> caminho) {
        if (no == null) return false;
        caminho.add(no.valor);
        if (no.valor.equals(valor)) return true;
        if (encontrarCaminho(no.esquerdo, valor, caminho) || encontrarCaminho(no.direito, valor, caminho))
            return true;
        caminho.remove(caminho.size() - 1); // Remove último elemento se caminho incorreto
        return false;
    }

    // ===== CAMINHO ENTRE DOIS NÓS =====
    public List<T> caminhoEntre(T valor1, T valor2) {
        List<T> caminho1 = caminhoAte(valor1);
        List<T> caminho2 = caminhoAte(valor2);

        if (caminho1.isEmpty() || caminho2.isEmpty()) return Collections.emptyList();

        // Encontrar ponto de divergência
        int i = 0;
        while (i < caminho1.size() && i < caminho2.size() && caminho1.get(i).equals(caminho2.get(i)))
            i++;

        // Construir caminho entre os dois nós
        List<T> caminho = new ArrayList<>();
        for (int j = caminho1.size() - 1; j >= i; j--)
            caminho.add(caminho1.get(j));
        for (int j = i - 1; j < caminho2.size(); j++)
            caminho.add(caminho2.get(j));

        return caminho;
    }

    // ===== ANCESTRAL COMUM MAIS PRÓXIMO (LCA) =====
    // Retorna o ancestral comum mais próximo de dois valores
    public T ancestralComum(T valor1, T valor2) {
        No<T> lca = ancestralComumRec(raiz, valor1, valor2);
        return (lca != null) ? lca.valor : null;
    }

    private No<T> ancestralComumRec(No<T> no, T v1, T v2) {
        if (no == null) return null;

        if (no.valor.equals(v1) || no.valor.equals(v2))
            return no;

        No<T> esq = ancestralComumRec(no.esquerdo, v1, v2);
        No<T> dir = ancestralComumRec(no.direito, v1, v2);

        if (esq != null && dir != null) return no; // Divergência encontrada
        return (esq != null) ? esq : dir;
    }
}
