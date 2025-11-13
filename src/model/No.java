package src.model;

public class No<T> {
    T valor;
    No<T> esquerdo;
    No<T> direito;

    public No(T valor) {
        this.valor = valor;
        this.esquerdo = null;
        this.direito = null;
    }
}
