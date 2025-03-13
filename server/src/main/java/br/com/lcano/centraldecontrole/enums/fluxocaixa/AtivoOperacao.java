package br.com.lcano.centraldecontrole.enums.fluxocaixa;

import lombok.Getter;

@Getter
public enum AtivoOperacao {
    VENDA("VENDA"),
    COMPRA("COMPRA");

    private final String descricao;

    AtivoOperacao(String descricao) {
        this.descricao = descricao;
    }

    public static AtivoOperacao fromDescricao(String descricao) {
        for (AtivoOperacao operacao : values()) {
            if (operacao.getDescricao().equalsIgnoreCase(descricao)) {
                return operacao;
            }
        }
        throw new IllegalArgumentException("Tipo de movimentação inválido: " + descricao);
    }
}
