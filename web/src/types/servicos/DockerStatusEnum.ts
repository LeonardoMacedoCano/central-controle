export enum DockerStatusEnum {
  RUNNING = "RUNNING",
  STOPPED = "STOPPED",
  NOT_FOUND = "NOT_FOUND",
  ERROR = "ERROR",
}

export const getDockerStatusDescription = (status?: DockerStatusEnum): string => {
  if (!status) {
    return "desconhecido";
  }

  const statusDescriptions: { [key in DockerStatusEnum]: string } = {
    [DockerStatusEnum.RUNNING]: "em execução",
    [DockerStatusEnum.STOPPED]: "parado",
    [DockerStatusEnum.NOT_FOUND]: "não encontrado",
    [DockerStatusEnum.ERROR]: "com erro",
  };

  return statusDescriptions[status] || "desconhecido";
};
