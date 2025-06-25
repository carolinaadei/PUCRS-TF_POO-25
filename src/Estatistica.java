import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Estatistica {

    public static void mostrar(List<Paciente> pacientes) {
        if (pacientes == null || pacientes.isEmpty()) {
            System.out.println("Nenhum paciente cadastrado para exibir estatísticas.");
            return;
        }

        long totalPacientes = pacientes.size();
        long totalInternados = pacientes.stream()
                .filter(p -> p instanceof Internado)
                .count();
        long totalAmbulatoriais = pacientes.stream()
                .filter(p -> p instanceof Ambulatorial)
                .count();

        int somaIdades = pacientes.stream()
                .mapToInt(Paciente::getIdade)
                .sum();

        double mediaIdade = pacientes.stream()
                .mapToInt(Paciente::getIdade)
                .average()
                .orElse(0.0);

        double mediaConsultas = pacientes.stream()
                .filter(p -> p instanceof Ambulatorial)
                .mapToInt(p -> ((Ambulatorial) p).getQtdConsultas())
                .average()
                .orElse(0.0);

        double mediaDiasInternado = pacientes.stream()
                .filter(p -> p instanceof Internado)
                .mapToInt(p -> ((Internado) p).getDiasInternado())
                .average()
                .orElse(0.0);

        double custoTotalGeral = pacientes.stream()
                .mapToDouble(Paciente::getCustoTotal)
                .sum();

        double custoMedio = pacientes.stream()
                .mapToDouble(Paciente::getCustoTotal)
                .average()
                .orElse(0.0);

        System.out.println("----- ESTATÍSTICAS DETALHADAS -----");
        System.out.println("Total de pacientes: " + totalPacientes);
        System.out.println("Ambulatoriais: " + totalAmbulatoriais);
        System.out.println("Internados: " + totalInternados);
        System.out.println("Soma das idades: " + somaIdades);
        System.out.println("Média das idades: " + String.format("%.2f", mediaIdade));
        System.out.println("Média de consultas realizadas: " + String.format("%.2f", mediaConsultas));
        System.out.println("Média de dias internados: " + String.format("%.2f", mediaDiasInternado));
        System.out.println("Custo total geral: R$ " + String.format("%.2f", custoTotalGeral));
        System.out.println("Custo médio por paciente: R$ " + String.format("%.2f", custoMedio));
        System.out.println("------------------------------------");
    }

    public static void mostrarEstatisticasPorDiagnostico(List<Paciente> pacientes) {
        if (pacientes == null || pacientes.isEmpty()) {
            System.out.println("Nenhum paciente cadastrado.");
            return;
        }

        Map<String, Long> contadorDiagnosticos = pacientes.stream()
                .collect(Collectors.groupingBy(Paciente::getDiagnostico, Collectors.counting()));

        System.out.println("----- ESTATÍSTICAS POR DIAGNÓSTICO -----");
        contadorDiagnosticos.forEach((diagnostico, quantidade) ->
                System.out.println(diagnostico + ": " + quantidade + " paciente(s)")
        );
        System.out.println("----------------------------------------");
    }


    public static void mostrarEstatisticasCustos(List<Paciente> pacientes) {
        if (pacientes == null || pacientes.isEmpty()) {
            System.out.println("Nenhum paciente cadastrado.");
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

        System.out.println("----- ESTATÍSTICAS DE CUSTOS -----");
        System.out.println("Custo mínimo: R$ " + String.format("%.2f", custoMinimo));
        System.out.println("Custo máximo: R$ " + String.format("%.2f", custoMaximo));
        System.out.println("Custo total: R$ " + String.format("%.2f", custoTotal));
        System.out.println("Custo médio: R$ " + String.format("%.2f", custoMedio));
        System.out.println("----------------------------------");
    }
}