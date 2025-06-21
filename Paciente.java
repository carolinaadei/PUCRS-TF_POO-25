public abstract class Paciente extends Pessoa {
    private String diagnostico;
    private String responsavel;

    public Paciente(String nome, String dataNasc, int idade, String cpf, char sexo, String email, String endereco, String telefone, String diagnostico, String responsavel) {
        super(nome, dataNasc, idade, cpf, sexo, email, endereco, telefone);
        this.diagnostico = diagnostico;
        this.responsavel = responsavel;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public abstract double getCustoTotal();

    @Override
    public String getTipo() {
        return "Paciente";
    }
}