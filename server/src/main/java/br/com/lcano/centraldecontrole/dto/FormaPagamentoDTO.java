package br.com.lcano.centraldecontrole.dto;

import br.com.lcano.centraldecontrole.domain.FormaPagamento;
import lombok.Data;

@Data
public class FormaPagamentoDTO {
    private Long id;
    private String descricao;

    public static FormaPagamentoDTO converterParaDTO(FormaPagamento formaPagamento) {
        FormaPagamentoDTO dto = new FormaPagamentoDTO();
        dto.setId(formaPagamento.getId());
        dto.setDescricao(formaPagamento.getDescricao());
        return dto;
    }
}
