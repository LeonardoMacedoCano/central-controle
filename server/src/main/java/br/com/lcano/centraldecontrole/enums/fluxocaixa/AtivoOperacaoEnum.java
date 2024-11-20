package br.com.lcano.centraldecontrole.enums.fluxocaixa;

import lombok.Getter;

@Getter
public enum AtivoOperacaoEnum {
    VENDA("VENDA"),
    COMPRA("COMPRA");

    private final String descricao;

    AtivoOperacaoEnum(String descricao) {
        this.descricao = descricao;
    }
}
