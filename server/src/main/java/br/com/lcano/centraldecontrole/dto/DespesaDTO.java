package br.com.lcano.centraldecontrole.dto;

import br.com.lcano.centraldecontrole.domain.Despesa;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class DespesaDTO {
    private Long id;

    private Long idCategoria;

    private String descricao;

    @JsonProperty("parcelas")
    private List<DespesaParcelaDTO> parcelasDTO;

    public static DespesaDTO converterParaDTO(Despesa despesa) {
        DespesaDTO dto = new DespesaDTO();
        dto.setId(despesa.getId());
        dto.setIdCategoria(despesa.getCategoria().getId());
        dto.setDescricao(despesa.getDescricao());
        dto.setParcelasDTO(DespesaParcelaDTO.converterListaParaDTO(despesa.getParcelas()));
        return dto;
    }
}
