package com.backend.centraldecontrole.dto;

import java.util.Date;

public record DespesaRequestDTO(Long idCategoria, String descricao, Double valor, Date data) {
}
