package br.com.lcano.centraldecontrole.dto;

import br.com.lcano.centraldecontrole.domain.DespesaParcela;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class DespesaParcelaDTO{
    private Long id;

    private Integer numero;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dataVencimento;

    private Double valor;

    private Boolean pago;

    public static DespesaParcelaDTO converterParaDTO(DespesaParcela despesaParcela) {
        DespesaParcelaDTO dto = new DespesaParcelaDTO();
        dto.setId(despesaParcela.getId());
        dto.setNumero(despesaParcela.getNumero());
        dto.setDataVencimento(despesaParcela.getDataVencimento());
        dto.setValor(despesaParcela.getValor());
        dto.setPago(despesaParcela.isPago());
        return dto;
    }

    public static List<DespesaParcelaDTO> converterListaParaDTO(List<DespesaParcela> despesaParcelas) {
        List<DespesaParcelaDTO> dto = new ArrayList<>();
        for (DespesaParcela despesaParcela : despesaParcelas) {
            dto.add(converterParaDTO(despesaParcela));
        }
        return dto;
    }
}
