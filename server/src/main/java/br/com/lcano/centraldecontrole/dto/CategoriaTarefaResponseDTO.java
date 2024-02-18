package br.com.lcano.centraldecontrole.dto;

import br.com.lcano.centraldecontrole.domain.CategoriaTarefa;

public record CategoriaTarefaResponseDTO(Long id, String descricao) {
    public CategoriaTarefaResponseDTO(CategoriaTarefa categoriaTarefa) {
        this(categoriaTarefa.getId(), categoriaTarefa.getDescricao());
    }
}
