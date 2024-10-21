package br.com.lcano.centraldecontrole.enums.servicos;

public enum DockerStatusEnum {
    RUNNING("Em execução"),
    STOPPED("Parado"),
    NOT_FOUND("Não encontrado"),
    ERROR("Erro");

    private final String description;

    DockerStatusEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
