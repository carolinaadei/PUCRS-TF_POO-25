public enum ItemHospitalar {
    // Medicamentos
    DIPIRONA("Dipirona", "Analgésico e antitérmico", TipoItem.MEDICAMENTO),
    PARACETAMOL("Paracetamol", "Analgésico e antitérmico", TipoItem.MEDICAMENTO),
    AMOXICILINA("Amoxicilina", "Antibiótico", TipoItem.MEDICAMENTO),
    CEFALEXINA("Cefalexina", "Antibiótico", TipoItem.MEDICAMENTO),
    OMEPRAZOL("Omeprazol", "Antiácido e protetor gástrico", TipoItem.MEDICAMENTO),
    MORFINA("Morfina", "Analgésico opioide potente", TipoItem.MEDICAMENTO),

    // Equipamentos e materiais
    SORO_FISIOLOGICO("Soro fisiológico", "Hidratação intravenosa", TipoItem.MATERIAL),
    CAMA_HOSPITALAR("Cama hospitalar", "Acomodação do paciente", TipoItem.EQUIPAMENTO),
    MONITOR_CARDIACO("Monitor cardíaco", "Monitoramento dos sinais vitais", TipoItem.EQUIPAMENTO),
    BOMBA_INFUSAO("Bomba de infusão", "Controle de medicação intravenosa", TipoItem.EQUIPAMENTO),
    MASCARA_OXIGENIO("Máscara de oxigênio", "Suporte respiratório", TipoItem.MATERIAL);

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

    /**
     * Busca um item hospitalar pelo nome
     * @param nome o nome do item a ser buscado
     * @return o ItemHospitalar correspondente ou null se não encontrado
     */
    public static ItemHospitalar buscarPorNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return null;
        }

        for (ItemHospitalar item : values()) {
            if (item.getNomeItem().toLowerCase().contains(nome.toLowerCase())) {
                return item;
            }
        }
        return null;
    }

    /**
     * Retorna todos os itens de um determinado tipo
     * @param tipo o tipo de item desejado
     * @return array com os itens do tipo especificado
     */
    public static ItemHospitalar[] getItensPorTipo(TipoItem tipo) {
        return java.util.Arrays.stream(values())
                .filter(item -> item.getTipo() == tipo)
                .toArray(ItemHospitalar[]::new);
    }

    /**
     * Enum para categorizar os tipos de itens hospitalares
     */
    public enum TipoItem {
        MEDICAMENTO("Medicamento"),
        EQUIPAMENTO("Equipamento"),
        MATERIAL("Material");

        private final String descricao;

        TipoItem(String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }

        @Override
        public String toString() {
            return descricao;
        }
    }
}