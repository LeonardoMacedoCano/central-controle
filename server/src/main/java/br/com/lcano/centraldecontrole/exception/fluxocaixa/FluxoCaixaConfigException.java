package br.com.lcano.centraldecontrole.exception.fluxocaixa;

public class FluxoCaixaConfigException extends RuntimeException {
  private static final String CATEGORIA_PADRAO_NAO_ENCONTRADA = "Categoria padrão não encontrada para o tipo de lançamento %s.";
  private static final String PARAMETRO_NAO_ENCONTRADA = "Parâmetro do fluxo de caixa não encontrada para o usuário logado.";

  public static class CategoriaPadraoNaoEncontrada extends RuntimeException {
    public CategoriaPadraoNaoEncontrada(String tipoLancamento) {
      super(String.format(CATEGORIA_PADRAO_NAO_ENCONTRADA, tipoLancamento));
    }
  }

  public static class ParametroNaoEncontrado extends RuntimeException {
    public ParametroNaoEncontrado() {
      super(String.format(PARAMETRO_NAO_ENCONTRADA));
    }
  }
}
