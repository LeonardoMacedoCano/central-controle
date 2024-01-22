package com.backend.centraldecontrole.dto;

import com.backend.centraldecontrole.model.CategoriaIdeia;

public record CategoriaIdeiaResponseDTO(Long id, String descricao) {
    public CategoriaIdeiaResponseDTO(CategoriaIdeia categoriaIdeia) {
        this(categoriaIdeia.getId(), categoriaIdeia.getDescricao());
    }
}
