public class Internado extends Paciente {
    private int diasInternado;
    private double custoDiario;

    public Internado(String nome, String dataNasc, int idade, String cpf, char sexo,
                     String email, String endereco, String telefone, String diagnostico,
                     String responsavel, String nomeItem, int diasInternado, double custoDiario) {
        super(nome, dataNasc, idade, cpf, sexo, email, endereco, telefone,
                diagnostico, responsavel, nomeItem);
        setDiasInternado(diasInternado);
        setCustoDiario(custoDiario);
    }

    public void setDiasInternado(int diasInternado) {
        this.diasInternado = diasInternado;
    }

    public void setCustoDiario(double custoDiario) {
        this.custoDiario = custoDiario;
    }

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

    @Override
    public String toString() {
        return super.toString() +
                ", Dias Internado: " + diasInternado +
                ", Custo Diário: R$ " + String.format("%.2f", custoDiario);
    }
}