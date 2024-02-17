package br.com.lcano.centraldecontrole.dto;

import br.com.lcano.centraldecontrole.model.CategoriaIdeia;

public record CategoriaIdeiaResponseDTO(Long id, String descricao) {
    public CategoriaIdeiaResponseDTO(CategoriaIdeia categoriaIdeia) {
        this(categoriaIdeia.getId(), categoriaIdeia.getDescricao());
    }
}
