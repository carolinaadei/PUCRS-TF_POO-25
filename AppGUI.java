
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

    public class AppGUI {

        private ArrayList<Paciente> pacientes = new ArrayList<>();
        private JFrame frame;
        private JTable tabela;
        private DefaultTableModel modeloTabela;

        public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> new AppGUI().iniciar());
        }

        public void iniciar() {
            // Dados de exemplo
            pacientes.add(new Ambulatorial("João", "01/01/1990", 30, "12345678900", 'M', "joao@mail.com", "Rua A", "1111-2222", "Gripe", 4, 50.0));
            pacientes.add(new Internado("Maria", "02/02/1985", 40, "98765432100", 'F', "maria@mail.com", "Rua B", "3333-4444", "Pneumonia", 10, 200.0));


            // Janela principal
            frame = new JFrame("Sistema de Pacientes");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 400);
            frame.setLayout(new BorderLayout());

            // Tabela
            modeloTabela = new DefaultTableModel(new String[]{"Nome", "Idade", "Tipo", "Detalhe"}, 0);
            tabela = new JTable(modeloTabela);
            JScrollPane scrollPane = new JScrollPane(tabela);
            frame.add(scrollPane, BorderLayout.CENTER);

            atualizarTabela(pacientes);

            // Painel de botões
            JPanel painelInferior = new JPanel();
            JTextField campoBusca = new JTextField(15);
            JButton btnBuscar = new JButton("Buscar");
            JButton btnCadastrar = new JButton("Cadastrar");
            JButton btnEstatisticas = new JButton("Estatísticas");
            JButton btnOrdenarIdade = new JButton("Ordenar por idade");

            painelInferior.add(new JLabel("Nome:"));
            painelInferior.add(campoBusca);
            painelInferior.add(btnBuscar);
            painelInferior.add(btnCadastrar);
            painelInferior.add(btnEstatisticas);
            painelInferior.add(btnOrdenarIdade);

            frame.add(painelInferior, BorderLayout.SOUTH);

            // Eventos

            btnBuscar.addActionListener(e -> {
                String termo = campoBusca.getText().toLowerCase();
                ArrayList<Paciente> encontrados = new ArrayList<>();
                for (Paciente p : pacientes) {
                    if (p.getNome().toLowerCase().contains(termo)) {
                        encontrados.add(p);
                    }
                }
                atualizarTabela(encontrados);
            });

            btnCadastrar.addActionListener(e -> cadastrarPaciente());

            btnEstatisticas.addActionListener(e -> mostrarEstatisticas());

            btnOrdenarIdade.addActionListener(e -> {
                Collections.sort(pacientes, new Comparator<Paciente>() {
                    public int compare(Paciente p1, Paciente p2) {
                        return Integer.compare(p1.getIdade(), p2.getIdade());
                    }
                });
                atualizarTabela(pacientes);
            });

            frame.setVisible(true);
        }

        private void atualizarTabela(ArrayList<Paciente> lista) {
            modeloTabela.setRowCount(0);
            for (Paciente p : lista) {
                String tipo = (p instanceof Internado) ? "Internado" : "Ambulatorial";
                String detalhe = (p instanceof Internado)
                        ? ((Internado) p).getDiasInternado() + " dias"
                        : ((Ambulatorial) p).getConsultasRealizadas() + " consultas";
                modeloTabela.addRow(new Object[]{p.getNome(), p.getIdade(), tipo, detalhe});
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

            String[] opcoes = {"Ambulatorial", "Internado"};
            int tipo = JOptionPane.showOptionDialog(frame, "Tipo de paciente", "Tipo",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, opcoes, opcoes[0]);

            if (tipo == 0) {
                int consultas = Integer.parseInt(JOptionPane.showInputDialog(frame, "Consultas realizadas:"));
                pacientes.add(new Ambulatorial(nome, dataNasc, idade, "---.---.---.--", 'M-F', "-", "Cep:", "(DDD) - ", "Diagnóstico", consultas, 100.0));
            } else {
                int dias = Integer.parseInt(JOptionPane.showInputDialog(frame, "Dias internado:"));
                pacientes.add(new Internado(nome, dataNasc, idade, "-", 'M', "-", "-", "-", "Diagnóstico", dias, 300.0));
            }

            atualizarTabela(pacientes);
        }

