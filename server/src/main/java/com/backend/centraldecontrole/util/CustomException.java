package com.backend.centraldecontrole.util;

public class CustomException extends RuntimeException {
    public static class UsuarioJaCadastradoException extends RuntimeException {
        public UsuarioJaCadastradoException() {
            super(MensagemConstantes.USUARIO_JA_CADASTRADO);
        }
    }

    public static class UsuarioNaoEncontradoException extends RuntimeException {
        public UsuarioNaoEncontradoException() {
            super(MensagemConstantes.USUARIO_NAO_ENCONTRADO);
        }
    }

    public static class GerarTokenException extends RuntimeException {
        public GerarTokenException() {
            super(MensagemConstantes.ERRO_GERAR_TOKEN);
        }
    }

    public static class TokenExpiradoOuInvalidoException extends RuntimeException {
        public TokenExpiradoOuInvalidoException() {
            super(MensagemConstantes.TOKEN_EXPIRADO_OU_INVALIDO);
        }
    }

}
