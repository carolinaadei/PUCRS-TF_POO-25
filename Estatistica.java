import java.util.List;

public class Estatistica {

    public static void mostrar(List<Paciente> pacientes) {
        long totalPacientes = pacientes.size();
        long totalInternados = pacientes.stream().filter(p -> p instanceof Internado).count();
        long totalAmbulatoriais = pacientes.stream().filter(p -> p instanceof Ambulatorial).count();

        int somaIdades = pacientes.stream().mapToInt(Paciente::getIdade).sum();
        double mediaIdade = pacientes.stream().mapToInt(Paciente::getIdade).average().orElse(0);

        double mediaConsultas = pacientes.stream()
                .filter(p -> p instanceof Ambulatorial)
                .mapToInt(p -> ((Ambulatorial) p).getQtdConsultas())
                .average()
                .orElse(0);

        double mediaDiasInternado = pacientes.stream()
                .filter(p -> p instanceof Internado)
                .mapToInt(p -> ((Internado) p).getDiasInternado())
                .average()
                .orElse(0);

        System.out.println("----- ESTATÍSTICAS -----");
        System.out.println("Total de pacientes: " + totalPacientes);
        System.out.println("Ambulatoriais: " + totalAmbulatoriais);
        System.out.println("Internados: " + totalInternados);
        System.out.println("Soma das idades: " + somaIdades);
        System.out.println("Média das idades: " + String.format("%.2f", mediaIdade));
        System.out.println("Média de consultas realizadas: " + String.format("%.2f", mediaConsultas));
        System.out.println("Média de dias internados: " + String.format("%.2f", mediaDiasInternado));
        System.out.println("------------------------");
    }
}