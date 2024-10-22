export enum DockerStatusEnum {
  RUNNING = "RUNNING",
  STOPPED = "STOPPED",
  PAUSED = "PAUSED",
  RESTARTING = "RESTARTING",
  DEAD = "DEAD",
  NOT_FOUND = "NOT_FOUND",
  ERROR = "ERROR",
  UNKNOWN = "UNKNOWN",
}

export const getDescricaoDockerStatus = (status?: DockerStatusEnum): string => {
  if (!status) {
    return "desconhecido";
  }

  const statusDescriptions: { [key in DockerStatusEnum]: string } = {
    [DockerStatusEnum.RUNNING]: "em execução",
    [DockerStatusEnum.STOPPED]: "parado",
    [DockerStatusEnum.PAUSED]: "pausado",
    [DockerStatusEnum.RESTARTING]: "reiniciando",
    [DockerStatusEnum.DEAD]: "falhou",
    [DockerStatusEnum.NOT_FOUND]: "não encontrado",
    [DockerStatusEnum.ERROR]: "com erro",
    [DockerStatusEnum.UNKNOWN]: "desconhecido",
  };

  return statusDescriptions[status] || "desconhecido";
};
