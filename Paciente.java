import java.time.LocalDate;

public abstract class Paciente extends Pessoa {
    private String diagnostico;

    public Paciente(String nome, LocalDate dataNasc, int idade, String cpf, char sexo, String email, String endereco, String telefone, String diagnostico) {
        super(nome, dataNasc, idade, cpf, sexo, email, endereco, telefone);
        this.diagnostico = diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public abstract double getCustoTotal();

    @Override
    public String getTipo() {
        return "Paciente";
    }
}