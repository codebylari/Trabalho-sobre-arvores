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

        JPanel painelEntrada = new JPanel(new GridLayout(2, 2, 5, 5));
        painelEntrada.add(new JLabel("Nome 1:"));
        txtNome1 = new JTextField(10);
        painelEntrada.add(txtNome1);

        painelEntrada.add(new JLabel("Nome 2 (para caminho entre nós):"));
        txtNome2 = new JTextField(10);
        painelEntrada.add(txtNome2);

        add(painelEntrada, BorderLayout.NORTH);

        JPanel painelBotoes = new JPanel(new GridLayout(1, 2, 5, 5));
        JButton btnRaiz = new JButton("Raiz → Nó");
        JButton btnEntre = new JButton("Nó ↔ Nó");

        painelBotoes.add(btnRaiz);
        painelBotoes.add(btnEntre);
        add(painelBotoes, BorderLayout.CENTER);

        txtSaida = new JTextArea(10, 30);
        txtSaida.setEditable(false);
        add(new JScrollPane(txtSaida), BorderLayout.SOUTH);

        btnRaiz.addActionListener(e -> caminhoRaiz());
        btnEntre.addActionListener(e -> caminhoEntreNos());
    }

    private void caminhoRaiz() {
        Pessoa p = new Pessoa(txtNome1.getText(), 0, "");
        List<Pessoa> caminho = arvore.caminhoAte(p);

        txtSaida.setText("");

        if (caminho.isEmpty()) {
            txtSaida.setText("Nó não encontrado.");
            return;
        }

        caminho.forEach(n -> txtSaida.append(n + "\n"));
    }

    private void caminhoEntreNos() {
        Pessoa p1 = new Pessoa(txtNome1.getText(), 0, "");
        Pessoa p2 = new Pessoa(txtNome2.getText(), 0, "");

        List<Pessoa> caminho = arvore.caminhoEntre(p1, p2);

        txtSaida.setText("");

        if (caminho.isEmpty()) {
            txtSaida.setText("Um dos nós não existe.");
            return;
        }

        caminho.forEach(n -> txtSaida.append(n + "\n"));
    }
}
