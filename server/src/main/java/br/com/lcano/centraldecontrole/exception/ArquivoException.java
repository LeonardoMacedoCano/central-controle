package br.com.lcano.centraldecontrole.exception;

public class ArquivoException extends RuntimeException {
    private static final String MSG_ARQUIVO_JA_IMPORTADO = "Esse arquivo já foi importado com o id %d.";

    public static class ArquivoJaImportado extends RuntimeException {
        public ArquivoJaImportado(Long id) {
            super(String.format(MSG_ARQUIVO_JA_IMPORTADO, id));
        }
    }

}
