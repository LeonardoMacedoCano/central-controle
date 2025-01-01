package br.com.lcano.centraldecontrole.enums.fluxocaixa;

import lombok.Getter;

@Getter
public enum TipoRegraExtratoContaCorrente {
    IGNORAR("IGNORAR"),
    CLASSIFICAR("CLASSIFICAR");

    private final String descricao;

    TipoRegraExtratoContaCorrente(String descricao) {
        this.descricao = descricao;
    }
}
