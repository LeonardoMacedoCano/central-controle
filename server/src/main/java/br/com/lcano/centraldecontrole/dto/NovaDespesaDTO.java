package br.com.lcano.centraldecontrole.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class NovaDespesaDTO {
    private Long idCategoria;

    private String descricao;

    @JsonProperty("parcelas")
    private List<NovaDespesaParcelaDTO> parcelasDTO;
}
