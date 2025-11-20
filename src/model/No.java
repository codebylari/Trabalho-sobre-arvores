package src.model; // Declara que esta classe pertence ao pacote src.model

// Classe genérica que representa um nó de uma estrutura de dados, como uma árvore binária
public class No<T> { 
    T valor;        // Armazena o valor do nó. É genérico, então pode ser de qualquer tipo.
    No<T> esquerdo; // Referência para o nó filho à esquerda
    No<T> direito;  // Referência para o nó filho à direita

    // Construtor da classe
    public No(T valor) {
        this.valor = valor;   // Inicializa o valor do nó com o valor passado
        this.esquerdo = null; // Inicializa o filho esquerdo como nulo
        this.direito = null;  // Inicializa o filho direito como nulo
    }
}