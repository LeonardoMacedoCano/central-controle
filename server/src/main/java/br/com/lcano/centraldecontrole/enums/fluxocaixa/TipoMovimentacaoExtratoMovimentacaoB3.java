package br.com.lcano.centraldecontrole.enums.fluxocaixa;

import lombok.Getter;

@Getter
public enum TipoMovimentacaoExtratoMovimentacaoB3 {
    TRANSFERENCIA_LIQUIDACAO("Transferência - Liquidação", true),
    RENDIMENTO("Rendimento", false),
    DIVIDENDO("Dividendo", false),
    JUROS_SOBRE_CAPITAL_PROPRIO("Juros Sobre Capital Próprio", false);

    private final String descricao;
    private final boolean isAtivo;

    TipoMovimentacaoExtratoMovimentacaoB3(String descricao, boolean isAtivo) {
        this.descricao = descricao;
        this.isAtivo = isAtivo;
    }

    public static TipoMovimentacaoExtratoMovimentacaoB3 fromDescricao(String descricao) {
        for (TipoMovimentacaoExtratoMovimentacaoB3 tipo : values()) {
            if (tipo.getDescricao().equalsIgnoreCase(descricao)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Tipo de movimentação desconhecido: " + descricao);
    }
}
