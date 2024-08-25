package br.com.lcano.centraldecontrole.enums;

public enum TipoLancamentoEnum {
    DESPESA("DESPESA"),
    RECEITA("RECEITA"),
    PASSIVO("PASSIVO"),
    ATIVO("ATIVO");

    private final String descricao;

    TipoLancamentoEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
