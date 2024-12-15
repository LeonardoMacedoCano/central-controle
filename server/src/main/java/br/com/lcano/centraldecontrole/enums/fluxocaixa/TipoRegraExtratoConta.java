package br.com.lcano.centraldecontrole.enums.fluxocaixa;

import lombok.Getter;

@Getter
public enum TipoRegraExtratoConta {
    IGNORAR("IGNORAR"),
    CLASSIFICAR("CLASSIFICAR");

    private final String descricao;

    TipoRegraExtratoConta(String descricao) {
        this.descricao = descricao;
    }
}
