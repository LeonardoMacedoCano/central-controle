package br.com.lcano.centraldecontrole.enums.servicos;

import lombok.Getter;

@Getter
public enum ContainerActionEnum {
    START("iniciado"),
    STOP("parado"),
    RESTART("reiniciado");

    private final String descricao;

    ContainerActionEnum(String descricao) {
        this.descricao = descricao;
    }
}
