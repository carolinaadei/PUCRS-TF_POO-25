public class Internado extends Paciente {
    private int diasInternado;
    private double custoDiario;

    public Internado(String nome, String dataNasc, int idade, String cpf, char sexo, String email, String endereco, String telefone, String diagnostico, String responsavel, int diasInternado, double custoDiario) {
        super(nome, dataNasc, idade, cpf, sexo, email, endereco, telefone, diagnostico, responsavel);
        this.diasInternado = diasInternado;
        this.custoDiario = custoDiario;
    }

    // Setters
    public void setDiasInternado(int diasInternado) {
        this.diasInternado = diasInternado;
    }

    public void setCustoDiario(double custoDiario) {
        this.custoDiario = custoDiario;
    }

    // Getters
    public int getDiasInternado() {
        return diasInternado;
    }

    public double getCustoDiario() {
        return custoDiario;
    }

    @Override
    public String getTipo() {
        return "Internado";
    }

    @Override
    public double getCustoTotal() {
        return diasInternado * custoDiario;
    }
}