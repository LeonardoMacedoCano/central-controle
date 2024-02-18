package br.com.lcano.centraldecontrole.exception;

public class IdeiaException extends RuntimeException {
    public static final String MSG_CATEGORIA_IDEIA_NAO_ENCONTRADA_BY_ID = "Categoria de ideia não encontrada com o id: %d.";
    public static final String MSG_IDEIA_NAO_ENCONTRADA_BY_ID = "Ideia não encontrada com o id: %d.";

    public static class CategoriaIdeiaNaoEncontradaById extends RuntimeException {
        public CategoriaIdeiaNaoEncontradaById(Long IDCategoriaIdeia) {
            super(String.format(MSG_CATEGORIA_IDEIA_NAO_ENCONTRADA_BY_ID, IDCategoriaIdeia));
        }
    }

    public static class IdeiaNaoEncontradaById extends RuntimeException {
        public IdeiaNaoEncontradaById(Long IDIdeia) {
            super(String.format(MSG_IDEIA_NAO_ENCONTRADA_BY_ID, IDIdeia));
        }
    }
}
