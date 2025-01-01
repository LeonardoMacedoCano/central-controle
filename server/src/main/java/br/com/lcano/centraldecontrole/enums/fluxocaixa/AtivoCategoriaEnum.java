package br.com.lcano.centraldecontrole.enums.fluxocaixa;

import lombok.Getter;

@Getter
public enum AtivoCategoriaEnum {
    ACAO_NACIONAL("Ação Nacional"),
    ACAO_INTERNACIONAL("Ação Internacional"),
    FUNDO_IMOBILIARIO("Fundo Imobiliário"),
    ETF("ETF"),
    CRIPTOMOEDA("Criptomoeda"),
    DESCONHECIDO("Desconhecido");

    private final String descricao;

    AtivoCategoriaEnum(String descricao) {
        this.descricao = descricao;
    }
}
