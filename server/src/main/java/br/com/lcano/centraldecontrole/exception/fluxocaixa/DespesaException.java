package br.com.lcano.centraldecontrole.exception.fluxocaixa;

public class DespesaException extends RuntimeException {
    private static final String MSG_CATEGORIA_NAO_ENCONTRADA_BY_ID = "Categoria %d não encontrada.";
    private static final String MSG_DESPESA_NAO_ENCONTRADA_BY_LANCAMENTOID = "Despesa não encontrada para o lançamento %d.";

    public static class CategoriaNaoEncontradaById extends RuntimeException {
        public CategoriaNaoEncontradaById(Long id) {
            super(String.format(MSG_CATEGORIA_NAO_ENCONTRADA_BY_ID, id));
        }
    }

    public static class DespesaNaoEncontradaByLancamentoId extends RuntimeException {
        public DespesaNaoEncontradaByLancamentoId(Long lancamentoId) {
            super(String.format(MSG_DESPESA_NAO_ENCONTRADA_BY_LANCAMENTOID, lancamentoId));
        }
    }
}
