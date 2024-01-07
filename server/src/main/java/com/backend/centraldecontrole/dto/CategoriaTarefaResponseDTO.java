package com.backend.centraldecontrole.dto;

import com.backend.centraldecontrole.model.CategoriaTarefa;

public record CategoriaTarefaResponseDTO(Long id, String descricao) {
    public CategoriaTarefaResponseDTO(CategoriaTarefa categoriaTarefa) {
        this(categoriaTarefa.getId(), categoriaTarefa.getDescricao());
    }
}
