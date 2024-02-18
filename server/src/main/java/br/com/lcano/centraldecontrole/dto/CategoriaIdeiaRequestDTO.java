package br.com.lcano.centraldecontrole.dto;

import br.com.lcano.centraldecontrole.domain.CategoriaIdeia;

public record CategoriaIdeiaRequestDTO(Long id, String descricao) {
    public CategoriaIdeiaRequestDTO(CategoriaIdeia categoriaIdeia) {
        this(categoriaIdeia.getId(), categoriaIdeia.getDescricao());
    }
}
