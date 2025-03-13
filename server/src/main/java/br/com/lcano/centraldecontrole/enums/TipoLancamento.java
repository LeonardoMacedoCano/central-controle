package br.com.lcano.centraldecontrole.enums;

import lombok.Getter;

@Getter
public enum TipoLancamento {
    DESPESA("DESPESA"),
    RENDA("RENDA"),
    ATIVO("ATIVO");

    private final String descricao;

    TipoLancamento(String descricao) {
        this.descricao = descricao;
    }
}
