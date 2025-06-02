import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDate;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class App extends JFrame {
    List<Paciente> pacientes = new ArrayList<>();

    public App() {
        setTitle("Sistema Hospitalar");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        pacientes.add(new Ambulatorial("Lucas de Souza", LocalDate.of(1990, 5, 12), 34, "123.456.789.10", 'F', "Lucas@email.com", "Rua A", "1234-5678", "Exame de sangue", 3, 150));
        pacientes.add(new Internado("Carlos Dias", LocalDate.of(1985, 3, 22), 39, "456.789.101.11", 'M', "carlos@email.com", "Rua B", "9876-5432", "Apendicectomia", 5, 500));
        pacientes.add(new Ambulatorial("Maria Alves", LocalDate.of(1985, 3, 22), 39, "789.101.112.13", 'F', "maria@email.com", "Rua B", "9876-5432", "Retorno", 1, 200));
        pacientes.add(new Internado("Joana Silva", LocalDate.of(1990, 5, 12), 34, "131.415.161.71", 'F', "joana@email.com", "Rua A", "1234-5678", "Internacao", 2, 600));

        add(painelPacientes());
        setVisible(true);
    }

    private JPanel painelPacientes() {
        JPanel painel = new JPanel(new BorderLayout());

        String[] colunas = {"Nome", "Tipo", "Idade", "CPF", "Diagnóstico", "Custo Total"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);
        JTable tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela);

        atualizarTabelaPacientes(modelo, pacientes);

        JPanel botoes = new JPanel(new FlowLayout());

        JButton btnCadastrar = new JButton("Novo Paciente");
        btnCadastrar.addActionListener(e -> {
            Paciente novo = mostrarFormularioCadastro();
            if (novo != null) {
                pacientes.add(novo);
                atualizarTabelaPacientes(modelo, pacientes);
            }
        });

        JTextField campoBusca = new JTextField(20);
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> {
            String texto = campoBusca.getText().toLowerCase();
            List<Paciente> filtrados = pacientes.stream()
                    .filter(p -> p.getNome().toLowerCase().contains(texto) || p.getCpf().contains(texto))
                    .collect(Collectors.toList());
            atualizarTabelaPacientes(modelo, filtrados);
        });

        JButton btnAmbuInternado = new JButton("Pacientes Ambulatorial e Internado");
        btnAmbuInternado.addActionListener(e -> {
            List<String> cpfsInternado = pacientes.stream()
                    .filter(p -> p instanceof Internado)
                    .map(Paciente::getCpf)
                    .collect(Collectors.toList());

            List<Ambulatorial> ambusDuplicados = pacientes.stream()
                    .filter(p -> p instanceof Ambulatorial)
                    .map(p -> (Ambulatorial) p)
                    .filter(a -> cpfsInternado.contains(a.getCpf()))
                    .collect(Collectors.toList());

            StringBuilder sb = new StringBuilder();
            sb.append(String.format("%-25s %-15s %-25s %-25s\n", "Nome", "Tipo", "Período", "Diagnóstico"));
            sb.append("=".repeat(90)).append("\n");

            for (Ambulatorial a : ambusDuplicados) {
                String data = LocalDate.now().toString(); // Data simulada
                sb.append(String.format("%-25s %-15s %-25s %-25s\n",
                        a.getNome(), a.getTipo(), data + " - " + data, a.getDiagnostico()));
            }

            JTextArea area = new JTextArea(sb.toString());
            area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
            area.setEditable(false);
            JScrollPane areaScroll = new JScrollPane(area);
            areaScroll.setPreferredSize(new Dimension(800, 300));
            JOptionPane.showMessageDialog(this, areaScroll, "Pacientes Ambulatorial e Internado", JOptionPane.INFORMATION_MESSAGE);
        });

        botoes.add(btnCadastrar);
        botoes.add(new JLabel("Buscar por nome ou CPF:"));
        botoes.add(campoBusca);
        botoes.add(btnBuscar);
        botoes.add(btnAmbuInternado);

        painel.add(scroll, BorderLayout.CENTER);
        painel.add(botoes, BorderLayout.SOUTH);
        return painel;
    }

    private void atualizarTabelaPacientes(DefaultTableModel modelo, List<Paciente> lista) {
        modelo.setRowCount(0);
        for (Paciente p : lista) {
            modelo.addRow(new Object[]{
                    p.getNome(), p.getTipo(), p.getIdade(), p.getCpf(), p.getDiagnostico(), p.getCustoTotal()
            });
        }
    }

    private Paciente mostrarFormularioCadastro() {
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(App::new);
    }
}