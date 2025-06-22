/**
 * Exceção customizada para problemas relacionados aos itens hospitalares
 */
public class ItemHospitalarException extends Exception {

    private String codigoErro;

    /**
     * Construtor básico com mensagem
     * @param mensagem descrição do erro
     */
    public ItemHospitalarException(String mensagem) {
        super(mensagem);
    }

    /**
     * Construtor com mensagem e causa
     * @param mensagem descrição do erro
     * @param causa exceção que causou este erro
     */
    public ItemHospitalarException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }

    /**
     * Construtor com mensagem e código do erro
     * @param mensagem descrição do erro
     * @param codigoErro código identificador do erro
     */
    public ItemHospitalarException(String mensagem, String codigoErro) {
        super(mensagem);
        this.codigoErro = codigoErro;
    }

    /**
     * Construtor completo
     * @param mensagem descrição do erro
     * @param causa exceção que causou este erro
     * @param codigoErro código identificador do erro
     */
    public ItemHospitalarException(String mensagem, Throwable causa, String codigoErro) {
        super(mensagem, causa);
        this.codigoErro = codigoErro;
    }

    /**
     * Retorna o código do erro, se disponível
     * @return código do erro ou null se não definido
     */
    public String getCodigoErro() {
        return codigoErro;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName()).append(": ");
        sb.append(getMessage());

        if (codigoErro != null && !codigoErro.trim().isEmpty()) {
            sb.append(" [Código: ").append(codigoErro).append("]");
        }

        return sb.toString();
    }
}