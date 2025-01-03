package br.com.lcano.centraldecontrole.exception;

public class UsuarioException extends RuntimeException {
    private static final String MSG_USUARIO_NAO_ENCONTRADO = "Usuário não encontrado.";
    private static final String MSG_USUARIO_JA_CADASTRADO = "Usuário já cadastrado.";
    public static final String MSG_CREDENCIAIS_INVALIDAS = "Credenciais inválidas.";
    private static final String MSG_USUARIO_DESATIVADO = "Usuário desativado.";
    private static final String MSG_ERRO_GERAR_TOKEN = "Erro ao gerar Token.";
    private static final String MSG_TOKEN_EXPIRADO_OU_INVALIDO = "Token expirado ou inválido.";

    public static class UsuarioNaoEncontrado extends RuntimeException {
        public UsuarioNaoEncontrado() {
            super(MSG_USUARIO_NAO_ENCONTRADO);
        }
    }

    public static class UsuarioJaCadastrado extends RuntimeException {
        public UsuarioJaCadastrado() {
            super(MSG_USUARIO_JA_CADASTRADO);
        }
    }

    public static class UsuarioDesativado extends RuntimeException {
        public UsuarioDesativado() {
            super(MSG_USUARIO_DESATIVADO);
        }
    }

    public static class ErroGerarToken extends RuntimeException {
        public ErroGerarToken() {
            super(MSG_ERRO_GERAR_TOKEN);
        }
    }

    public static class TokenExpiradoOuInvalido extends RuntimeException {
        public TokenExpiradoOuInvalido() {
            super(MSG_TOKEN_EXPIRADO_OU_INVALIDO);
        }
    }
}
