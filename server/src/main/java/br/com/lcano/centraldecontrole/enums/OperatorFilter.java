package br.com.lcano.centraldecontrole.enums;

import lombok.Getter;

@Getter
public enum OperatorFilter {
    IGUAL("==", "Igual"),
    DIFERENTE("!=", "Diferente"),
    CONTEM("LIKE", "Contém"),
    MAIOR(">", "Maior"),
    MENOR("<", "Menor"),
    MAIOR_OU_IGUAL(">=", "Maior ou Igual"),
    MENOR_OU_IGUAL("<=", "Menor ou Igual");

    private final String symbol;
    private final String description;

    OperatorFilter(String symbol, String description) {
        this.symbol = symbol;
        this.description = description;
    }

    public static OperatorFilter fromSymbol(String symbol) {
        for (OperatorFilter operator : values()) {
            if (operator.getSymbol().equals(symbol)) {
                return operator;
            }
        }
        throw new IllegalArgumentException("Operador desconhecido: " + symbol);
    }
}
