package br.com.lcano.centraldecontrole.dto.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.Lancamento;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.Despesa;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.DespesaCategoria;
import br.com.lcano.centraldecontrole.dto.CategoriaDTO;
import br.com.lcano.centraldecontrole.dto.LancamentoItemDTO;
import br.com.lcano.centraldecontrole.enums.fluxocaixa.DespesaFormaPagamentoEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class DespesaDTO implements LancamentoItemDTO {
    private Long id;
    private CategoriaDTO categoria;
    private Date dataVencimento;
    private BigDecimal valor;
    private DespesaFormaPagamentoEnum formaPagamento;

    public static DespesaDTO converterParaDTO(Despesa despesa) {
        DespesaDTO dto = new DespesaDTO();

        dto.setId(despesa.getId());
        dto.setCategoria(CategoriaDTO.converterParaDTO(despesa.getCategoria()));
        dto.setDataVencimento(despesa.getDataVencimento());
        dto.setValor(despesa.getValor());
        if (despesa.getFormaPagamento() != null) dto.setFormaPagamento(despesa.getFormaPagamento());

        return dto;
    }

    public Despesa toEntity(Long id,
                            Lancamento lancamento,
                            DespesaCategoria despesaCategoria,
                            Date dataVencimento,
                            BigDecimal valor,
                            DespesaFormaPagamentoEnum formaPagamento) {
        Despesa despesa = new Despesa();

        despesa.setId(id);
        despesa.setLancamento(lancamento);
        despesa.setCategoria(despesaCategoria);
        despesa.setDataVencimento(dataVencimento);
        despesa.setValor(valor);
        despesa.setFormaPagamento(formaPagamento);

        return despesa;
    }
}
