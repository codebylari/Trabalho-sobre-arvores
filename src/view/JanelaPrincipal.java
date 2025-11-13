package src.view;

import src.model.*;
import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class JanelaPrincipal extends JFrame {
    private ArvoreBinaria<Pessoa> arvore = new ArvoreBinaria<>();

    private JTextField txtNome = new JTextField(10);
    private JTextField txtIdade = new JTextField(5);
    private JTextField txtCpf = new JTextField(10);
    private JTextArea txtSaida = new JTextArea(12, 35);

    // Novo: histórico dos dois últimos comandos
    private DefaultListModel<String> historicoModel = new DefaultListModel<>();
    private JList<String> listaHistorico = new JList<>(historicoModel);
    private LinkedList<String> comandosRecentes = new LinkedList<>();

    public JanelaPrincipal() {
        super("🌳 Árvore Binária de Pessoas");
        setLayout(new BorderLayout());

        // Painel de entrada
        JPanel painelEntrada = new JPanel(new GridLayout(3, 4, 5, 5));
        painelEntrada.add(new JLabel("Nome:"));
        painelEntrada.add(txtNome);
        painelEntrada.add(new JLabel("Idade:"));
        painelEntrada.add(txtIdade);
        painelEntrada.add(new JLabel("CPF:"));
        painelEntrada.add(txtCpf);

        // Painel de botões principais
        JPanel painelBotoes = new JPanel(new GridLayout(2, 4, 5, 5));
        JButton btnInserir = new JButton("Inserir");
        JButton btnRemover = new JButton("Remover");
        JButton btnBuscar = new JButton("Buscar");
        JButton btnAltura = new JButton("Altura");

        JButton btnPreOrdem = new JButton("Pré-Ordem");
        JButton btnEmOrdem = new JButton("Em Ordem");
        JButton btnPosOrdem = new JButton("Pós-Ordem");
        JButton btnLimpar = new JButton("Limpar");

        painelBotoes.add(btnInserir);
        painelBotoes.add(btnRemover);
        painelBotoes.add(btnBuscar);
        painelBotoes.add(btnAltura);
        painelBotoes.add(btnPreOrdem);
        painelBotoes.add(btnEmOrdem);
        painelBotoes.add(btnPosOrdem);
        painelBotoes.add(btnLimpar);

        // Painel superior (entrada + botões)
        JPanel painelSuperior = new JPanel(new BorderLayout());
        painelSuperior.add(painelEntrada, BorderLayout.NORTH);
        painelSuperior.add(painelBotoes, BorderLayout.CENTER);
        add(painelSuperior, BorderLayout.NORTH);

        // Histórico acima da saída
        JPanel painelHistorico = new JPanel(new BorderLayout());
        painelHistorico.setBorder(BorderFactory.createTitledBorder("🕘 Histórico (2 últimos comandos)"));
        painelHistorico.add(new JScrollPane(listaHistorico), BorderLayout.CENTER);
        add(painelHistorico, BorderLayout.WEST);

        // Área de saída (para percursos e resultados de buscas)
        txtSaida.setEditable(false);
        txtSaida.setFont(new Font("Monospaced", Font.PLAIN, 13));
        add(new JScrollPane(txtSaida), BorderLayout.CENTER);

        // ====== EVENTOS ======
        btnInserir.addActionListener(e -> inserirPessoa());
        btnRemover.addActionListener(e -> removerPessoa());
        btnBuscar.addActionListener(e -> buscarPessoa());
        btnAltura.addActionListener(e -> mostrarAltura());
        btnPreOrdem.addActionListener(e -> mostrarPercurso("PRE"));
        btnEmOrdem.addActionListener(e -> mostrarPercurso("EM"));
        btnPosOrdem.addActionListener(e -> mostrarPercurso("POS"));
        btnLimpar.addActionListener(e -> limparSaida());

        // ====== CONFIGURAÇÕES ======
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // ===== MÉTODOS AUXILIARES =====
    private void inserirPessoa() {
        try {
            String nome = txtNome.getText();
            int idade = Integer.parseInt(txtIdade.getText());
            String cpf = txtCpf.getText();

            arvore.inserir(new Pessoa(nome, idade, cpf));
            adicionarAoHistorico("✅ Inserido: " + nome);
            limparCampos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao inserir: " + ex.getMessage());
        }
    }

    private void removerPessoa() {
        String nome = txtNome.getText();
        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o nome da pessoa para remover.");
            return;
        }

        arvore.remover(new Pessoa(nome, 0, ""));
        adicionarAoHistorico("❌ Removido (se existia): " + nome);
        limparCampos();
    }

    private void buscarPessoa() {
        String nome = txtNome.getText();
        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o nome para buscar.");
            return;
        }

        boolean encontrado = arvore.buscar(new Pessoa(nome, 0, ""));
        adicionarAoHistorico(encontrado ? "🔎 Encontrado: " + nome : "🚫 Não encontrado: " + nome);
        limparCampos();
    }

    private void mostrarAltura() {
        txtSaida.append("\n🌲 Altura da Árvore: " + arvore.altura() + "\n");
        adicionarAoHistorico("📏 Altura consultada");
    }

    private void mostrarPercurso(String tipo) {
        txtSaida.append("\n=== PERCURSO " + tipo + "-ORDEM ===\n");
        switch (tipo) {
            case "PRE":
                for (Pessoa p : arvore.preOrdem())
                    txtSaida.append(p + "\n");
                break;
            case "EM":
                for (Pessoa p : arvore.emOrdem())
                    txtSaida.append(p + "\n");
                break;
            case "POS":
                for (Pessoa p : arvore.posOrdem())
                    txtSaida.append(p + "\n");
                break;
        }
        adicionarAoHistorico("📋 Percurso " + tipo + "-Ordem mostrado");
    }

    private void limparSaida() {
        txtSaida.setText("");
        adicionarAoHistorico("🧹 Tela limpa");
    }

    private void limparCampos() {
        txtNome.setText("");
        txtIdade.setText("");
        txtCpf.setText("");
    }

    // ===== HISTÓRICO =====
    private void adicionarAoHistorico(String comando) {
        comandosRecentes.addFirst(comando);
        if (comandosRecentes.size() > 2)
            comandosRecentes.removeLast();

        historicoModel.clear();
        for (String c : comandosRecentes)
            historicoModel.addElement(c);
    }
}
