package br.com.lcano.centraldecontrole.exception.fluxocaixa;

public class ExtratoException extends RuntimeException {
  private static final String MSG_EXTRATO_CONTA_REGRA_UNIQUE_PRIORIDADE = "Já existe uma regra com a prioridade %d para o usuário logado.";
  private static final String MSG_EXTRATO_CONTA_CATEGORIA_PADRAO_NAO_ENCONTRADA = "Categoria padrão não encontrada para o tipo de lançamento %s.";

  public static class ExtratoContaRegraUniquePrioridadeViolada extends RuntimeException {
    public ExtratoContaRegraUniquePrioridadeViolada(Long prioridade) {
      super(String.format(MSG_EXTRATO_CONTA_REGRA_UNIQUE_PRIORIDADE, prioridade));
    }
  }

  public static class ExtratoContaRegraCategoriaPadraoNaoEncontrada extends RuntimeException {
    public ExtratoContaRegraCategoriaPadraoNaoEncontrada(String tipoLancamento) {
      super(String.format(MSG_EXTRATO_CONTA_CATEGORIA_PADRAO_NAO_ENCONTRADA, tipoLancamento));
    }
  }
}
