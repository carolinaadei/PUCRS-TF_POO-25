public class ItemHospitalarException extends Exception {

    private String codigoErro;


    public ItemHospitalarException(String mensagem) {
        super(mensagem);
    }

    public ItemHospitalarException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }

    public ItemHospitalarException(String mensagem, String codigoErro) {
        super(mensagem);
        this.codigoErro = codigoErro;
    }

    public ItemHospitalarException(String mensagem, Throwable causa, String codigoErro) {
        super(mensagem, causa);
        this.codigoErro = codigoErro;
    }

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