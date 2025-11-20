package src.model; // Declara que esta classe pertence ao pacote src.model

// Classe Pessoa que implementa Comparable, permitindo comparar objetos Pessoa pelo nome
public class Pessoa implements Comparable<Pessoa> {
    private String nome; // Atributo que armazena o nome da pessoa
    private int idade;   // Atributo que armazena a idade da pessoa
    private String cpf;  // Atributo que armazena o CPF da pessoa

    // Construtor da classe, inicializa os atributos da pessoa
    public Pessoa(String nome, int idade, String cpf) {
        this.nome = nome;
        this.idade = idade;
        this.cpf = cpf;
    }

    // Getter para o nome
    public String getNome() {
        return nome;
    }

    // Getter para a idade
    public int getIdade() {
        return idade;
    }

    // Getter para o CPF
    public String getCpf() {
        return cpf;
    }

    // Método compareTo da interface Comparable
    // Permite comparar duas pessoas pelo nome, ignorando maiúsculas/minúsculas
    @Override
    public int compareTo(Pessoa outra) {
        return this.nome.compareToIgnoreCase(outra.nome);
    }

    // Método equals sobrescrito para comparar pessoas pelo nome
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // Se for o mesmo objeto, retorna true
        if (obj == null || getClass() != obj.getClass()) return false; // Se for null ou de outra classe, retorna false
        Pessoa pessoa = (Pessoa) obj; // Faz o cast para Pessoa
        return nome.equalsIgnoreCase(pessoa.nome); // Compara nomes ignorando maiúsculas/minúsculas
    }

    // Método toString sobrescrito para exibir a pessoa de forma legível
    @Override
    public String toString() {
        return nome + " (" + idade + " anos, CPF: " + cpf + ")";
    }
}
