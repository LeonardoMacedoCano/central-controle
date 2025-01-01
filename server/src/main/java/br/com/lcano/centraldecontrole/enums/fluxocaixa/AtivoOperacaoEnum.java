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

    public static AtivoOperacaoEnum fromDescricao(String descricao) {
        for (AtivoOperacaoEnum operacao : values()) {
            if (operacao.getDescricao().equalsIgnoreCase(descricao)) {
                return operacao;
            }
        }
        throw new IllegalArgumentException("Tipo de movimentação inválido: " + descricao);
    }
}
