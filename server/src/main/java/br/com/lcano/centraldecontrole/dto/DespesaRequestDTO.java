package br.com.lcano.centraldecontrole.dto;

import br.com.lcano.centraldecontrole.util.CustomDateDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.Date;

public record DespesaRequestDTO(
        Long idCategoria,
        String descricao,
        Double valor,
        @JsonDeserialize(using = CustomDateDeserializer.class)
        Date data
) {
        @JsonPOJOBuilder(withPrefix = "")
        public static class DespesaRequestDTOBuilder {
        }
}
