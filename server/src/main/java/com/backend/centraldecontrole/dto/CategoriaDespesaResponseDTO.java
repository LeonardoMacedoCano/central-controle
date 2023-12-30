package com.backend.centraldecontrole.dto;

import com.backend.centraldecontrole.model.CategoriaDespesa;

public record CategoriaDespesaResponseDTO(Long id, String descricao) {
    public CategoriaDespesaResponseDTO(CategoriaDespesa categoriaDespesa) {
        this(categoriaDespesa.getId(), categoriaDespesa.getDescricao());
    }
}
