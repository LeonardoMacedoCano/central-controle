package br.com.lcano.centraldecontrole.exception;

public class ArquivoException extends RuntimeException {
    private static final String MSG_ARQUIVO_JA_IMPORTADO = "Esse arquivo já foi importado com o id %d.";
    private static final String MSG_ARQUIVO_NAO_ENCONTRADO_ID = "Arquivo não encontrado com o id %d.";

    public static class ArquivoJaImportado extends RuntimeException {
        public ArquivoJaImportado(Long id) {
            super(String.format(MSG_ARQUIVO_JA_IMPORTADO, id));
        }
    }

    public static class ArquivoNaoEncontrado extends RuntimeException {
        public ArquivoNaoEncontrado(Long id) {
            super(String.format(MSG_ARQUIVO_NAO_ENCONTRADO_ID, id));
        }
    }

}
