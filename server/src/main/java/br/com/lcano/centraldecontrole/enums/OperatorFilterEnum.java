package br.com.lcano.centraldecontrole.enums;

import lombok.Getter;

@Getter
public enum OperatorFilterEnum {
    IGUAL("==", "Igual"),
    DIFERENTE("!=", "Diferente"),
    CONTEM("LIKE", "Contém"),
    MAIOR(">", "Maior"),
    MENOR("<", "Menor"),
    MAIOR_OU_IGUAL(">=", "Maior ou Igual"),
    MENOR_OU_IGUAL("<=", "Menor ou Igual");

    private final String symbol;
    private final String description;

    OperatorFilterEnum(String symbol, String description) {
        this.symbol = symbol;
        this.description = description;
    }

    public static OperatorFilterEnum fromSymbol(String symbol) {
        for (OperatorFilterEnum operator : values()) {
            if (operator.getSymbol().equals(symbol)) {
                return operator;
            }
        }
        throw new IllegalArgumentException("Operador desconhecido: " + symbol);
    }
}
