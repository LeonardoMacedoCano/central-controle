package br.com.lcano.centraldecontrole.util;

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

    public static class UsuarioDesativadoException extends RuntimeException {
        public UsuarioDesativadoException() {
            super(MensagemConstantes.USUARIO_DESATIVADO);
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

    public static class CategoriaDespesaNaoEncontradaComIdException extends RuntimeException {
        public CategoriaDespesaNaoEncontradaComIdException(Long IDCategoriaDespesa) {
            super(String.format(MensagemConstantes.CATEGORIA_DESPESA_NAO_ENCONTRADA_COM_ID, IDCategoriaDespesa));
        }
    }

    public static class DespesaNaoEncontradaComIdException extends RuntimeException {
        public DespesaNaoEncontradaComIdException(Long IDDespesa) {
            super(String.format(MensagemConstantes.DESPESA_NAO_ENCONTRADA_COM_ID, IDDespesa));
        }
    }

    public static class CategoriaTarefaNaoEncontradaComIdException extends RuntimeException {
        public CategoriaTarefaNaoEncontradaComIdException(Long IDCategoriaTarefa) {
            super(String.format(MensagemConstantes.CATEGORIA_TAREFA_NAO_ENCONTRADA_COM_ID, IDCategoriaTarefa));
        }
    }

    public static class TarefaNaoEncontradaComIdException extends RuntimeException {
        public TarefaNaoEncontradaComIdException(Long IDTarefa) {
            super(String.format(MensagemConstantes.TAREFA_NAO_ENCONTRADA_COM_ID, IDTarefa));
        }
    }

    public static class CategoriaIdeiaNaoEncontradaComIdException extends RuntimeException {
        public CategoriaIdeiaNaoEncontradaComIdException(Long IDCategoriaIdeia) {
            super(String.format(MensagemConstantes.CATEGORIA_IDEIA_NAO_ENCONTRADA_COM_ID, IDCategoriaIdeia));
        }
    }

    public static class IdeiaNaoEncontradaComIdException extends RuntimeException {
        public IdeiaNaoEncontradaComIdException(Long IDIdeia) {
            super(String.format(MensagemConstantes.IDEIA_NAO_ENCONTRADA_COM_ID, IDIdeia));
        }
    }

}
