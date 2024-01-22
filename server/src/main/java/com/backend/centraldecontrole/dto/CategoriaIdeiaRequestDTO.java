package com.backend.centraldecontrole.dto;

import com.backend.centraldecontrole.model.CategoriaIdeia;

public record CategoriaIdeiaRequestDTO(Long id, String descricao) {
    public CategoriaIdeiaRequestDTO(CategoriaIdeia categoriaIdeia) {
        this(categoriaIdeia.getId(), categoriaIdeia.getDescricao());
    }
}
