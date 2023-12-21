package com.backend.centraldecontrole.dto;
import java.util.Date;

public record DespesaResponseDTO(Long id, Long idCategoria, String descricao, Double valor, Date data) {
}
