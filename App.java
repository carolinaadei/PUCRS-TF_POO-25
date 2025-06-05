import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDate;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class App extends JFrame {
    List<Paciente> pacientes = new ArrayList<>();
    List<Paciente> pacientesBaixados = new ArrayList<>();

    DefaultTableModel modelo;
    JTable tabela;

    public App() {
        setTitle("Sistema Hospitalar");
        setSize(1000, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        pacientes.add(new Ambulatorial("Carolina de Souza", LocalDate.of(2005, 11, 8), 19, "123.456.789.10", 'F', "carolina@gmail.com", "Rua A", "1234-5678", "Exame de sangue", 3, 150));
        pacientes.add(new Internado("Samantha Martins", LocalDate.of(1985, 11, 2), 19, "456.789.101.11", 'F', "samantha@gmail.com", "Rua B", "9876-5432", "Apendicectomia", 5, 500));
        pacientes.add(new Ambulatorial("Ana Clara Purper", LocalDate.of(1985, 11, 2), 20, "789.101.112.13", 'F', "anaclara@gmail.com", "Rua B", "9876-5432", "Retorno", 1, 200));
        pacientes.add(new Internado("Ana Cristina Schmidt", LocalDate.of(1990, 5, 12), 34, "131.415.161.71", 'F', "anacristina@gmail.com", "Rua A", "1234-5678", "Internacao", 2, 600));

        add(painelPacientes());
        setVisible(true);
    }

    private JPanel painelPacientes() {
        JPanel painel = new JPanel(new BorderLayout());

        String[] colunas = {"Nome", "Tipo", "Idade", "CPF", "Diagnóstico", "Custo Total"};
        modelo = new DefaultTableModel(colunas, 0);
        tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela);
        atualizarTabelaPacientes(pacientes);

        JPanel painelInferior = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));

        JButton btnAmbulatorial = new JButton("Novo Paciente Ambulatorial");
        btnAmbulatorial.setPreferredSize(new Dimension(180, 25));
        btnAmbulatorial.addActionListener(e -> {
            Paciente novo = mostrarFormularioAmbulatorial();
            if (novo != null) {
                pacientes.add(novo);
                atualizarTabelaPacientes(pacientes);
            }
        });

        JButton btnInternado = new JButton("Novo Paciente Internação");
        btnInternado.setPreferredSize(new Dimension(180, 25));
        btnInternado.addActionListener(e -> {
            Paciente novo = mostrarFormularioInternado();
            if (novo != null) {
                pacientes.add(novo);
                atualizarTabelaPacientes(pacientes);
            }
        });

        JTextField campoBusca = new JTextField(20);
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setPreferredSize(new Dimension(80, 25));
        btnBuscar.addActionListener(e -> {
            String texto = campoBusca.getText().toLowerCase();
            List<Paciente> filtrados = pacientes.stream()
                    .filter(p -> p.getNome().toLowerCase().contains(texto) || p.getCpf().contains(texto))
                    .collect(Collectors.toList());
            atualizarTabelaPacientes(filtrados);
        });

        JButton btnHistoricoAmbulatorial = new JButton("Histórico Ambulatorial");
        btnHistoricoAmbulatorial.setPreferredSize(new Dimension(150, 25));
        btnHistoricoAmbulatorial.addActionListener(e -> mostrarHistoricoAmbulatorial());

        JButton btnHistoricoInternado = new JButton("Histórico Internação");
        btnHistoricoInternado.setPreferredSize(new Dimension(150, 25));
        btnHistoricoInternado.addActionListener(e -> mostrarHistoricoInternado());

        JButton btnDarBaixa = new JButton("Dar baixa");
        btnDarBaixa.setPreferredSize(new Dimension(100, 25));
        btnDarBaixa.addActionListener(e -> darBaixaPacienteSelecionado());

        painelInferior.add(btnAmbulatorial);
        painelInferior.add(btnInternado);
        painelInferior.add(new JLabel("Buscar:"));
        painelInferior.add(campoBusca);
        painelInferior.add(btnBuscar);
        painelInferior.add(btnHistoricoAmbulatorial);
        painelInferior.add(btnHistoricoInternado);
        painelInferior.add(btnDarBaixa);

        painel.add(scroll, BorderLayout.CENTER);
        painel.add(painelInferior, BorderLayout.SOUTH);

        return painel;
    }

    private void atualizarTabelaPacientes(List<Paciente> lista) {
        modelo.setRowCount(0);
        for (Paciente p : lista) {
            modelo.addRow(new Object[]{
                    p.getNome(), p.getTipo(), p.getIdade(), p.getCpf(), p.getDiagnostico(), p.getCustoTotal()
            });
        }
    }

    private Paciente mostrarFormularioAmbulatorial() {
        JTextField nome = new JTextField();
        JTextField idade = new JTextField();
        JTextField cpf = new JTextField();
        JTextField diagnostico = new JTextField();
        JTextField qtdConsultas = new JTextField();
        JTextField custoConsulta = new JTextField();

        JPanel painel = new JPanel(new GridLayout(0, 2));
        painel.add(new JLabel("Nome:")); painel.add(nome);
        painel.add(new JLabel("Idade:")); painel.add(idade);
        painel.add(new JLabel("CPF:")); painel.add(cpf);
        painel.add(new JLabel("Diagnóstico:")); painel.add(diagnostico);
        painel.add(new JLabel("Qtd. Consultas:")); painel.add(qtdConsultas);
        painel.add(new JLabel("Custo Consulta:")); painel.add(custoConsulta);

        int resultado = JOptionPane.showConfirmDialog(this, painel, "Novo Paciente Ambulatorial",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (resultado == JOptionPane.OK_OPTION) {
            try {
                return new Ambulatorial(
                        nome.getText(),
                        LocalDate.now(),
                        Integer.parseInt(idade.getText()),
                        cpf.getText(),
                        'N', "", "", "",
                        diagnostico.getText(),
                        Integer.parseInt(qtdConsultas.getText()),
                        Double.parseDouble(custoConsulta.getText())
                );
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Dados inválidos!");
            }
        }
        return null;
    }

    private Paciente mostrarFormularioInternado() {
        JTextField nome = new JTextField();
        JTextField idade = new JTextField();
        JTextField cpf = new JTextField();
        JTextField diagnostico = new JTextField();
        JTextField diasInternado = new JTextField();
        JTextField custoDiaria = new JTextField();

        JPanel painel = new JPanel(new GridLayout(0, 2));
        painel.add(new JLabel("Nome:")); painel.add(nome);
        painel.add(new JLabel("Idade:")); painel.add(idade);
        painel.add(new JLabel("CPF:")); painel.add(cpf);
        painel.add(new JLabel("Diagnóstico:")); painel.add(diagnostico);
        painel.add(new JLabel("Dias Internado:")); painel.add(diasInternado);
        painel.add(new JLabel("Custo Diário:")); painel.add(custoDiaria);

        int resultado = JOptionPane.showConfirmDialog(this, painel, "Novo Paciente Internação",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (resultado == JOptionPane.OK_OPTION) {
            try {
                return new Internado(
                        nome.getText(),
                        LocalDate.now(),
                        Integer.parseInt(idade.getText()),
                        cpf.getText(),
                        'N', "", "", "",
                        diagnostico.getText(),
                        Integer.parseInt(diasInternado.getText()),
                        Double.parseDouble(custoDiaria.getText())
                );
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Dados inválidos!");
            }
        }
        return null;
    }

    private void mostrarHistoricoAmbulatorial() {
        List<Ambulatorial> ambulatoriais = pacientesBaixados.stream()
                .filter(p -> p instanceof Ambulatorial)
                .map(p -> (Ambulatorial) p)
                .collect(Collectors.toList());

        if (ambulatoriais.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum paciente ambulatorial no histórico.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-25s %-10s %-15s %-20s\n", "Nome", "Idade", "CPF", "Diagnóstico"));
        sb.append("=".repeat(75)).append("\n");

        for (Ambulatorial a : ambulatoriais) {
            sb.append(String.format("%-25s %-10d %-15s %-20s\n",
                    a.getNome(), a.getIdade(), a.getCpf(), a.getDiagnostico()));
        }

        JTextArea area = new JTextArea(sb.toString());
        area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        area.setEditable(false);
        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(850, 300));
        JOptionPane.showMessageDialog(this, scroll, "Histórico de Pacientes Ambulatoriais", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarHistoricoInternado() {
        List<Internado> internados = pacientesBaixados.stream()
                .filter(p -> p instanceof Internado)
                .map(p -> (Internado) p)
                .collect(Collectors.toList());

        if (internados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum paciente internado no histórico.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-25s %-10s %-15s %-20s\n", "Nome", "Idade", "CPF", "Diagnóstico"));
        sb.append("=".repeat(75)).append("\n");

        for (Internado i : internados) {
            sb.append(String.format("%-25s %-10d %-15s %-20s\n",
                    i.getNome(), i.getIdade(), i.getCpf(), i.getDiagnostico()));
        }

        JTextArea area = new JTextArea(sb.toString());
        area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        area.setEditable(false);
        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(850, 300));
        JOptionPane.showMessageDialog(this, scroll, "Histórico de Pacientes Internados", JOptionPane.INFORMATION_MESSAGE);
    }

    private void darBaixaPacienteSelecionado() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um paciente para dar baixa.");
            return;
        }

        String cpf = (String) modelo.getValueAt(linha, 3);
        Paciente paciente = pacientes.stream()
                .filter(p -> p.getCpf().equals(cpf))
                .findFirst().orElse(null);

        if (paciente != null) {
            int confirm = JOptionPane.showConfirmDialog(this, "Confirmar dar baixa para " + paciente.getNome() + "?",
                    "Dar baixa", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                pacientes.remove(paciente);
                pacientesBaixados.add(paciente);
                atualizarTabelaPacientes(pacientes);
                JOptionPane.showMessageDialog(this, "Paciente dado baixa e movido para histórico.");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(App::new);
    }
}
