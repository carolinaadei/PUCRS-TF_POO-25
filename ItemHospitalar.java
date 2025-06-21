public enum ItemHospitalar {
    DIPIRONA("Dipirona", "Analgésico e antitérmico"),
    PARACETAMOL("Paracetamol", "Analgésico e antitérmico"),
    AMOXICILINA("Amoxicilina", "Antibiótico"),
    CEFALEXINA("Cefalexina", "Antibiótico"),
    OMEPRAZOL("Omeprazol", "Antiácido e protetor gástrico"),
    MORFINA("Morfina", "Analgésico opioide potente"),

    SORO_FISIOLOGICO("Soro fisiológico", "Hidratação intravenosa"),
    CAMA_HOSPITALAR("Cama hospitalar", "Acomodação do paciente"),
    MONITOR_CARDIACO("Monitor cardíaco", "Monitoramento dos sinais vitais"),
    BOMBA_INFUSAO("Bomba de infusão", "Controle de medicação intravenosa"),
    MASCARA_OXIGENIO("Máscara de oxigênio", "Suporte respiratório");

    private final String nomeItem;
    private final String descricaoItem;

    ItemHospitalar(String nomeItem, String descricaoItem) {
        this.nomeItem = nomeItem;
        this.descricaoItem = descricaoItem;
    }

    public String getNomeItem() {
        return nomeItem;
    }

    public String getDescricaoItem() {
        return descricaoItem;
    }

    @Override
    public String toString() {
        return nomeItem + " - " + descricaoItem;
    }
}