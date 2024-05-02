package br.com.lcano.centraldecontrole.exception;

public class DespesaException extends RuntimeException {
    public static final String MSG_CATEGORIA_DESPESA_NAO_ENCONTRADA_BY_ID = "Categoria de despesa não encontrada com o id: %d.";
    public static final String MSG_DESPESA_NAO_ENCONTRADA_BY_ID = "Despesa não encontrada com o id: %d.";
    public static final String MSG_FORMA_PAGAMENTO_NAO_ENCONTRADA_BY_ID = "Despesa não encontrada com o id: %d.";

    public static class CategoriaDespesaNaoEncontradaById extends RuntimeException {
        public CategoriaDespesaNaoEncontradaById(Long IDCategoriaDespesa) {
            super(String.format(MSG_CATEGORIA_DESPESA_NAO_ENCONTRADA_BY_ID, IDCategoriaDespesa));
        }
    }

    public static class DespesaNaoEncontradaById extends RuntimeException {
        public DespesaNaoEncontradaById(Long IDDespesa) {
            super(String.format(MSG_DESPESA_NAO_ENCONTRADA_BY_ID, IDDespesa));
        }
    }

    public static class FormaPagamentoNaoEncontradaById extends RuntimeException {
        public FormaPagamentoNaoEncontradaById(Long IDFormaPagamento) {
            super(String.format(MSG_FORMA_PAGAMENTO_NAO_ENCONTRADA_BY_ID, IDFormaPagamento));
        }
    }
}
