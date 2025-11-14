package src.view;

import src.model.*;
import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class JanelaPrincipal extends JFrame {

    private ArvoreBinaria<Pessoa> arvore = new ArvoreBinaria<>();

    // CAMPOS PRINCIPAIS
    private JTextField txtNome = new JTextField(10);
    private JTextField txtIdade = new JTextField(5);
    private JTextField txtCpf = new JTextField(10);

    // CAMPOS EXTRAS (para operações com dois nós)
    private JTextField txtNome2 = new JTextField(10);

    private JTextArea txtSaida = new JTextArea(14, 40);

    // HISTÓRICO DOS DOIS ÚLTIMOS COMANDOS
    private DefaultListModel<String> historicoModel = new DefaultListModel<>();
    private JList<String> listaHistorico = new JList<>(historicoModel);
    private LinkedList<String> comandosRecentes = new LinkedList<>();

    public JanelaPrincipal() {
        super("🌳 Árvore Binária de Pessoas");
        setLayout(new BorderLayout());

        // ================= PAINEL DE ENTRADA =================
        JPanel painelEntrada = new JPanel(new GridLayout(4, 4, 5, 5));
        painelEntrada.add(new JLabel("Nome:"));
        painelEntrada.add(txtNome);
        painelEntrada.add(new JLabel("Idade:"));
        painelEntrada.add(txtIdade);

        painelEntrada.add(new JLabel("CPF:"));
        painelEntrada.add(txtCpf);
        painelEntrada.add(new JLabel("2º Nome (Para Caminho/LCA):"));
        painelEntrada.add(txtNome2);

        // ================= PAINEL DE BOTÕES =================
        JPanel painelBotoes = new JPanel(new GridLayout(3, 4, 5, 5));

        JButton btnInserir = new JButton("Inserir");
        JButton btnRemover = new JButton("Remover");
        JButton btnBuscar = new JButton("Buscar");
        JButton btnAltura = new JButton("Altura");

        JButton btnPreOrdem = new JButton("Pré-Ordem");
        JButton btnEmOrdem = new JButton("Em Ordem");
        JButton btnPosOrdem = new JButton("Pós-Ordem");
        JButton btnLimpar = new JButton("Limpar");

        JButton btnProfundidade = new JButton("Profundidade");
        JButton btnCaminhoRaiz = new JButton("Caminho Raiz → Nó");
        JButton btnCaminhoEntre = new JButton("Caminho Nó ↔ Nó");
        JButton btnLCA = new JButton("LCA (Ancestral)");

        painelBotoes.add(btnInserir);
        painelBotoes.add(btnRemover);
        painelBotoes.add(btnBuscar);
        painelBotoes.add(btnAltura);

        painelBotoes.add(btnPreOrdem);
        painelBotoes.add(btnEmOrdem);
        painelBotoes.add(btnPosOrdem);
        painelBotoes.add(btnLimpar);

        painelBotoes.add(btnProfundidade);
        painelBotoes.add(btnCaminhoRaiz);
        painelBotoes.add(btnCaminhoEntre);
        painelBotoes.add(btnLCA);

        // PAINEL SUPERIOR
        JPanel painelSuperior = new JPanel(new BorderLayout());
        painelSuperior.add(painelEntrada, BorderLayout.NORTH);
        painelSuperior.add(painelBotoes, BorderLayout.CENTER);
        add(painelSuperior, BorderLayout.NORTH);

        // HISTÓRICO
        JPanel painelHistorico = new JPanel(new BorderLayout());
        painelHistorico.setBorder(BorderFactory.createTitledBorder("🕘 Histórico (2 últimos comandos)"));
        painelHistorico.add(new JScrollPane(listaHistorico), BorderLayout.CENTER);
        add(painelHistorico, BorderLayout.WEST);

        // ÁREA DE SAÍDA
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

        btnProfundidade.addActionListener(e -> mostrarProfundidade());
        btnCaminhoRaiz.addActionListener(e -> mostrarCaminhoDaRaiz());
        btnCaminhoEntre.addActionListener(e -> mostrarCaminhoEntreNos());
        btnLCA.addActionListener(e -> mostrarLCA());

        // ====== JANELA ======
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // ================= MÉTODOS =================

    private Pessoa criarPessoaPorNome(String nome) {
        return new Pessoa(nome, 0, "");
    }

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
        arvore.remover(criarPessoaPorNome(nome));
        adicionarAoHistorico("❌ Removido (se existia): " + nome);
        limparCampos();
    }

    private void buscarPessoa() {
        String nome = txtNome.getText();
        boolean encontrado = arvore.buscar(criarPessoaPorNome(nome));
        adicionarAoHistorico(encontrado ? "🔎 Encontrado: " + nome : "🚫 Não encontrado: " + nome);
        limparCampos();
    }

    private void mostrarAltura() {
        txtSaida.append("\n🌲 Altura da Árvore: " + arvore.altura() + "\n");
        adicionarAoHistorico("📏 Altura consultada");
    }

    private void mostrarPercurso(String tipo) {
        txtSaida.append("\n=== PERCURSO " + tipo + "-ORDEM ===\n");
        List<Pessoa> lista;

        switch (tipo) {
            case "PRE": lista = arvore.preOrdem(); break;
            case "EM": lista = arvore.emOrdem(); break;
            default:   lista = arvore.posOrdem(); break;
        }

        for (Pessoa p : lista)
            txtSaida.append(p + "\n");

        adicionarAoHistorico("📋 Percurso " + tipo + "-Ordem mostrado");
    }

    private void mostrarProfundidade() {
        String nome = txtNome.getText();
        int prof = arvore.profundidade(criarPessoaPorNome(nome));

        txtSaida.append("\n📌 Profundidade de " + nome + ": " + prof + "\n");
        adicionarAoHistorico("📌 Profundidade consultada");
    }

    private void mostrarCaminhoDaRaiz() {
        String nome = txtNome.getText();

        List<Pessoa> caminho = arvore.caminhoAte(criarPessoaPorNome(nome));

        txtSaida.append("\n🛤️ Caminho da raiz até " + nome + ":\n");
        if (caminho.isEmpty())
            txtSaida.append("❌ Nó não encontrado.\n");
        else
            caminho.forEach(n -> txtSaida.append(n + "\n"));

        adicionarAoHistorico("🛤️ Caminho raiz → " + nome);
    }

    private void mostrarCaminhoEntreNos() {
        String n1 = txtNome.getText();
        String n2 = txtNome2.getText();

        List<Pessoa> caminho = arvore.caminhoEntre(
                criarPessoaPorNome(n1),
                criarPessoaPorNome(n2)
        );

        txtSaida.append("\n🛤️ Caminho entre " + n1 + " ↔ " + n2 + ":\n");
        if (caminho.isEmpty())
            txtSaida.append("❌ Um dos nós não existe.\n");
        else
            caminho.forEach(n -> txtSaida.append(n + "\n"));

        adicionarAoHistorico("🛤️ Caminho entre nós");
    }

    private void mostrarLCA() {
        String n1 = txtNome.getText();
        String n2 = txtNome2.getText();

        Pessoa lca = arvore.ancestralComum(
                criarPessoaPorNome(n1),
                criarPessoaPorNome(n2)
        );

        txtSaida.append("\n👴 LCA entre " + n1 + " e " + n2 + ":\n");
        txtSaida.append((lca == null) ? "❌ Não encontrado.\n" : lca + "\n");

        adicionarAoHistorico("👴 LCA consultado");
    }

    private void limparSaida() {
        txtSaida.setText("");
        adicionarAoHistorico("🧹 Tela limpa");
    }

    private void limparCampos() {
        txtNome.setText("");
        txtIdade.setText("");
        txtCpf.setText("");
        txtNome2.setText("");
    }

    // ================= HISTÓRICO =================
    private void adicionarAoHistorico(String comando) {
        comandosRecentes.addFirst(comando);

        if (comandosRecentes.size() > 2)
            comandosRecentes.removeLast();

        historicoModel.clear();
        for (String c : comandosRecentes)
            historicoModel.addElement(c);
    }
}
