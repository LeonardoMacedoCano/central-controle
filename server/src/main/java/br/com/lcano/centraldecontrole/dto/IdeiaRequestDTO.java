package br.com.lcano.centraldecontrole.dto;

import br.com.lcano.centraldecontrole.util.CustomDateDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Date;

public record IdeiaRequestDTO(Long idCategoria,
                              String titulo,
                              String descricao,
                              @JsonDeserialize(using = CustomDateDeserializer.class)
                              Date dataPrazo,
                              Boolean finalizado
) {
}
