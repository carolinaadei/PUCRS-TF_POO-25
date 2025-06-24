public abstract class HospitalException extends Exception {
    protected String codigoErro;
    protected String categoria;
    protected long timestamp;

    public HospitalException(String mensagem, String codigoErro, String categoria) {
        super(mensagem);
        this.codigoErro = codigoErro;
        this.categoria = categoria;
        this.timestamp = System.currentTimeMillis();
    }

    public HospitalException(String mensagem, Throwable causa, String codigoErro, String categoria) {
        super(mensagem, causa);
        this.codigoErro = codigoErro;
        this.categoria = categoria;
        this.timestamp = System.currentTimeMillis();
    }

    public String getCodigoErro() { return codigoErro; }
    public String getCategoria() { return categoria; }
    public long getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        return String.format("[%s] %s: %s [Código: %s] - %tc", 
                categoria, getClass().getSimpleName(), getMessage(), codigoErro, timestamp);
    }
}

public class PacienteException extends HospitalException {
    public PacienteException(String mensagem, String codigoErro) {
        super(mensagem, codigoErro, "PACIENTE");
    }
    
    public PacienteException(String mensagem, Throwable causa, String codigoErro) {
        super(mensagem, causa, codigoErro, "PACIENTE");
    }
}

public class EquipeMedicaException extends HospitalException {
    public EquipeMedicaException(String mensagem, String codigoErro) {
        super(mensagem, codigoErro, "EQUIPE_MEDICA");
    }
    
    public EquipeMedicaException(String mensagem, Throwable causa, String codigoErro) {
        super(mensagem, causa, codigoErro, "EQUIPE_MEDICA");
    }
}

public class ItemHospitalarException extends HospitalException {
    public ItemHospitalarException(String mensagem, String codigoErro) {
        super(mensagem, codigoErro, "ITEM_HOSPITALAR");
    }
    
    public ItemHospitalarException(String mensagem, Throwable causa, String codigoErro) {
        super(mensagem, causa, codigoErro, "ITEM_HOSPITALAR");
    }
}

public class ValidacaoException extends HospitalException {
    private String campo;
    private String valorInvalido;

    public ValidacaoException(String mensagem, String campo, String valorInvalido) {
        super(mensagem, "VAL_001", "VALIDACAO");
        this.campo = campo;
        this.valorInvalido = valorInvalido;
    }

    public String getCampo() { return campo; }
    public String getValorInvalido() { return valorInvalido; }

    @Override
    public String toString() {
        return String.format("%s - Campo: %s, Valor: %s", super.toString(), campo, valorInvalido);
    }
}

public enum CodigoErro {
    PAC_001("Paciente não encontrado"),
    PAC_002("CPF já cadastrado"),
    PAC_003("Dados obrigatórios não preenchidos"),
    PAC_004("Idade inválida"),
    PAC_005("CPF inválido"),
    
    EQP_001("Membro da equipe não encontrado"),
    EQP_002("Registro profissional já existe"),
    EQP_003("Especialidade não permitida para o cargo"),
    
    ITM_001("Item não disponível no estoque"),
    ITM_002("Item já atribuído ao paciente"),
    ITM_003("Tipo de item incompatível"),
    
    SIS_001("Erro interno do sistema"),
    SIS_002("Operação não permitida"),
    SIS_003("Dados corrompidos");

    private final String descricao;

    CodigoErro(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() { return descricao; }
    public String getCodigo() { return this.name(); }
}

public class ValidadorUtil {
    
    public static void validarCPF(String cpf) throws ValidacaoException {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new ValidacaoException("CPF é obrigatório", "cpf", cpf);
        }
        
        String cpfLimpo = cpf.replaceAll("[^0-9]", "");
        
        if (cpfLimpo.length() != 11) {
            throw new ValidacaoException("CPF deve conter 11 dígitos", "cpf", cpf);
        }
        
        if (cpfLimpo.matches("(\\d)\\1{10}")) {
            throw new ValidacaoException("CPF inválido", "cpf", cpf);
        }
        
        if (!validarDigitosCPF(cpfLimpo)) {
            throw new ValidacaoException("CPF inválido", "cpf", cpf);
        }
    }
    
    private static boolean validarDigitosCPF(String cpf) {
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
        }
        int digito1 = 11 - (soma % 11);
        if (digito1 >= 10) digito1 = 0;
        
        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
        }
        int digito2 = 11 - (soma % 11);
        if (digito2 >= 10) digito2 = 0;
        
        return digito1 == Character.getNumericValue(cpf.charAt(9)) && 
               digito2 == Character.getNumericValue(cpf.charAt(10));
    }
    
    public static void validarIdade(int idade, int minima, int maxima) throws ValidacaoException {
        if (idade < minima || idade > maxima) {
            throw new ValidacaoException(
                String.format("Idade deve estar entre %d e %d anos", minima, maxima),
                "idade", 
                String.valueOf(idade)
            );
        }
    }
    
    public static void validarEmail(String email) throws ValidacaoException {
        if (email != null && !email.trim().isEmpty()) {
            String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
            if (!email.matches(regex)) {
                throw new ValidacaoException("Email inválido", "email", email);
            }
        }
    }
    
    public static void validarCampoObrigatorio(String valor, String nomeCampo) throws ValidacaoException {
        if (valor == null || valor.trim().isEmpty()) {
            throw new ValidacaoException(
                nomeCampo + " é obrigatório", 
                nomeCampo.toLowerCase(), 
                valor
            );
        }
    }
}

public class LogExcecao {
    private static final String ARQUIVO_LOG = "hospital_errors.log";
    
    public static void logarExcecao(HospitalException e) {
        try {
            java.io.FileWriter writer = new java.io.FileWriter(ARQUIVO_LOG, true);
            writer.write(String.format("%tc - %s%n", e.getTimestamp(), e.toString()));
            if (e.getCause() != null) {
                writer.write("Causa: " + e.getCause().toString() + "\n");
            }
            writer.write("---\n");
            writer.close();
        } catch (java.io.IOException ioException) {
            System.err.println("Erro ao escrever log: " + ioException.getMessage());
        }
    }
    
    public static void mostrarErroUsuario(HospitalException e, java.awt.Component parent) {
        String mensagem = String.format(
            "Erro: %s\n\nCódigo: %s\nCategoria: %s\n\nContate o suporte se o problema persistir.",
            e.getMessage(), e.getCodigoErro(), e.getCategoria()
        );
        
        javax.swing.JOptionPane.showMessageDialog(
            parent, 
            mensagem, 
            "Erro do Sistema", 
            javax.swing.JOptionPane.ERROR_MESSAGE
        );
        
        logarExcecao(e);
    }
}
