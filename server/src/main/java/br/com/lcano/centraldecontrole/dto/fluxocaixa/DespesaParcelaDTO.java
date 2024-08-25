package br.com.lcano.centraldecontrole.dto.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.fluxocaixa.DespesaParcela;
import br.com.lcano.centraldecontrole.util.CustomDateDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class DespesaParcelaDTO{
    private Long id;

    private Integer numero;

    @JsonDeserialize(using = CustomDateDeserializer.class)
    private Date dataVencimento;

    private Double valor;

    private Boolean pago;

    private DespesaFormaPagamentoDTO formaPagamento;

    public static DespesaParcelaDTO converterParaDTO(DespesaParcela despesaParcela) {
        DespesaParcelaDTO dto = new DespesaParcelaDTO();
        dto.setId(despesaParcela.getId());
        dto.setNumero(despesaParcela.getNumero());
        dto.setDataVencimento(despesaParcela.getDataVencimento());
        dto.setValor(despesaParcela.getValor());
        dto.setPago(despesaParcela.isPago());

        if (despesaParcela.getFormaPagamento() != null) {
            dto.setFormaPagamento(DespesaFormaPagamentoDTO.converterParaDTO(despesaParcela.getFormaPagamento()));
        }
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
