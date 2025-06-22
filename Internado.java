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

    // Setters com validação
    public void setDiasInternado(int diasInternado) {
        if (diasInternado >= 0) {
            this.diasInternado = diasInternado;
        } else {
            throw new IllegalArgumentException("Dias internado não pode ser negativo");
        }
    }

    public void setCustoDiario(double custoDiario) {
        if (custoDiario >= 0) {
            this.custoDiario = custoDiario;
        } else {
            throw new IllegalArgumentException("Custo diário não pode ser negativo");
        }
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

    @Override
    public String toString() {
        return super.toString() +
                ", Dias Internado: " + diasInternado +
                ", Custo Diário: R$ " + String.format("%.2f", custoDiario);
    }
}