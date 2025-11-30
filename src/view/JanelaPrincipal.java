package src.view;

import src.model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class JanelaPrincipal extends JFrame {

    private ArvoreBinaria<Pessoa> arvore = new ArvoreBinaria<>();

    // HISTÓRICO
    private DefaultListModel<String> historicoModel = new DefaultListModel<>();
    private JList<String> listaHistorico = new JList<>(historicoModel);
    private LinkedList<String> comandosRecentes = new LinkedList<>();

    // TABELA DE CADASTROS
    private DefaultTableModel tabelaModel = new DefaultTableModel();
    private JTable tabela = new JTable(tabelaModel);

    // BOTÃO REMOVER SELEÇÃO
    private JButton btnRemoverSelecionado = new JButton("Remover Selecionado");

    public JanelaPrincipal() {
        super("🌳 Árvore Binária de Pessoas");
        setLayout(new BorderLayout(10, 10));

        // ================= PAINEL DE BOTÕES =================
        JPanel painelBotoes = new JPanel(new GridLayout(1, 5, 10, 10));

        JButton btnCadastro = new JButton("Cadastrar Pessoa");
        JButton btnPercursos = new JButton("Percursos");
        JButton btnMetricas = new JButton("Métricas");
        JButton btnCaminhos = new JButton("Caminhos");
        JButton btnPopular = new JButton("Popular 10 Pessoas");

        Dimension botaoTamanho = new Dimension(140, 30);
        btnCadastro.setPreferredSize(botaoTamanho);
        btnPercursos.setPreferredSize(botaoTamanho);
        btnMetricas.setPreferredSize(botaoTamanho);
        btnCaminhos.setPreferredSize(botaoTamanho);
        btnPopular.setPreferredSize(botaoTamanho);

        painelBotoes.add(btnCadastro);
        painelBotoes.add(btnPercursos);
        painelBotoes.add(btnMetricas);
        painelBotoes.add(btnCaminhos);
        painelBotoes.add(btnPopular);

        add(painelBotoes, BorderLayout.NORTH);

        // ================= TABELA DE CADASTROS =================
        tabelaModel.addColumn("Nº");
        tabelaModel.addColumn("Nome");
        tabelaModel.addColumn("Idade");
        tabelaModel.addColumn("CPF");

        JScrollPane scrollTabela = new JScrollPane(tabela);
        scrollTabela.setBorder(BorderFactory.createTitledBorder("📋 Cadastros"));

        // Botão remover inicial invisível/desativado
        btnRemoverSelecionado.setEnabled(false);

        JPanel painelCentro = new JPanel(new BorderLayout());
        painelCentro.add(scrollTabela, BorderLayout.CENTER);
        painelCentro.add(btnRemoverSelecionado, BorderLayout.SOUTH);

        add(painelCentro, BorderLayout.CENTER);

        // ================= HISTÓRICO =================
        JPanel painelHistorico = new JPanel(new BorderLayout());
        painelHistorico.setBorder(BorderFactory.createTitledBorder("🕘 Histórico (5 últimos comandos)"));
        painelHistorico.add(new JScrollPane(listaHistorico), BorderLayout.CENTER);
        painelHistorico.setPreferredSize(new Dimension(220, 0));
        add(painelHistorico, BorderLayout.WEST);

        // ====== EVENTOS ======
        btnCadastro.addActionListener(e -> new TelaCadastro(this, arvore).setVisible(true));
        btnPercursos.addActionListener(e -> new TelaPercursos(this, arvore).setVisible(true));
        btnMetricas.addActionListener(e -> new TelaMetricas(this, arvore).setVisible(true));
        btnCaminhos.addActionListener(e -> new TelaCaminhos(this, arvore).setVisible(true));

        // ====== BOTÃO POPULAR ======
        btnPopular.addActionListener(e -> {
            popularComPessoasAleatorias();
            adicionarAoHistorico("⚡ Inseridas 10 pessoas aleatórias");
            atualizarTabela();
        });

        // ================= BOTÃO REMOVER SELEÇÃO =================
        tabela.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                btnRemoverSelecionado.setEnabled(tabela.getSelectedRow() != -1);
            }
        });

        btnRemoverSelecionado.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha != -1) {
                String nome = (String) tabelaModel.getValueAt(linha, 1);
                arvore.remover(new Pessoa(nome, 0, ""));
                adicionarAoHistorico("❌ Removido: " + nome);
                atualizarTabela();
                tabela.clearSelection();
            }
        });

        // ====== JANELA ======
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // ================= MÉTODOS =================

    private void popularComPessoasAleatorias() {
        String[] nomes = {
                "Ana", "Bruno", "Carlos", "Daniela", "Eduardo",
                "Fernanda", "Gabriel", "Helena", "Isabela", "João",
                "Karen", "Luiz", "Mariana", "Nicolas", "Olivia"
        };

        Random r = new Random();

        for (int i = 0; i < 10; i++) {
            String nome = nomes[r.nextInt(nomes.length)];
            int idade = 18 + r.nextInt(40); // idade 18-57
            String cpf = String.valueOf(100000000 + r.nextInt(900000000));

            Pessoa p = new Pessoa(nome, idade, cpf);
            arvore.inserir(p);
        }
    }

    public void adicionarAoHistorico(String comando) {
        comandosRecentes.addFirst(comando);
        if (comandosRecentes.size() > 5)
            comandosRecentes.removeLast();

        historicoModel.clear();
        for (String c : comandosRecentes)
            historicoModel.addElement(c);
    }

    public void atualizarTabela() {
        tabelaModel.setRowCount(0);
        List<Pessoa> lista = arvore.emOrdem();
        int i = 1;
        for (Pessoa p : lista) {
            tabelaModel.addRow(new Object[] { i++, p.getNome(), p.getIdade(), p.getCpf() });
        }
    }
}
