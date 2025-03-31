package br.com.lcano.centraldecontrole.enums.fluxocaixa;

import lombok.Getter;

@Getter
public enum CamposExtratoMovimentacaoB3 {
    TIPO_OPERACAO(0, "Tipo Operação"),
    DATA_MOVIMENTACAO(1, "Data Movimentação"),
    TIPO_MOVIMENTACAO(2, "Tipo Movimentação"),
    PRODUTO(3, "Produto"),
    QUANTIDADE(5, "Quantidade"),
    PRECO_UNITARIO(6, "Preço Unitário"),
    PRECO_TOTAL(7, "Preço Total");

    private final int index;
    private final String nome;

    CamposExtratoMovimentacaoB3(int index, String nome) {
        this.index = index;
        this.nome = nome;
    }
}
