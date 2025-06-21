import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class App {

    private ArrayList<Paciente> pacientes = new ArrayList<>();
    private JFrame frame;
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

    public void iniciarPrincipal() {
        pacientes.add(new Ambulatorial("João Diniz", "01/01/1990", 30, "12345678900", 'M', "joao@mail.com", "Rua A", "1111-2222", "Gripe", "Dra. Cecília Brandão", 1, 150));
        pacientes.add(new Internado("Maria Bastos", "02/02/1985", 40, "98765432100", 'F', "maria@mail.com", "Rua B", "3333-4444", "Pneumonia", "Dr. Benjamin Rocha", 10, 550));


        frame = new JFrame("Sistema de Pacientes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);
        frame.setLayout(new BorderLayout());

        modeloTabela = new DefaultTableModel(new String[]{"Nome", "Idade", "Tipo", "Diagnostico", "Detalhe", "Responsável"}, 0);
        tabela = new JTable(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabela);
        frame.add(scrollPane, BorderLayout.CENTER);

        atualizarTabela(pacientes);

        JPanel painelInferior = new JPanel();
        JTextField campoBusca = new JTextField(15);
        JButton btnCadastrar = new JButton("Cadastrar");
        JButton btnEstatisticas = new JButton("Estatísticas");
        JButton btnVoltar = new JButton("⤶");

        painelInferior.add(new JLabel("Nome:"));
        painelInferior.add(campoBusca);
        painelInferior.add(btnCadastrar);
        painelInferior.add(btnEstatisticas);
        painelInferior.add(btnVoltar);

        frame.add(painelInferior, BorderLayout.SOUTH);

        btnCadastrar.addActionListener(e -> cadastrarPaciente());

        btnEstatisticas.addActionListener(e -> Estatistica.mostrar(pacientes));

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
            modeloTabela.addRow(new Object[]{p.getNome(), p.getIdade(), tipo, p.getDiagnostico(), detalhe, p.getResponsavel()});
        }
    }

    private void cadastrarPaciente() {
        String nome = JOptionPane.showInputDialog(frame, "Nome do paciente:");
        if (nome == null || nome.trim().isEmpty()) return;

        String dataNasc = JOptionPane.showInputDialog(frame, "Data de nascimento (dd/mm/aaaa):");
        if (dataNasc == null || dataNasc.trim().isEmpty()) return;

        int idade;
        try {
            idade = Integer.parseInt(JOptionPane.showInputDialog(frame, "Idade:"));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Idade inválida!");
            return;
        }

        String cpf = JOptionPane.showInputDialog(frame, "CPF:");
        if (cpf == null || cpf.trim().isEmpty()) return;

        String sexoStr = JOptionPane.showInputDialog(frame, "Sexo (M/F):");
        if (sexoStr == null || sexoStr.trim().isEmpty()) return;
        char sexo = sexoStr.toUpperCase().charAt(0);

        String email = JOptionPane.showInputDialog(frame, "E-mail:");
        String endereco = JOptionPane.showInputDialog(frame, "Endereço:");
        String telefone = JOptionPane.showInputDialog(frame, "Telefone:");
        String diagnostico = JOptionPane.showInputDialog(frame, "Diagnóstico:");
        String responsavel = JOptionPane.showInputDialog(frame, "Médico responsável:");

        String[] opcoes = {"Ambulatorial", "Internado"};
        int tipo = JOptionPane.showOptionDialog(frame, "Tipo de paciente", "Tipo",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, opcoes, opcoes[0]);

        if (tipo == 0) {
            int consultas;
            try {
                consultas = Integer.parseInt(JOptionPane.showInputDialog(frame, "Consultas realizadas:"));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Número de consultas inválido!");
                return;
            }

            double valorConsulta = Double.parseDouble(JOptionPane.showInputDialog(frame, "Valor por consulta:"));
            pacientes.add(new Ambulatorial(nome, dataNasc, idade, cpf, sexo, email, endereco, telefone, diagnostico, responsavel, consultas, valorConsulta));

        } else {
            int diasInternado;
            try {
                diasInternado = Integer.parseInt(JOptionPane.showInputDialog(frame, "Dias internado:"));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Número de dias inválido!");
                return;
            }

            double diaria = Double.parseDouble(JOptionPane.showInputDialog(frame, "Valor da diária:"));
            pacientes.add(new Internado(nome, dataNasc, idade, cpf, sexo, email, endereco, telefone, diagnostico, responsavel, diasInternado, diaria));
        }

        atualizarTabela(pacientes);
    }

    public void iniciarTecnica() {
        frame = new JFrame("Sistema de Pacientes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);
        frame.setLayout(new BorderLayout());

    }
}
