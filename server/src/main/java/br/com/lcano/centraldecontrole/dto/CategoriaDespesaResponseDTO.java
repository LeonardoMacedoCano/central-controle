package br.com.lcano.centraldecontrole.dto;

import br.com.lcano.centraldecontrole.model.CategoriaDespesa;

public record CategoriaDespesaResponseDTO(Long id, String descricao) {
    public CategoriaDespesaResponseDTO(CategoriaDespesa categoriaDespesa) {
        this(categoriaDespesa.getId(), categoriaDespesa.getDescricao());
    }
}
