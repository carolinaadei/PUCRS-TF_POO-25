import java.util.List;

    public class Estatistica {

        public static void mostrar(List<Paciente> pacientes) {
            long totalPacientes = pacientes.size();
            long totalInternados = pacientes.stream().filter(p -> p instanceof Internado).count();
            long totalAmbulatoriais = pacientes.stream().filter(p -> p instanceof Ambulatorial).count();

            int somaIdades = pacientes.stream().mapToInt(Paciente::getIdade).sum();
            double mediaIdade = pacientes.stream().mapToInt(Paciente::getIdade).average().orElse(0);

            int totalConsultas = pacientes.stream()
                    .filter(p -> p instanceof Ambulatorial)
                    .map(p -> (Ambulatorial) p)
                    .mapToInt(Ambulatorial::getConsultasRealizadas)
                    .sum();

            int totalDiasInternado = pacientes.stream()
                    .filter(p -> p instanceof Internado)
                    .map(p -> (Internado) p)
                    .mapToInt(Internado::getDiasInternado)
                    .sum();

            System.out.println("----- ESTATÍSTICAS -----");
            System.out.println("Total de pacientes: " + totalPacientes);
            System.out.println("Ambulatoriais: " + totalAmbulatoriais);
            System.out.println("Internados: " + totalInternados);
            System.out.println("Soma das idades: " + somaIdades);
            System.out.println("Média das idades: " + mediaIdade);
            System.out.println("Total de consultas realizadas: " + totalConsultas);
            System.out.println("Total de dias internados: " + totalDiasInternado);
            System.out.println("------------------------");
        }
    }



