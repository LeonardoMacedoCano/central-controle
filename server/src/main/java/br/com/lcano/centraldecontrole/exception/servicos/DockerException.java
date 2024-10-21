package br.com.lcano.centraldecontrole.exception.servicos;

public class DockerException extends RuntimeException {
  private static final String MSG_DOCKER_NAO_ENCONTRADO_BY_NAME = "Contêiner com nome '%s' não foi encontrado.";
  private static final String MSG_DOCKER_ERRO_ALTERAR_STATUS = "Erro ao tentar alterar o status do contêiner '%s': %s";

  public static class DockerNaoEncontradoByName extends RuntimeException {
    public DockerNaoEncontradoByName(String containerName) {
      super(String.format(MSG_DOCKER_NAO_ENCONTRADO_BY_NAME, containerName));
    }
  }

  public static class DockerErroAlterarStatus extends RuntimeException {
    public DockerErroAlterarStatus(String containerName , String erro) {
      super(String.format(MSG_DOCKER_ERRO_ALTERAR_STATUS, containerName, erro));
    }
  }
}
