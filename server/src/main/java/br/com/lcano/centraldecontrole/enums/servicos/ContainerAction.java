package br.com.lcano.centraldecontrole.enums.servicos;

import lombok.Getter;

@Getter
public enum ContainerAction {
    START("iniciado"),
    STOP("parado"),
    RESTART("reiniciado");

    private final String descricao;

    ContainerAction(String descricao) {
        this.descricao = descricao;
    }
}
