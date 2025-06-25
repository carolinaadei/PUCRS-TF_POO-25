import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public class Estatistica {

    public static void mostrarEstatisticasCustos(Collection<Paciente> pacientes) {
        if (pacientes == null || pacientes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum paciente cadastrado.");
            return;
        }

        double[] custos = pacientes.stream()
                .mapToDouble(Paciente::getCustoTotal)
                .sorted()
                .toArray();

        double custoMinimo = custos[0];
        double custoMaximo = custos[custos.length - 1];
        double custoTotal = pacientes.stream()
                .mapToDouble(Paciente::getCustoTotal)
                .sum();
        double custoMedio = custoTotal / pacientes.size();

        String texto = "----- ESTATÍSTICAS DE CUSTOS -----\n"
                + "Custo mínimo: R$ " + String.format("%.2f", custoMinimo) + "\n"
                + "Custo máximo: R$ " + String.format("%.2f", custoMaximo) + "\n"
                + "Custo total: R$ " + String.format("%.2f", custoTotal) + "\n"
                + "Custo médio: R$ " + String.format("%.2f", custoMedio) + "\n"
                + "----------------------------------";

        exibirEstatisticaEmTela("Estatísticas de Custo", texto);
    }

    public static void mostrarEstatisticasPorDiagnostico(Collection<Paciente> pacientes) {
        if (pacientes == null || pacientes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum paciente cadastrado.");
            return;
        }

        Map<String, Long> contadorDiagnosticos = pacientes.stream()
                .collect(Collectors.groupingBy(Paciente::getDiagnostico, Collectors.counting()));

        StringBuilder texto = new StringBuilder("----- ESTATÍSTICAS POR DIAGNÓSTICO -----\n");
        contadorDiagnosticos.forEach((diagnostico, quantidade) ->
                texto.append(diagnostico).append(": ").append(quantidade).append(" paciente(s)\n")
        );
        texto.append("----------------------------------------");

        exibirEstatisticaEmTela("Estatísticas por Diagnóstico", texto.toString());
    }

    public static void mostrar(Collection<Paciente> pacientes) {
        if (pacientes == null || pacientes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum paciente cadastrado para exibir estatísticas.");
            return;
        }

        long totalPacientes = pacientes.size();
        long totalInternados = pacientes.stream().filter(p -> p instanceof Internado).count();
        long totalAmbulatoriais = pacientes.stream().filter(p -> p instanceof Ambulatorial).count();

        int somaIdades = pacientes.stream().mapToInt(Paciente::getIdade).sum();
        double mediaIdade = pacientes.stream().mapToInt(Paciente::getIdade).average().orElse(0.0);
        double mediaConsultas = pacientes.stream()
                .filter(p -> p instanceof Ambulatorial)
                .mapToInt(p -> ((Ambulatorial) p).getQtdConsultas())
                .average().orElse(0.0);
        double mediaDiasInternado = pacientes.stream()
                .filter(p -> p instanceof Internado)
                .mapToInt(p -> ((Internado) p).getDiasInternado())
                .average().orElse(0.0);

        double custoTotalGeral = pacientes.stream().mapToDouble(Paciente::getCustoTotal).sum();
        double custoMedio = pacientes.stream().mapToDouble(Paciente::getCustoTotal).average().orElse(0.0);

        String texto = "----- ESTATÍSTICAS DETALHADAS -----\n"
                + "Total de pacientes: " + totalPacientes + "\n"
                + "Ambulatoriais: " + totalAmbulatoriais + "\n"
                + "Internados: " + totalInternados + "\n"
                + "Soma das idades: " + somaIdades + "\n"
                + "Média das idades: " + String.format("%.2f", mediaIdade) + "\n"
                + "Média de consultas realizadas: " + String.format("%.2f", mediaConsultas) + "\n"
                + "Média de dias internados: " + String.format("%.2f", mediaDiasInternado) + "\n"
                + "Custo total geral: R$ " + String.format("%.2f", custoTotalGeral) + "\n"
                + "Custo médio por paciente: R$ " + String.format("%.2f", custoMedio) + "\n"
                + "------------------------------------";

        exibirEstatisticaEmTela("Estatísticas Detalhadas", texto);
    }

    private static void exibirEstatisticaEmTela(String titulo, String texto) {
        JFrame frame = new JFrame(titulo);
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        JTextArea areaTexto = new JTextArea(texto);
        areaTexto.setEditable(false);
        areaTexto.setMargin(new Insets(10, 10, 10, 10));
        JScrollPane scroll = new JScrollPane(areaTexto);

        frame.add(scroll, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}