package src.view;
import src.model.*;
import javax.swing.*;
import java.awt.*;

public class TelaCadastro extends JDialog {

    private JTextField txtNome = new JTextField(15);
    private JTextField txtIdade = new JTextField(5);
    private JTextField txtCpf = new JTextField(12);

    public TelaCadastro(JFrame parent, ArvoreBinaria<Pessoa> arvore) {
        super(parent, "Cadastro de Pessoa", true);
        setLayout(new GridLayout(4, 2, 5, 5));

        add(new JLabel("Nome:"));
        add(txtNome);

        add(new JLabel("Idade:"));
        add(txtIdade);

        add(new JLabel("CPF:"));
        add(txtCpf);

        JButton btnInserir = new JButton("Inserir");
        add(btnInserir);
        JButton btnCancelar = new JButton("Cancelar");
        add(btnCancelar);

        btnInserir.addActionListener(e -> {
            try {
                String nome = txtNome.getText();
                int idade = Integer.parseInt(txtIdade.getText());
                String cpf = txtCpf.getText();

                arvore.inserir(new Pessoa(nome, idade, cpf));
                ((JanelaPrincipal) parent).adicionarAoHistorico("✅ Inserido: " + nome);
                ((JanelaPrincipal) parent).atualizarTabela();
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
            }
        });

        btnCancelar.addActionListener(e -> dispose());

        pack();
        setLocationRelativeTo(parent);
    }
}
