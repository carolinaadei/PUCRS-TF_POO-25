import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.Normalizer;
import java.util.ArrayList;

public class App {

    private ArrayList<Paciente> pacientes = new ArrayList<>();
    private static ArrayList<EquipeMedica> listaEquipe = new ArrayList<>();
    private ItemHospitalar itemHospitalar;
    private static JFrame frame;
    private JTable tabela;
    private DefaultTableModel modeloTabela;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new App().mostrarEscolha());
    }

    public void mostrarEscolha() {
        JFrame escolhaFrame = new JFrame("Escolha o modo");
        escolhaFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        escolhaFrame.setSize(300, 100);
        escolhaFrame.setLayout(new FlowLayout());

        JButton btnPrincipal = new JButton("Consulta principal");
        JButton btnTecnica = new JButton("Consulta técnica");

        btnPrincipal.addActionListener(e -> {
            escolhaFrame.dispose();
            iniciarPrincipal();
        });

        btnTecnica.addActionListener(e -> {
            escolhaFrame.dispose();
            iniciarTecnica();
        });

        escolhaFrame.add(btnPrincipal);
        escolhaFrame.add(btnTecnica);
        escolhaFrame.setLocationRelativeTo(null);
        escolhaFrame.setVisible(true);
    }

    private void inicializarPacientes() {
        if (pacientes.isEmpty()) {
            pacientes.add(new Ambulatorial("João Diniz", "01/01/1990", 30, "12345678900", 'M', "joao@mail.com", "Rua A", "1111-2222", "Gripe", "Dra. Cecília Brandão - Médico", "Dipirona", 1, 150.0));
            pacientes.add(new Internado("Maria Bastos", "02/02/1985", 40, "98765432100", 'F', "maria@mail.com", "Rua B", "3333-4444", "Pneumonia", "Dr. Benjamin Rocha - Médico", "Soro fisiológico", 5, 600.0));
        }
    }

    public void iniciarPrincipal() {
        inicializarPacientes();

        frame = new JFrame("Sistema de Pacientes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);
        frame.setLayout(new BorderLayout());

        modeloTabela = new DefaultTableModel(new String[]{"Nome", "Idade", "Tipo", "Diagnostico", "Detalhe", "Responsável", "Custo Total"}, 0);
        tabela = new JTable(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabela);
        frame.add(scrollPane, BorderLayout.CENTER);

        atualizarTabela(pacientes);

        JPanel painelInferior = new JPanel();
        JTextField campoBusca = new JTextField(15);
        JButton btnCadastrar = new JButton("Cadastrar");
        JButton btnExcluir = new JButton("Excluir");
        JButton btnEstatisticas = new JButton("Estatísticas");
        JButton btnVoltar = new JButton("Voltar");

        painelInferior.add(new JLabel("Nome:"));
        painelInferior.add(campoBusca);
        painelInferior.add(btnCadastrar);
        painelInferior.add(btnExcluir);
        painelInferior.add(btnEstatisticas);
        painelInferior.add(btnVoltar);

        frame.add(painelInferior, BorderLayout.SOUTH);

        btnCadastrar.addActionListener(e -> cadastrarPaciente());
        btnExcluir.addActionListener(e -> removerPaciente());
        btnEstatisticas.addActionListener(e -> mostrarEstatisticas());

        btnVoltar.addActionListener(e -> {
            frame.dispose();
            mostrarEscolha();
        });

        frame.setVisible(true);

        campoBusca.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                filtrar();
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                filtrar();
            }

            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                filtrar();
            }

            private void filtrar() {
                String termo = removerAcentos(campoBusca.getText());
                ArrayList<Paciente> encontrados = new ArrayList<>();
                for (Paciente p : pacientes) {
                    if (removerAcentos(p.getNome()).contains(termo)) {
                        encontrados.add(p);
                    }
                }
                atualizarTabela(encontrados);
            }
        });
    }

    private String removerAcentos(String texto) {
        return Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("[\\p{InCombiningDiacriticalMarks}]", "")
                .toLowerCase();
    }

    private void atualizarTabela(ArrayList<Paciente> lista) {
        modeloTabela.setRowCount(0);
        for (Paciente p : lista) {
            String tipo = (p instanceof Internado) ? "Internado" : "Ambulatorial";
            String detalhe = (p instanceof Internado)
                    ? ((Internado) p).getDiasInternado() + " dias"
                    : ((Ambulatorial) p).getQtdConsultas() + " consultas";
            modeloTabela.addRow(new Object[]{p.getNome(), p.getIdade(), tipo, p.getDiagnostico(), detalhe, p.getResponsavel(), String.format("R$ %.2f", p.getCustoTotal())});
        }
    }

    private void cadastrarPaciente() {
        String nome = JOptionPane.showInputDialog(frame, "Nome do paciente:");
        if (nome == null || nome.trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Nome é obrigatório!");
            return;
        }

        String dataNasc = JOptionPane.showInputDialog(frame, "Data de nascimento (dd/mm/aaaa):");
        if (dataNasc == null || dataNasc.trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Data de nascimento é obrigatória!");
            return;
        }

        int idade;
        try {
            String idadeStr = JOptionPane.showInputDialog(frame, "Idade:");
            if (idadeStr == null || idadeStr.trim().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Idade é obrigatória!");
                return;
            }
            idade = Integer.parseInt(idadeStr);
            if (idade < 0 || idade > 150) {
                JOptionPane.showMessageDialog(frame, "Idade deve estar entre 0 e 150 anos!");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Idade inválida!");
            return;
        }

        String cpf = JOptionPane.showInputDialog(frame, "CPF:");
        if (cpf == null || cpf.trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "CPF é obrigatório!");
            return;
        }

        String sexoStr = JOptionPane.showInputDialog(frame, "Sexo (M/F):");
        if (sexoStr == null || sexoStr.trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Sexo é obrigatório!");
            return;
        }
        char sexo = sexoStr.toUpperCase().charAt(0);
        if (sexo != 'M' && sexo != 'F') {
            JOptionPane.showMessageDialog(frame, "Sexo deve ser M ou F!");
            return;
        }

        String email = JOptionPane.showInputDialog(frame, "E-mail:");
        if (email == null) email = "";

        String endereco = JOptionPane.showInputDialog(frame, "Endereço:");
        if (endereco == null) endereco = "";

        String telefone = JOptionPane.showInputDialog(frame, "Telefone:");
        if (telefone == null) telefone = "";

        String diagnostico = JOptionPane.showInputDialog(frame, "Diagnóstico:");
        if (diagnostico == null || diagnostico.trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Diagnóstico é obrigatório!");
            return;
        }

        String responsavel = escolherResponsavel();
        if (responsavel == null) responsavel = "";

        String nomeItem = escolherItem();
        if (nomeItem == null) nomeItem = "";

        String[] opcoes = {"Ambulatorial", "Internado"};
        int tipo = JOptionPane.showOptionDialog(frame, "Tipo de paciente", "Tipo",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, opcoes, opcoes[0]);

        if (tipo == 0) {
            int qntConsultas;
            try {
                String consultasStr = JOptionPane.showInputDialog(frame, "Consultas realizadas:");
                if (consultasStr == null || consultasStr.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Número de consultas é obrigatório!");
                    return;
                }
                qntConsultas = Integer.parseInt(consultasStr);
                if (qntConsultas < 0) {
                    JOptionPane.showMessageDialog(frame, "Número de consultas não pode ser negativo!");
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Número de consultas inválido!");
                return;
            }

            double custoConsulta;
            try {
                String custoStr = JOptionPane.showInputDialog(frame, "Valor por consulta:");
                if (custoStr == null || custoStr.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Valor da consulta é obrigatório!");
                    return;
                }
                custoConsulta = Double.parseDouble(custoStr);
                if (custoConsulta < 0) {
                    JOptionPane.showMessageDialog(frame, "Valor da consulta não pode ser negativo!");
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Valor da consulta inválido!");
                return;
            }

            pacientes.add(new Ambulatorial(nome, dataNasc, idade, cpf, sexo, email, endereco, telefone, diagnostico, responsavel, nomeItem, qntConsultas, custoConsulta));

        } else if (tipo == 1) {
            int diasInternado;
            try {
                String diasStr = JOptionPane.showInputDialog(frame, "Dias internado:");
                if (diasStr == null || diasStr.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Número de dias é obrigatório!");
                    return;
                }
                diasInternado = Integer.parseInt(diasStr);
                if (diasInternado < 0) {
                    JOptionPane.showMessageDialog(frame, "Número de dias não pode ser negativo!");
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Número de dias inválido!");
                return;
            }

            double custoDiario;
            try {
                String custoStr = JOptionPane.showInputDialog(frame, "Valor da diária:");
                if (custoStr == null || custoStr.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Valor da diária é obrigatório!");
                    return;
                }
                custoDiario = Double.parseDouble(custoStr);
                if (custoDiario < 0) {
                    JOptionPane.showMessageDialog(frame, "Valor da diária não pode ser negativo!");
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Valor da diária inválido!");
                return;
            }

            pacientes.add(new Internado(nome, dataNasc, idade, cpf, sexo, email, endereco, telefone, diagnostico, responsavel, nomeItem, diasInternado, custoDiario));
        } else {
            return;
        }

        atualizarTabela(pacientes);
        JOptionPane.showMessageDialog(frame, "Paciente cadastrado com sucesso!");
    }

    private String escolherResponsavel() {
        if (listaEquipe.isEmpty()) {
            listarEquipe();
        }

        String[] nomesEquipe = new String[listaEquipe.size() + 1];
        nomesEquipe[0] = "Nenhum";
        for (int i = 0; i < listaEquipe.size(); i++) {
            nomesEquipe[i + 1] = listaEquipe.get(i).getNome() + " - " + listaEquipe.get(i).getCargo();
        }

        String responsavel = (String) JOptionPane.showInputDialog(
                frame,
                "Selecione o responsável (opcional):",
                "Responsável",
                JOptionPane.PLAIN_MESSAGE,
                null,
                nomesEquipe,
                nomesEquipe[0]);

        if (responsavel != null && responsavel.equals("Nenhum")) {
            responsavel = "";
        }

        return responsavel;
    }

    private void removerPaciente() {
        if (pacientes.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Não há pacientes cadastrados para remover.",
                    "Lista Vazia", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String[] nomesPacientes = new String[pacientes.size()];
        for (int i = 0; i < pacientes.size(); i++) {
            Paciente p = pacientes.get(i);
            String tipo = (p instanceof Internado) ? "Internado" : "Ambulatorial";
            nomesPacientes[i] = p.getNome() + " - " + tipo + " - " + p.getDiagnostico();
        }

        String pacienteSelecionado = (String) JOptionPane.showInputDialog(
                frame,
                "Selecione o paciente que deseja remover:",
                "Remover Paciente",
                JOptionPane.PLAIN_MESSAGE,
                null,
                nomesPacientes,
                nomesPacientes[0]
        );

        if (pacienteSelecionado == null) {
            return;
        }

        String nomeSelecionado = pacienteSelecionado.split(" - ")[0];
        Paciente pacienteParaRemover = null;

        for (Paciente p : pacientes) {
            if (p.getNome().equals(nomeSelecionado)) {
                pacienteParaRemover = p;
                break;
            }
        }

        if (pacienteParaRemover != null) {
            int confirmacao = JOptionPane.showConfirmDialog(
                    frame,
                    "Tem certeza que deseja remover o paciente:\n" +
                            pacienteParaRemover.getNome() + "?\n\n" +
                            "Esta ação não pode ser desfeita.",
                    "Confirmar Remoção",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (confirmacao == JOptionPane.YES_OPTION) {
                pacientes.remove(pacienteParaRemover);
                atualizarTabela(pacientes);
                JOptionPane.showMessageDialog(frame,
                        "Paciente " + pacienteParaRemover.getNome() + " removido com sucesso!",
                        "Remoção Concluída",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Erro ao localizar o paciente selecionado.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarEstatisticas() {
        JFrame escolhaFrame = new JFrame("Escolha a Estatistica");
        escolhaFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        escolhaFrame.setSize(300, 190);
        escolhaFrame.setLayout(new FlowLayout());

        JButton btnEstatisticaCusto = new JButton("Estatisticas por custo");
        JButton btnEstatisticaDiagnostico = new JButton("Estatisticas por diagnostico");
        JButton btnEstatisticaDetalhado = new JButton("Estatisticas detalhadas");
        JButton btnVoltar = new JButton("Voltar");

        JPanel painelBotoes = new JPanel(new GridLayout(4, 1, 0, 10));
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        Dimension tamanhoPadrao = new Dimension(220, 30);

        btnEstatisticaCusto.setPreferredSize(tamanhoPadrao);
        btnEstatisticaDiagnostico.setPreferredSize(tamanhoPadrao);
        btnEstatisticaDetalhado.setPreferredSize(tamanhoPadrao);
        btnVoltar.setPreferredSize(tamanhoPadrao);

        btnEstatisticaCusto.addActionListener(e -> {
            escolhaFrame.dispose();
            Estatistica.mostrarEstatisticasCustos(pacientes);
        });

        btnEstatisticaDiagnostico.addActionListener(e -> {
            escolhaFrame.dispose();
            Estatistica.mostrarEstatisticasPorDiagnostico(pacientes);
        });

        btnEstatisticaDetalhado.addActionListener(e -> {
            escolhaFrame.dispose();
            Estatistica.mostrar(pacientes);
        });

        btnVoltar.addActionListener(e -> {
            escolhaFrame.dispose();
        });

        escolhaFrame.add(btnEstatisticaCusto);
        escolhaFrame.add(btnEstatisticaDiagnostico);
        escolhaFrame.add(btnEstatisticaDetalhado);
        escolhaFrame.add(btnVoltar);
        escolhaFrame.setLocationRelativeTo(null);
        escolhaFrame.setVisible(true);
    }

    private String escolherItem() {
        ItemHospitalar[] itens = ItemHospitalar.values();
        String[] nomeItens = new String[itens.length + 1];
        nomeItens[0] = "Nenhum";

        for (int i = 0; i < itens.length; i++) {
            nomeItens[i + 1] = itens[i].getNomeItem();
        }

        String item = (String) JOptionPane.showInputDialog(
                frame,
                "Selecione o item hospitalar:",
                "Item Hospitalar",
                JOptionPane.PLAIN_MESSAGE,
                null,
                nomeItens,
                nomeItens[0]);

        if (item != null && item.equals("Nenhum")) {
            item = "";
        }

        return item;
    }

    public void iniciarTecnica() {
        inicializarPacientes();

        frame = new JFrame("Sistema Técnico");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);
        frame.setLayout(new BorderLayout());

        modeloTabela = new DefaultTableModel(new String[]{"Paciente", "Responsável", "Diagnostico", "Itens Hospitalares"}, 0);
        tabela = new JTable(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabela);
        frame.add(scrollPane, BorderLayout.CENTER);

        atualizaTabelaTecnica(pacientes, listaEquipe);

        JPanel painelInferior = new JPanel();
        JTextField campoBusca = new JTextField(15);
        JButton btnCadastrarEquipe = new JButton("Cadastrar equipe");
        JButton btnListarEquipe = new JButton("Listar equipe");
        JButton btnAtribuirItens = new JButton("Atribuir Itens");
        JButton btnVoltar = new JButton("Voltar");

        painelInferior.add(new JLabel("Nome:"));
        painelInferior.add(campoBusca);
        painelInferior.add(btnCadastrarEquipe);
        painelInferior.add(btnListarEquipe);
        painelInferior.add(btnAtribuirItens);
        painelInferior.add(btnVoltar);

        frame.add(painelInferior, BorderLayout.SOUTH);

        btnCadastrarEquipe.addActionListener(e -> cadastrarEquipe());

        btnListarEquipe.addActionListener(e -> listarEquipe());

        btnAtribuirItens.addActionListener(e -> atribuirItem());

        btnVoltar.addActionListener(e -> {
            frame.dispose();
            mostrarEscolha();
        });

        frame.setVisible(true);

        campoBusca.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                filtrar();
            }
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                filtrar();
            }
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                filtrar();
            }
            private void filtrar() {
                String termo = removerAcentos(campoBusca.getText());
                ArrayList<Paciente> encontrados = new ArrayList<>();
                for (Paciente p : pacientes) {
                    if (removerAcentos(p.getNome()).contains(termo)) {
                        encontrados.add(p);
                    }
                }
                atualizaTabelaTecnica(encontrados, listaEquipe);
            }
        });
    }

    private void atribuirItem() {
        JFrame escolhaFrame = new JFrame("Atribuição de itens");
        escolhaFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        escolhaFrame.setSize(300, 190);
        escolhaFrame.setLocationRelativeTo(frame);
        escolhaFrame.setLayout(new FlowLayout());

        JButton btnAtribuirItens = new JButton("Atribuir item para paciente");
        JButton btnListarAtribuidos = new JButton("Listar itens de um paciente");
        JButton btnVoltar = new JButton("Voltar");

        JPanel painelBotoes = new JPanel(new GridLayout(4, 1, 0, 10));
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        Dimension tamanhoPadrao = new Dimension(220, 30);

        btnAtribuirItens.setPreferredSize(tamanhoPadrao);
        btnListarAtribuidos.setPreferredSize(tamanhoPadrao);
        btnVoltar.setPreferredSize(tamanhoPadrao);

        painelBotoes.add(btnAtribuirItens);
        painelBotoes.add(btnListarAtribuidos);
        painelBotoes.add(btnVoltar);

        escolhaFrame.add(painelBotoes);

        btnAtribuirItens.addActionListener(e -> {
            escolhaFrame.dispose();
            atribuirItemParaPaciente();
        });

        btnListarAtribuidos.addActionListener(e -> {
            escolhaFrame.dispose();
            gerenciarItensPaciente();
        });

        btnVoltar.addActionListener(e -> {
            escolhaFrame.dispose();
        });

        escolhaFrame.setVisible(true);
    }

    private void atribuirItemParaPaciente() {
        if (pacientes.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Nenhum paciente cadastrado.");
            return;
        }

        String[] nomesPacientes = pacientes.stream()
                .map(Paciente::getNome)
                .toArray(String[]::new);

        String pacienteSelecionado = (String) JOptionPane.showInputDialog(
                frame,
                "Selecione o paciente:",
                "Escolher Paciente",
                JOptionPane.PLAIN_MESSAGE,
                null,
                nomesPacientes,
                nomesPacientes[0]);

        if (pacienteSelecionado == null) {
            return;
        }

        Paciente paciente = pacientes.stream()
                .filter(p -> p.getNome().equals(pacienteSelecionado))
                .findFirst()
                .orElse(null);

        if (paciente == null) {
            JOptionPane.showMessageDialog(frame, "Paciente não encontrado.");
            return;
        }

        ItemHospitalar[] itens = ItemHospitalar.values();
        String[] nomeItens = new String[itens.length];

        for (int i = 0; i < itens.length; i++) {
            nomeItens[i] = itens[i].getNomeItem() + " (" + itens[i].getTipo() + ")";
        }

        String itemSelecionado = (String) JOptionPane.showInputDialog(
                frame,
                "Selecione o item hospitalar:",
                "Escolher Item",
                JOptionPane.PLAIN_MESSAGE,
                null,
                nomeItens,
                nomeItens[0]);

        if (itemSelecionado == null) {
            return;
        }

        String nomeItem = itemSelecionado.split(" \\(")[0];

        String itensAtuais = paciente.getNomeItem();
        if (!itensAtuais.isEmpty() && itensAtuais.contains(nomeItem)) {
            JOptionPane.showMessageDialog(frame, "Este item já está atribuído ao paciente.");
            return;
        }

        if (itensAtuais.isEmpty()) {
            paciente.setNomeItem(nomeItem);
        } else {
            paciente.setNomeItem(itensAtuais + ", " + nomeItem);
        }

        atualizaTabelaTecnica(pacientes, listaEquipe);
        JOptionPane.showMessageDialog(frame, "Item '" + nomeItem + "' atribuído com sucesso ao paciente " + paciente.getNome() + ".");
    }

    private void gerenciarItensPaciente() {
        if (pacientes.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Nenhum paciente cadastrado.");
            return;
        }

        String[] nomesPacientes = pacientes.stream()
                .map(Paciente::getNome)
                .toArray(String[]::new);

        String pacienteSelecionado = (String) JOptionPane.showInputDialog(
                frame,
                "Selecione o paciente:",
                "Escolher Paciente",
                JOptionPane.PLAIN_MESSAGE,
                null,
                nomesPacientes,
                nomesPacientes[0]);

        if (pacienteSelecionado == null) {
            return;
        }

        Paciente paciente = pacientes.stream()
                .filter(p -> p.getNome().equals(pacienteSelecionado))
                .findFirst()
                .orElse(null);

        if (paciente == null) {
            JOptionPane.showMessageDialog(frame, "Paciente não encontrado.");
            return;
        }

        exibirGerenciadorItens(paciente);
    }

    private void exibirGerenciadorItens(Paciente paciente) {
        JFrame gerenciadorFrame = new JFrame("Gerenciar Itens - " + paciente.getNome());
        gerenciadorFrame.setSize(500, 400);
        gerenciadorFrame.setLocationRelativeTo(frame);
        gerenciadorFrame.setLayout(new BorderLayout());

        String itensTexto = paciente.getNomeItem();
        String[] itensArray;

        if (itensTexto.isEmpty()) {
            itensArray = new String[]{"Nenhum item atribuído"};
        } else {
            itensArray = itensTexto.split(", ");
        }

        JList<String> listaItens = new JList<>(itensArray);
        listaItens.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(listaItens);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Itens Atribuídos"));

        JPanel painelBotoes = new JPanel(new FlowLayout());
        JButton btnRemover = new JButton("Remover Item Selecionado");
        JButton btnAtribuirNovo = new JButton("Atribuir Novo Item");
        JButton btnFechar = new JButton("Fechar");

        btnRemover.addActionListener(e -> {
            String itemSelecionado = listaItens.getSelectedValue();
            if (itemSelecionado == null || itemSelecionado.equals("Nenhum item atribuído")) {
                JOptionPane.showMessageDialog(gerenciadorFrame, "Selecione um item para remover.");
                return;
            }

            int confirmacao = JOptionPane.showConfirmDialog(
                    gerenciadorFrame,
                    "Tem certeza que deseja remover o item '" + itemSelecionado + "'?",
                    "Confirmar Remoção",
                    JOptionPane.YES_NO_OPTION);

            if (confirmacao == JOptionPane.YES_OPTION) {
                removerItemDoPaciente(paciente, itemSelecionado);
                gerenciadorFrame.dispose();
                exibirGerenciadorItens(paciente); // Reabrir com lista atualizada
                atualizaTabelaTecnica(pacientes, listaEquipe);
            }
        });

        btnAtribuirNovo.addActionListener(e -> {
            gerenciadorFrame.dispose();
            atribuirItemParaPacienteEspecifico(paciente);
        });

        btnFechar.addActionListener(e -> gerenciadorFrame.dispose());

        painelBotoes.add(btnAtribuirNovo);
        painelBotoes.add(btnRemover);
        painelBotoes.add(btnFechar);

        gerenciadorFrame.add(scrollPane, BorderLayout.CENTER);
        gerenciadorFrame.add(painelBotoes, BorderLayout.SOUTH);

        gerenciadorFrame.setVisible(true);
    }

    private void removerItemDoPaciente(Paciente paciente, String itemParaRemover) {
        String itensAtuais = paciente.getNomeItem();

        if (itensAtuais.isEmpty()) {
            return;
        }

        String[] itensArray = itensAtuais.split(", ");
        StringBuilder novosItens = new StringBuilder();

        for (String item : itensArray) {
            if (!item.equals(itemParaRemover)) {
                if (novosItens.length() > 0) {
                    novosItens.append(", ");
                }
                novosItens.append(item);
            }
        }

        paciente.setNomeItem(novosItens.toString());
        JOptionPane.showMessageDialog(frame, "Item '" + itemParaRemover + "' removido com sucesso.");
    }

    private void atribuirItemParaPacienteEspecifico(Paciente paciente) {
        ItemHospitalar[] itens = ItemHospitalar.values();
        String[] nomeItens = new String[itens.length];

        for (int i = 0; i < itens.length; i++) {
            nomeItens[i] = itens[i].getNomeItem() + " (" + itens[i].getTipo() + ")";
        }

        String itemSelecionado = (String) JOptionPane.showInputDialog(
                frame,
                "Selecione o item hospitalar para " + paciente.getNome() + ":",
                "Escolher Item",
                JOptionPane.PLAIN_MESSAGE,
                null,
                nomeItens,
                nomeItens[0]);

        if (itemSelecionado == null) {
            exibirGerenciadorItens(paciente);
            return;
        }

        String nomeItem = itemSelecionado.split(" \\(")[0];

        String itensAtuais = paciente.getNomeItem();
        if (!itensAtuais.isEmpty() && itensAtuais.contains(nomeItem)) {
            JOptionPane.showMessageDialog(frame, "Este item já está atribuído ao paciente.");
            exibirGerenciadorItens(paciente);
            return;
        }

        if (itensAtuais.isEmpty()) {
            paciente.setNomeItem(nomeItem);
        } else {
            paciente.setNomeItem(itensAtuais + ", " + nomeItem);
        }

        atualizaTabelaTecnica(pacientes, listaEquipe);
        JOptionPane.showMessageDialog(frame, "Item '" + nomeItem + "' atribuído com sucesso!");
        exibirGerenciadorItens(paciente);
    }

    private void atualizaTabelaTecnica(ArrayList<Paciente> lista, ArrayList<EquipeMedica> listaEquipe) {
        modeloTabela.setRowCount(0);
        for (Paciente p : lista) {
            modeloTabela.addRow(new Object[]{
                    p.getNome(),
                    p.getResponsavel(),
                    p.getDiagnostico(),
                    p.getNomeItem()
            });
        }
    }

    private void cadastrarEquipe() {
        String nome = JOptionPane.showInputDialog(frame, "Nome do funcionário:");
        if (nome == null || nome.trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Nome é obrigatório!");
            return;
        }

        String dataNasc = JOptionPane.showInputDialog(frame, "Data de nascimento (dd/mm/aaaa):");
        if (dataNasc == null || dataNasc.trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Data de nascimento é obrigatória!");
            return;
        }

        int idade;
        try {
            String idadeStr = JOptionPane.showInputDialog(frame, "Idade:");
            if (idadeStr == null || idadeStr.trim().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Idade é obrigatória!");
                return;
            }
            idade = Integer.parseInt(idadeStr);
            if (idade < 18 || idade > 80) {
                JOptionPane.showMessageDialog(frame, "Idade deve estar entre 18 e 80 anos!");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Idade inválida!");
            return;
        }

        String cpf = JOptionPane.showInputDialog(frame, "CPF:");
        if (cpf == null || cpf.trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "CPF é obrigatório!");
            return;
        }

        String sexoStr = JOptionPane.showInputDialog(frame, "Sexo (M/F):");
        if (sexoStr == null || sexoStr.trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Sexo é obrigatório!");
            return;
        }
        char sexo = sexoStr.toUpperCase().charAt(0);
        if (sexo != 'M' && sexo != 'F') {
            JOptionPane.showMessageDialog(frame, "Sexo deve ser M ou F!");
            return;
        }

        String email = JOptionPane.showInputDialog(frame, "E-mail:");
        if (email == null) email = "";

        String endereco = JOptionPane.showInputDialog(frame, "Endereço:");
        if (endereco == null) endereco = "";

        String telefone = JOptionPane.showInputDialog(frame, "Telefone:");
        if (telefone == null) telefone = "";

        String cargo = JOptionPane.showInputDialog(frame, "Cargo:");
        if (cargo == null || cargo.trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Cargo é obrigatório!");
            return;
        }

        String registroProfissional = JOptionPane.showInputDialog(frame, "Registro Profissional:");
        if (registroProfissional == null || registroProfissional.trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Registro Profissional é obrigatório!");
            return;
        }

        String especialidade = JOptionPane.showInputDialog(frame, "Especialidade:");
        if (especialidade == null || especialidade.trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Especialidade é obrigatória!");
            return;
        }

        EquipeMedica novaEquipe = new EquipeMedica(nome, dataNasc, idade, cpf, sexo, email, endereco, telefone, cargo, registroProfissional, especialidade);
        listaEquipe.add(novaEquipe);
        JOptionPane.showMessageDialog(frame, "Membro da equipe cadastrado com sucesso!");
    }

    private static void listarEquipe() {
        if (listaEquipe.isEmpty()) {
            listaEquipe.add(new EquipeMedica("Dr. Benjamin Rocha", "15/03/1980", 44, "123.456.789-00", 'M', "benjaminr@gmail.com", "Rua A, 123", "(11) 99999-1111", "Médico", "CRM 26589", "Pneumologista"));
            listaEquipe.add(new EquipeMedica("Dra. Cecília Brandão", "05/06/1999", 26, "25698478213", 'F', "cecibrandao@gmail.com", "Rua B, 568", "(11)99925-7863", "Médico", "CRM 23697", "Geral"));
            listaEquipe.add(new EquipeMedica("Enf. Lucas Pereira", "10/05/1990", 34, "321.654.987-00", 'M', "lucas.pereira@hospital.com", "Rua D, 321", "(11) 96666-4444", "Enfermeiro", "COREN 12345", "Enfermeiro Geral"));
            listaEquipe.add(new EquipeMedica("Enf. Mariana Souza", "03/11/1992", 32, "654.321.987-00", 'F', "mariana.souza@hospital.com", "Rua E, 456", "(11) 95555-5555", "Enfermeiro", "COREN 54321", "Enfermeiro Pediátrico"));
            listaEquipe.add(new EquipeMedica("Dr. Felipe Andrade", "18/08/1987", 37, "789.123.456-00", 'M', "felipe.andrade@hospital.com", "Rua F, 789", "(11) 94444-6666", "Fisioterapeuta", "CREFITO 67890", "Fisioterapia ortopédica"));
            listaEquipe.add(new EquipeMedica("Dra. Beatriz Lima", "25/12/1982", 42, "159.753.486-00", 'F', "beatriz.lima@hospital.com", "Rua G, 101", "(11) 93333-7777", "Odontologista", "CRO 24680", "Odontologia Clínica"));
        }

        JFrame filtroFrame = new JFrame("Filtrar Equipe Médica");
        filtroFrame.setSize(500, 400);
        filtroFrame.setLayout(new BorderLayout());

        String[] opcoes = {"Todos", "Médico", "Enfermeiro", "Outros"};
        JComboBox<String> comboFiltro = new JComboBox<>(opcoes);

        JButton btnFechar = new JButton("Fechar");

        JPanel painelTopo = new JPanel();
        painelTopo.add(new JLabel("Filtrar por cargo:"));
        painelTopo.add(comboFiltro);

        JTextArea areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaTexto);

        JPanel painelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelInferior.add(btnFechar);

        filtroFrame.add(painelTopo, BorderLayout.NORTH);
        filtroFrame.add(scroll, BorderLayout.CENTER);
        filtroFrame.add(painelInferior, BorderLayout.SOUTH);

        comboFiltro.addActionListener(e -> {
            String filtro = (String) comboFiltro.getSelectedItem();
            StringBuilder sb = new StringBuilder();

            for (EquipeMedica eq : listaEquipe) {
                boolean exibir = switch (filtro) {
                    case "Médico" -> eq.getCargo().equalsIgnoreCase("Médico");
                    case "Enfermeiro" -> eq.getCargo().equalsIgnoreCase("Enfermeiro");
                    case "Outros" -> !eq.getCargo().equalsIgnoreCase("Médico") && !eq.getCargo().equalsIgnoreCase("Enfermeiro");
                    default -> true;
                };

                if (exibir) {
                    sb.append(eq.getNome()).append(" - ").append(eq.getEspecialidade())
                            .append(" (").append(eq.getCargo()).append(")\n");
                }
            }

            areaTexto.setText(sb.toString().isEmpty() ? "Nenhum membro encontrado com o filtro selecionado." : sb.toString());
        });

        btnFechar.addActionListener(e -> filtroFrame.dispose());

        comboFiltro.setSelectedIndex(0);

        filtroFrame.setLocationRelativeTo(frame);
        filtroFrame.setVisible(true);
    }
}