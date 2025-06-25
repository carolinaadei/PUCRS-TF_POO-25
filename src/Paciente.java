public abstract class Paciente extends Pessoa {
    private String diagnostico;
    private String responsavel;
    private String nomeItem;

    public Paciente(String nome, String dataNasc, int idade, String cpf, char sexo,
                    String email, String endereco, String telefone, String diagnostico,
                    String responsavel, String nomeItem) {
        super(nome, dataNasc, idade, cpf, sexo, email, endereco, telefone);
        this.diagnostico = diagnostico;
        this.responsavel = responsavel;
        this.nomeItem = nomeItem != null ? nomeItem : "";
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public void setNomeItem(String nomeItem) {
        this.nomeItem = nomeItem != null ? nomeItem : "";
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public String getResponsavel() {
        return responsavel != null ? responsavel : "";
    }

    public String getNomeItem() {
        return nomeItem != null ? nomeItem : "";
    }

    public abstract double getCustoTotal();

    @Override
    public String getTipo() {
        return "Paciente";
    }

    @Override
    public String toString() {
        return super.toString() + ", Diagnóstico: " + diagnostico +
                ", Responsável: " + getResponsavel() +
                ", Custo Total: R$ " + String.format("%.2f", getCustoTotal());
    }
}