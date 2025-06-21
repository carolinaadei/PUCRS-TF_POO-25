public abstract class Pessoa {
    private String nome;
    private String dataNasc;
    private int idade;
    private String cpf;
    private char sexo;
    private String email;
    private String endereco;
    private String telefone;

    public Pessoa(String nome, String dataNasc, int idade, String cpf, char sexo, String email, String endereco, String telefone) {
        this.nome = nome;
        this.dataNasc = dataNasc;
        this.idade = idade;
        this.cpf = cpf;
        this.sexo = sexo;
        this.email = email;
        this.endereco = endereco;
        this.telefone = telefone;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDataNasc(String dataNasc) {
        this.dataNasc = dataNasc;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getNome() {
        return nome;
    }

    public String getDataNasc() {
        return dataNasc;
    }

    public int getIdade() {
        return idade;
    }

    public String getCpf() {
        return cpf;
    }

    public char getSexo() {
        return sexo;
    }

    public String getEmail() {
        return email;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public abstract String getTipo();
}