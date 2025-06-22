public class EquipeMedica extends Pessoa {
    private String cargo;
    private String registroProfissional;
    private String especialidade;

    public EquipeMedica(String nome, String dataNasc, int idade, String cpf, char sexo, String email, String endereco, String telefone, String cargo, String registroProfissional, String especialidade) {
        super(nome, dataNasc, idade, cpf, sexo, email, endereco, telefone);
        this.cargo = cargo;
        this.registroProfissional = registroProfissional;
        this.especialidade = especialidade;
    }

    // Setters
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public void setRegistroProfissional(String registroProfissional) {
        this.registroProfissional = registroProfissional;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    // Getters
    public String getCargo() {
        return cargo;
    }

    public String getRegistroProfissional() {
        return registroProfissional;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    @Override
    public String getTipo() {
        return "Equipe Médica: " + cargo;
    }
}