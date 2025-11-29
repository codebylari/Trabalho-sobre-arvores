package src.view;

import src.model.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaPercursos extends JDialog {

    private ArvoreBinaria<Pessoa> arvore;
    private JTextArea txtSaida;

    public TelaPercursos(JFrame parent, ArvoreBinaria<Pessoa> arvore) {
        super(parent, "Percursos da Árvore", true);
        this.arvore = arvore;

        setSize(400, 320);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(5, 5));

        JPanel painelBotoes = new JPanel(new GridLayout(1, 3, 5, 5));
        JButton btnPre = new JButton("Pré-Ordem");
        JButton btnEm  = new JButton("Em Ordem");
        JButton btnPos = new JButton("Pós-Ordem");

        painelBotoes.add(btnPre);
        painelBotoes.add(btnEm);
        painelBotoes.add(btnPos);
        add(painelBotoes, BorderLayout.NORTH);

        txtSaida = new JTextArea(10, 30);
        txtSaida.setEditable(false);
        add(new JScrollPane(txtSaida), BorderLayout.CENTER);

        btnPre.addActionListener(e -> mostrarPercurso("PRE"));
        btnEm.addActionListener(e -> mostrarPercurso("EM"));
        btnPos.addActionListener(e -> mostrarPercurso("POS"));
    }

    private void mostrarPercurso(String tipo) {
        txtSaida.setText("");

        List<Pessoa> lista;
        switch (tipo) {
            case "PRE": lista = arvore.preOrdem(); break;
            case "EM":  lista = arvore.emOrdem();  break;
            default:    lista = arvore.posOrdem(); break;
        }

        for (Pessoa p : lista)
            txtSaida.append(p + "\n");
    }
}
