package br.com.lcano.centraldecontrole.dto;

import br.com.lcano.centraldecontrole.util.CustomDateDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.Date;

public record TarefaRequestDTO(
        Long idCategoria,
        String titulo,
        String descricao,
        @JsonDeserialize(using = CustomDateDeserializer.class)
        Date dataPrazo,
        Boolean finalizado
) {
    @JsonPOJOBuilder(withPrefix = "")
    public static class TarefaRequestDTOBuilder {
    }
}