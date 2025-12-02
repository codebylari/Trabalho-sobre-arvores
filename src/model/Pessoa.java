package src.model;

import java.util.HashSet;

public class Pessoa implements Comparable<Pessoa> {

    private static HashSet<String> cpfsRegistrados = new HashSet<>();

    private String nome;
    private int idade;
    private String cpf;

    public Pessoa(String nome, int idade, String cpf) {

        this.cpf = (cpf != null) ? cpf.trim() : "";

        // 🔥 Correção importante:
        // Não registrar nem validar CPF vazio ("")
        if (!this.cpf.isEmpty()) {

            if (cpfsRegistrados.contains(this.cpf)) {
                throw new IllegalArgumentException("Erro: CPF já cadastrado: " + this.cpf);
            }

            cpfsRegistrados.add(this.cpf);
        }

        this.nome = (nome != null) ? nome.trim() : "";
        this.idade = idade;
    }

    public String getNome() { return nome; }
    public int getIdade() { return idade; }
    public String getCpf() { return cpf; }

    @Override
    public int compareTo(Pessoa outra) {
        int comp = this.nome.compareToIgnoreCase(outra.nome);
        if (comp == 0) {
            return this.cpf.compareToIgnoreCase(outra.cpf);
        }
        return comp;
    }

    @Override
    public boolean equals(Object obj) {
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
