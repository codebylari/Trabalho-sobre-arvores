package src.view;

import src.model.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaCaminhos extends JDialog {

    private ArvoreBinaria<Pessoa> arvore;
    private JTextField txtNome1;
    private JTextField txtNome2;
    private JTextArea txtSaida;

    public TelaCaminhos(JFrame parent, ArvoreBinaria<Pessoa> arvore) {
        super(parent, "Caminhos na Árvore", true);
        this.arvore = arvore;

        setSize(420, 350);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(5, 5));

        // -------- PAINEL DE ENTRADA --------
        JPanel painelEntrada = new JPanel(new GridLayout(2, 2, 5, 5));

        painelEntrada.add(new JLabel("Nome 1:"));
        txtNome1 = new JTextField(10);
        painelEntrada.add(txtNome1);

        painelEntrada.add(new JLabel("Nome 2 (para caminho entre nós):"));
        txtNome2 = new JTextField(10);
        painelEntrada.add(txtNome2);

        add(painelEntrada, BorderLayout.NORTH);

        // -------- BOTÕES --------
        JPanel painelBotoes = new JPanel(new GridLayout(1, 2, 5, 5));
        JButton btnRaiz = new JButton("Raiz → Nó");
        JButton btnEntre = new JButton("Nó ↔ Nó");

        painelBotoes.add(btnRaiz);
        painelBotoes.add(btnEntre);

        add(painelBotoes, BorderLayout.CENTER);

        // -------- SAÍDA --------
        txtSaida = new JTextArea(10, 30);
        txtSaida.setEditable(false);
        add(new JScrollPane(txtSaida), BorderLayout.SOUTH);

        // Ações
        btnRaiz.addActionListener(e -> caminhoRaiz());
        btnEntre.addActionListener(e -> caminhoEntreNos());
    }

    // -------------------------------------------------------
    //                CAMINHO RAIZ → NÓ
    // -------------------------------------------------------
    private void caminhoRaiz() {

        String nome = txtNome1.getText().trim();
        txtSaida.setText("");

        // Verificação de campo vazio
        if (nome.isEmpty()) {
            txtSaida.setText("Preencha o campo Nome 1.");
            return;
        }

        Pessoa p = new Pessoa(nome, 0, "");
        List<Pessoa> caminho = arvore.caminhoAte(p);

        if (caminho.isEmpty()) {
            txtSaida.setText("Nó não encontrado.");
            return;
        }

        caminho.forEach(n -> txtSaida.append(n + "\n"));
    }

    // -------------------------------------------------------
    //                CAMINHO NÓ ↔ NÓ
    // -------------------------------------------------------
    private void caminhoEntreNos() {

        String nome1 = txtNome1.getText().trim();
        String nome2 = txtNome2.getText().trim();
        txtSaida.setText("");

        // Verificação dos campos
        if (nome1.isEmpty() || nome2.isEmpty()) {
            txtSaida.setText("Preencha os dois campos (Nome 1 e Nome 2).");
            return;
        }

        Pessoa p1 = new Pessoa(nome1, 0, "");
        Pessoa p2 = new Pessoa(nome2, 0, "");

        List<Pessoa> caminho = arvore.caminhoEntre(p1, p2);

        if (caminho.isEmpty()) {
            txtSaida.setText("Um dos nós não existe.");
            return;
        }

        caminho.forEach(n -> txtSaida.append(n + "\n"));
    }
}
