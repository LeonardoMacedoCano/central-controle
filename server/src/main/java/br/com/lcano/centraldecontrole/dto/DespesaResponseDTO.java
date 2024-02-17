package br.com.lcano.centraldecontrole.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public record DespesaResponseDTO(
        Long id,
        Long idCategoria,
        String descricao,
        Double valor,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime data
) {
}
