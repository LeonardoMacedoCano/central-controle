package com.backend.centraldecontrole.dto;

import com.backend.centraldecontrole.util.CustomDateDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Date;

public record DespesaRequestDTO(
        Long idCategoria,
        String descricao,
        Double valor,
        @JsonDeserialize(using = CustomDateDeserializer.class)
        Date data
) {

}
