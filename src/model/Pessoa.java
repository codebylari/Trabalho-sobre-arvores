package src.model;

/**
 * Representa uma pessoa. A ordenação (compareTo) é feita pelo nome, ignorando maiúsculas/minúsculas.
 */
public class Pessoa implements Comparable<Pessoa> {
    private String nome;
    private int idade;
    private String cpf;

    public Pessoa(String nome, int idade, String cpf) {
        this.nome = (nome != null) ? nome.trim() : "";
        this.idade = idade;
        this.cpf = (cpf != null) ? cpf.trim() : "";
    }

    public String getNome() { return nome; }
    public int getIdade() { return idade; }
    public String getCpf() { return cpf; }

    @Override
    public int compareTo(Pessoa outra) {
        if (outra == null) return 1;
        return this.nome.compareToIgnoreCase(outra.nome);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Pessoa p = (Pessoa) obj;
        return this.nome.equalsIgnoreCase(p.nome);
    }

    @Override
    public String toString() {
        return nome + " (" + idade + " anos, CPF: " + cpf + ")";
    }
}
