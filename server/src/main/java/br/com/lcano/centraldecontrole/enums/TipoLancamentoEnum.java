package br.com.lcano.centraldecontrole.enums;

import lombok.Getter;

@Getter
public enum TipoLancamentoEnum {
    DESPESA("DESPESA"),
    RECEITA("RECEITA"),
    ATIVO("ATIVO");

    private final String descricao;

    TipoLancamentoEnum(String descricao) {
        this.descricao = descricao;
    }
}
