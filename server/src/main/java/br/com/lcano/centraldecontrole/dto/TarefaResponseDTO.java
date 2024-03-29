package br.com.lcano.centraldecontrole.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public record TarefaResponseDTO(
        Long id,
        Long idCategoria,
        String titulo,
        String descricao,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime dataPrazo,
        Boolean finalizado) {
}
