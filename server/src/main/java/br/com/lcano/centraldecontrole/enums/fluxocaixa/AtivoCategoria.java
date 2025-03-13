package br.com.lcano.centraldecontrole.enums.fluxocaixa;

import lombok.Getter;

@Getter
public enum AtivoCategoria {
    ACAO_NACIONAL("Ação Nacional"),
    ACAO_INTERNACIONAL("Ação Internacional"),
    FUNDO_IMOBILIARIO("Fundo Imobiliário"),
    ETF("ETF"),
    CRIPTOMOEDA("Criptomoeda"),
    DESCONHECIDO("Desconhecido");

    private final String descricao;

    AtivoCategoria(String descricao) {
        this.descricao = descricao;
    }
}
