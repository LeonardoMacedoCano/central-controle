package br.com.lcano.centraldecontrole.exception.fluxocaixa;

public class FluxoCaixaConfigException extends RuntimeException {
  private static final String MSG_UNIQUE_PRIORIDADE_VIOLADA = "Já existe uma regra com a prioridade %d para o usuário logado.";
  private static final String CATEGORIA_PADRAO_NAO_ENCONTRADA = "Categoria padrão não encontrada para o tipo de lançamento %s.";
  private static final String CONFIG_NAO_ENCONTRADA = "Configuração do fluxo de caixa não encontrada para o usuário logado.";

  public static class UniquePrioridadeViolada extends RuntimeException {
    public UniquePrioridadeViolada(Long prioridade) {
      super(String.format(MSG_UNIQUE_PRIORIDADE_VIOLADA, prioridade));
    }
  }

  public static class CategoriaPadraoNaoEncontrada extends RuntimeException {
    public CategoriaPadraoNaoEncontrada(String tipoLancamento) {
      super(String.format(CATEGORIA_PADRAO_NAO_ENCONTRADA, tipoLancamento));
    }
  }

  public static class ConfigNaoEncontrada extends RuntimeException {
    public ConfigNaoEncontrada() {
      super(String.format(CONFIG_NAO_ENCONTRADA));
    }
  }
}
