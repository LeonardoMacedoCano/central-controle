package br.com.lcano.centraldecontrole.exception.fluxocaixa;

public class ExtratoException extends RuntimeException {
  private static final String MSG_ERRO_EXTRATO_CONTA_REGRA_UNIQUE_PRIORIDADE = "Já existe uma regra com a prioridade %d para o usuário logado.";

  public static class ExtratoContaRegraUniquePrioridadeViolada extends RuntimeException {
    public ExtratoContaRegraUniquePrioridadeViolada(Long prioridade) {
      super(String.format(MSG_ERRO_EXTRATO_CONTA_REGRA_UNIQUE_PRIORIDADE, prioridade));
    }
  }
}
