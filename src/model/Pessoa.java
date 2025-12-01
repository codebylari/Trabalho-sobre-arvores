package src.model;

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
        // Ordena primeiro pelo nome
        int comp = this.nome.compareToIgnoreCase(outra.nome);

        // Se os nomes forem iguais, diferencia pelo CPF
        if (comp == 0) {
            return this.cpf.compareToIgnoreCase(outra.cpf);
        }

        return comp;
    }

    @Override
    public boolean equals(Object obj) {
        // Duas pessoas são iguais APENAS se tiverem o mesmo CPF
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Pessoa p = (Pessoa) obj;
        return this.cpf.equalsIgnoreCase(p.cpf);
    }

    @Override
    public String toString() {
        return nome + " (" + idade + " anos, CPF: " + cpf + ")";
    }
}
