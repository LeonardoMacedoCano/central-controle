package br.com.lcano.centraldecontrole.dto.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.fluxocaixa.DespesaFormaPagamento;
import lombok.Data;

@Data
public class DespesaFormaPagamentoDTO {
    private Long id;
    private String descricao;

    public static DespesaFormaPagamentoDTO converterParaDTO(DespesaFormaPagamento formaPagamento) {
        DespesaFormaPagamentoDTO dto = new DespesaFormaPagamentoDTO();
        dto.setId(formaPagamento.getId());
        dto.setDescricao(formaPagamento.getDescricao());
        return dto;
    }
}
