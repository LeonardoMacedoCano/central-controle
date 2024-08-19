package br.com.lcano.centraldecontrole.exception.fluxocaixa;

public class DespesaException extends RuntimeException {
    private static final String MSG_CATEGORIA_DESPESA_NAO_ENCONTRADA_BY_ID = "Categoria de despesa %d não encontrada.";
    private static final String MSG_DESPESA_NAO_ENCONTRADA_BY_ID = "Despesa %d não encontrada.";
    private static final String MSG_FORMA_PAGAMENTO_NAO_ENCONTRADA_BY_ID = "Forma de pagamento %d não encontrado.";
    private static final String MSG_DESPESA_NAO_ENCONTRADA_BY_LANCAMENTOID = "Despesa não encontrada para o lançamento %d.";

    public static class CategoriaDespesaNaoEncontradaById extends RuntimeException {
        public CategoriaDespesaNaoEncontradaById(Long id) {
            super(String.format(MSG_CATEGORIA_DESPESA_NAO_ENCONTRADA_BY_ID, id));
        }
    }

    public static class DespesaNaoEncontradaById extends RuntimeException {
        public DespesaNaoEncontradaById(Long id) {
            super(String.format(MSG_DESPESA_NAO_ENCONTRADA_BY_ID, id));
        }
    }

    public static class FormaPagamentoNaoEncontradaById extends RuntimeException {
        public FormaPagamentoNaoEncontradaById(Long id) {
            super(String.format(MSG_FORMA_PAGAMENTO_NAO_ENCONTRADA_BY_ID, id));
        }
    }

    public static class DespesaNaoEncontradaByLancamentoId extends RuntimeException {
        public DespesaNaoEncontradaByLancamentoId(Long lancamentoId) {
            super(String.format(MSG_DESPESA_NAO_ENCONTRADA_BY_LANCAMENTOID, lancamentoId));
        }
    }
}
