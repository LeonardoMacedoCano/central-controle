package br.com.lcano.centraldecontrole.exception;

public class TarefaException extends RuntimeException {
    public static final String MSG_CATEGORIA_TAREFA_NAO_ENCONTRADA_BY_ID = "Categoria de tarefa não encontrada com o id: %d.";
    public static final String MSG_TAREFA_NAO_ENCONTRADA_BY_ID = "Tarefa não encontrada com o id: %d.";

    public static class CategoriaTarefaNaoEncontradaById extends RuntimeException {
        public CategoriaTarefaNaoEncontradaById(Long IDCategoriaTarefa) {
            super(String.format(MSG_CATEGORIA_TAREFA_NAO_ENCONTRADA_BY_ID, IDCategoriaTarefa));
        }
    }

    public static class TarefaNaoEncontradaById extends RuntimeException {
        public TarefaNaoEncontradaById(Long IDTarefa) {
            super(String.format(MSG_TAREFA_NAO_ENCONTRADA_BY_ID, IDTarefa));
        }
    }
}
