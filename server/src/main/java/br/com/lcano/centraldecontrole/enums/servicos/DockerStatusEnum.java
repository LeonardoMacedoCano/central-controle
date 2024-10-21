package br.com.lcano.centraldecontrole.enums.servicos;

import lombok.Getter;

@Getter
public enum DockerStatusEnum {
    RUNNING("Em execução"),
    STOPPED("Parado"),
    PAUSED("Pausado"),
    RESTARTING("Reiniciando"),
    DEAD("Falhou"),
    NOT_FOUND("Não encontrado"),
    ERROR("Erro"),
    UNKNOWN("Desconhecido");

    private final String description;

    DockerStatusEnum(String description) {
        this.description = description;
    }

}
