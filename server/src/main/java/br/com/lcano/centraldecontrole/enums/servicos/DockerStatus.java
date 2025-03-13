package br.com.lcano.centraldecontrole.enums.servicos;

import lombok.Getter;

@Getter
public enum DockerStatus {
    RUNNING("Em execução"),
    STOPPED("Parado"),
    PAUSED("Pausado"),
    RESTARTING("Reiniciando"),
    DEAD("Falhou"),
    NOT_FOUND("Não encontrado"),
    ERROR("Erro"),
    UNKNOWN("Desconhecido");

    private final String description;

    DockerStatus(String description) {
        this.description = description;
    }

}
