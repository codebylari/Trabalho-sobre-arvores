package src.view;

import src.model.*;
import javax.swing.*;
import java.awt.*;

public class TelaMetricas extends JDialog {

    private ArvoreBinaria<Pessoa> arvore;

    private JTextField txtNomeProfundidade;
    private JTextField txtLCA1;
    private JTextField txtLCA2;
    private JTextArea txtSaida;

    public TelaMetricas(JFrame parent, ArvoreBinaria<Pessoa> arvore) {
        super(parent, "Métricas da Árvore", true);
        this.arvore = arvore;

        setSize(420, 350);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(5, 5));

        JPanel painelCampos = new JPanel(new GridLayout(3, 2, 5, 5));

        painelCampos.add(new JLabel("Nome para profundidade:"));
        txtNomeProfundidade = new JTextField(10);
        painelCampos.add(txtNomeProfundidade);

        painelCampos.add(new JLabel("LCA - Nome 1:"));
        txtLCA1 = new JTextField(10);
        painelCampos.add(txtLCA1);

        painelCampos.add(new JLabel("LCA - Nome 2:"));
        txtLCA2 = new JTextField(10);
        painelCampos.add(txtLCA2);

        add(painelCampos, BorderLayout.NORTH);

        JPanel painelBotoes = new JPanel(new GridLayout(1, 3, 5, 5));
        JButton btnAltura = new JButton("Altura");
        JButton btnProf   = new JButton("Profundidade");
        JButton btnLCA    = new JButton("LCA");

        painelBotoes.add(btnAltura);
        painelBotoes.add(btnProf);
        painelBotoes.add(btnLCA);

        add(painelBotoes, BorderLayout.CENTER);

        txtSaida = new JTextArea(10, 30);
        txtSaida.setEditable(false);
        add(new JScrollPane(txtSaida), BorderLayout.SOUTH);

        btnAltura.addActionListener(e -> mostrarAltura());
        btnProf.addActionListener(e -> mostrarProfundidade());
        btnLCA.addActionListener(e -> mostrarLCA());
    }

    private void mostrarAltura() {
        txtSaida.setText("Altura da árvore: " + arvore.altura());
    }

    private void mostrarProfundidade() {
        String nome = txtNomeProfundidade.getText();
        int prof = arvore.profundidade(new Pessoa(nome, 0, ""));
        txtSaida.setText("Profundidade de " + nome + ": " + prof);
    }

    private void mostrarLCA() {
        Pessoa p1 = new Pessoa(txtLCA1.getText(), 0, "");
        Pessoa p2 = new Pessoa(txtLCA2.getText(), 0, "");

        Pessoa lca = arvore.ancestralComum(p1, p2);

        txtSaida.setText((lca == null) ? "Nenhum ancestral encontrado." : "LCA: " + lca);
    }
}
