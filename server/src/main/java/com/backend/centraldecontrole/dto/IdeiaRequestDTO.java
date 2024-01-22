package com.backend.centraldecontrole.dto;

import com.backend.centraldecontrole.util.CustomDateDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.Date;

public record IdeiaRequestDTO(Long idCategoria,
                              String titulo,
                              String descricao,
                              @JsonDeserialize(using = CustomDateDeserializer.class)
                              Date dataPrazo,
                              Boolean finalizado
) {
    @JsonPOJOBuilder(withPrefix = "")
    public static class IdeiaRequestDTOBuilder {
    }
}
