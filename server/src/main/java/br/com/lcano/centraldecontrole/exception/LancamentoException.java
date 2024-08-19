package br.com.lcano.centraldecontrole.exception;

public class LancamentoException extends RuntimeException {
    private static final String MSG_LANCAMENTO_NAO_ENCONTRADO_BY_ID = "Lançamento %d não encontrado.";
    private static final String MSG_LANCAMENTO_TIPO_NAO_SUPORTADO = "Tipo de lançamento %s não suportado";

    public static class LancamentoNaoEncontradoById extends RuntimeException {
        public LancamentoNaoEncontradoById(Long id) {
            super(String.format(MSG_LANCAMENTO_NAO_ENCONTRADO_BY_ID, id));
        }
    }

    public static class LancamentoTipoNaoSuportado extends RuntimeException {
        public LancamentoTipoNaoSuportado(String tipo) {
            super(String.format(MSG_LANCAMENTO_TIPO_NAO_SUPORTADO, tipo));
        }
    }
}
