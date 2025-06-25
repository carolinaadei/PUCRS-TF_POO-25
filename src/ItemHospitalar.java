public enum ItemHospitalar {

    DIPIRONA("Dipirona", "Analgésico e antitérmico", TipoItem.MEDICAMENTO),
    PARACETAMOL("Paracetamol", "Analgésico e antitérmico", TipoItem.MEDICAMENTO),
    AMOXICILINA("Amoxicilina", "Antibiótico", TipoItem.MEDICAMENTO),
    CEFALEXINA("Cefalexina", "Antibiótico", TipoItem.MEDICAMENTO),
    OMEPRAZOL("Omeprazol", "Antiácido e protetor gástrico", TipoItem.MEDICAMENTO),
    MORFINA("Morfina", "Analgésico opioide potente", TipoItem.MEDICAMENTO),
    IBUPROFENO("Ibuprofeno", "Antiinflamatório não esteroide", TipoItem.MEDICAMENTO),
    LOSARTANA("Losartana", "Anti-hipertensivo", TipoItem.MEDICAMENTO),
    INSULINA("Insulina", "Controle da glicose", TipoItem.MEDICAMENTO),
    FUROSEMIDA("Furosemida", "Diurético", TipoItem.MEDICAMENTO),
    RANITIDINA("Ranitidina", "Bloqueador de ácido gástrico", TipoItem.MEDICAMENTO),

    SORO_FISIOLOGICO("Soro fisiológico", "Hidratação intravenosa", TipoItem.MATERIAL),
    GAZE("Gaze", "Curativo e limpeza de feridas", TipoItem.MATERIAL),
    ALGODAO("Algodão", "Higienização e curativos", TipoItem.MATERIAL),
    SERINGA_DESCARTAVEL("Seringa descartável", "Administração de medicamentos", TipoItem.MATERIAL),
    LUVAS_DESCARTAVEIS("Luvas descartáveis", "Higiene e segurança", TipoItem.MATERIAL),
    MASCARA_OXIGENIO("Máscara de oxigênio", "Suporte respiratório", TipoItem.MATERIAL),

    CAMA_HOSPITALAR("Cama hospitalar", "Acomodação do paciente", TipoItem.EQUIPAMENTO),
    MONITOR_CARDIACO("Monitor cardíaco", "Monitoramento dos sinais vitais", TipoItem.EQUIPAMENTO),
    BOMBA_INFUSAO("Bomba de infusão", "Controle de medicação intravenosa", TipoItem.EQUIPAMENTO),
    DESFIBRILADOR("Desfibrilador", "Reanimação cardíaca", TipoItem.EQUIPAMENTO),
    VENTILADOR_MECANICO("Ventilador mecânico", "Assistência respiratória", TipoItem.EQUIPAMENTO),
    CADEIRA_RODAS("Cadeira de rodas", "Mobilidade do paciente", TipoItem.EQUIPAMENTO);

    private final String nomeItem;
    private final String descricaoItem;
    private final TipoItem tipo;

    ItemHospitalar(String nomeItem, String descricaoItem, TipoItem tipo) {
        this.nomeItem = nomeItem;
        this.descricaoItem = descricaoItem;
        this.tipo = tipo;
    }

    public String getNomeItem() {
        return nomeItem;
    }

    public String getDescricaoItem() {
        return descricaoItem;
    }

    public TipoItem getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return nomeItem + " - " + descricaoItem + " (" + tipo + ")";
    }

    public enum TipoItem {
        MEDICAMENTO("Medicamento"),
        EQUIPAMENTO("Equipamento"),
        MATERIAL("Material");

        private final String descricao;

        TipoItem(String descricao) {
            this.descricao = descricao;
        }

        @Override
        public String toString() {
            return descricao;
        }
    }
}