package br.com.lcano.centraldecontrole.enums.fluxocaixa;

import lombok.Getter;

@Getter
public enum TipoRegraExtratoContaCorrente {
    IGNORAR_DESPESA("IGNORAR_DESPESA"),
    CLASSIFICAR_DESPESA("CLASSIFICAR_DESPESA"),
    IGNORAR_RECEITA("IGNORAR_RECEITA"),
    CLASSIFICAR_RECEITA("CLASSIFICAR_RECEITA");

    private final String descricao;

    TipoRegraExtratoContaCorrente(String descricao) {
        this.descricao = descricao;
    }
}
