package br.com.lcano.centraldecontrole.exception.fluxocaixa;

public class ExtratoException extends RuntimeException {
    private static final String ERRO_PREENCHIMENTO = "Erro ao preencher o campo '%s' com o valor '%s' no extrato.";

    public static class ErroPreenchimentoCampo extends RuntimeException {
        public ErroPreenchimentoCampo(String campo, String valor) {
            super(String.format(ERRO_PREENCHIMENTO, campo, valor));
        }
    }
}
