package br.com.lcano.centraldecontrole.enums.fluxocaixa;

import lombok.Getter;
import java.util.Map;
import java.util.HashMap;

@Getter
public enum AtivoOperacao {
    VENDA("Venda"),
    COMPRA("Compra");

    private final String descricao;

    private static final Map<String, AtivoOperacao> descricaoMap = new HashMap<>();

    static {
        for (AtivoOperacao operacao : values()) {
            descricaoMap.put(operacao.getDescricao().toUpperCase(), operacao);
        }
        descricaoMap.put("DEBITO", VENDA);
        descricaoMap.put("CREDITO", COMPRA);
    }

    AtivoOperacao(String descricao) {
        this.descricao = descricao;
    }

    public static AtivoOperacao fromDescricao(String descricao) {
        AtivoOperacao operacao = descricaoMap.get(descricao.toUpperCase());
        if (operacao == null) {
            throw new IllegalArgumentException("Tipo de movimentação inválido: " + descricao);
        }
        return operacao;
    }
}
