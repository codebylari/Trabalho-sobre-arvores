package src.model;

import java.util.HashSet;

public class Pessoa implements Comparable<Pessoa> {

    private static HashSet<String> cpfsRegistrados = new HashSet<>();

    private String nome;
    private int idade;
    private String cpf;

    public Pessoa(String nome, int idade, String cpf) {

        // Remove espaços e garante string válida
        this.cpf = (cpf != null) ? cpf.trim() : "";

        // ❌ Se o CPF já existir, lança um erro
        if (cpfsRegistrados.contains(this.cpf)) {
            throw new IllegalArgumentException("Erro: CPF já cadastrado: " + this.cpf);
        }

        // Registra o CPF como novo
        cpfsRegistrados.add(this.cpf);

        this.nome = (nome != null) ? nome.trim() : "";
        this.idade = idade;
    }

    public String getNome() { return nome; }
    public int getIdade() { return idade; }
    public String getCpf() { return cpf; }

    @Override
    public int compareTo(Pessoa outra) {
        // Ordena pelo nome
        int comp = this.nome.compareToIgnoreCase(outra.nome);

        // Se os nomes forem iguais, usa CPF como desempate
        if (comp == 0) {
            return this.cpf.compareToIgnoreCase(outra.cpf);
        }

        return comp;
    }

    @Override
    public boolean equals(Object obj) {
        // Duas pessoas são iguais apenas se o CPF for igual
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
