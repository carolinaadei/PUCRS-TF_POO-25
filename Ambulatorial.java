public class Ambulatorial extends Paciente {
    private int qtdConsultas;
    private double custoConsulta;

    public Ambulatorial(String nome, String dataNasc, int idade, String cpf, char sexo,
                        String email, String endereco, String telefone,
                        String diagnostico, String responsavel, String nomeItem,
                        int qtdConsultas, double custoConsulta) {
        super(nome, dataNasc, idade, cpf, sexo, email, endereco, telefone,
                diagnostico, responsavel, nomeItem);
        setQtdConsultas(qtdConsultas);
        setCustoConsulta(custoConsulta);
    }

    public void setQtdConsultas(int qtdConsultas) {
        this.qtdConsultas = qtdConsultas;
    }

    public void setCustoConsulta(double custoConsulta) {
        this.custoConsulta = custoConsulta;
    }

    public int getQtdConsultas() {
        return qtdConsultas;
    }

    public double getCustoConsulta() {
        return custoConsulta;
    }

    @Override
    public String getTipo() {
        return "Ambulatorial";
    }

    @Override
    public double getCustoTotal() {
        return qtdConsultas * custoConsulta;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", Quantidade de Consultas: " + qtdConsultas +
                ", Custo por Consulta: R$ " + String.format("%.2f", custoConsulta);
    }
}