package br.com.lcano.centraldecontrole.enums.fluxocaixa;

import lombok.Getter;

@Getter
public enum DespesaFormaPagamentoEnum {
    DINHEIRO("Dinheiro"),
    PIX("PIX"),
    CARTAO_CREDITO("Cartão de Crédito"),
    CARTAO_DEBITO("Cartão de Débito");

    private final String descricao;

    DespesaFormaPagamentoEnum(String descricao) {
        this.descricao = descricao;
    }
}
