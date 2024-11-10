package br.com.lcano.centraldecontrole.exception.fluxocaixa;

public class ReceitaException extends RuntimeException {
  private static final String MSG_CATEGORIA_NAO_ENCONTRADA_BY_ID = "Categoria %d não encontrada.";
  private static final String MSG_RECEITA_NAO_ENCONTRADA_BY_LANCAMENTOID = "Receita não encontrada para o lançamento %d.";

  public static class CategoriaNaoEncontradaById extends RuntimeException {
    public CategoriaNaoEncontradaById(Long id) {
      super(String.format(MSG_CATEGORIA_NAO_ENCONTRADA_BY_ID, id));
    }
  }

  public static class ReceitaNaoEncontradaByLancamentoId extends RuntimeException {
    public ReceitaNaoEncontradaByLancamentoId(Long lancamentoId) {
      super(String.format(MSG_RECEITA_NAO_ENCONTRADA_BY_LANCAMENTOID, lancamentoId));
    }
  }
}
