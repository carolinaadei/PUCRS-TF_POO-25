    public class Ambulatorial extends Paciente {
        private int qtdConsultas;
        private double custoConsulta;

        public Ambulatorial(String nome, String dataNasc, int idade, String cpf, char sexo,
                            String email, String endereco, String telefone,
                            String diagnostico, int qtdConsultas, double custoConsulta) {
            super(nome, dataNasc, idade, cpf, sexo, email, endereco, telefone, diagnostico);
            this.qtdConsultas = qtdConsultas;
            this.custoConsulta = custoConsulta;

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
    }


