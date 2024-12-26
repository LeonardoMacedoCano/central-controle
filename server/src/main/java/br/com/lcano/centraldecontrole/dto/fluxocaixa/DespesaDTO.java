package br.com.lcano.centraldecontrole.dto.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.fluxocaixa.Despesa;
import br.com.lcano.centraldecontrole.dto.BaseDTO;
import br.com.lcano.centraldecontrole.dto.LancamentoItemDTO;
import br.com.lcano.centraldecontrole.enums.fluxocaixa.DespesaFormaPagamentoEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class DespesaDTO extends BaseDTO<Despesa> implements LancamentoItemDTO {
    private Long id;
    private DespesaCategoriaDTO categoria;
    private Date dataVencimento;
    private BigDecimal valor;
    private DespesaFormaPagamentoEnum formaPagamento;

    @Override
    public DespesaDTO fromEntity(Despesa entity) {
        this.id = entity.getId();
        this.dataVencimento = entity.getDataVencimento();
        this.valor = entity.getValor();
        this.formaPagamento = entity.getFormaPagamento();

        if (entity.getCategoria() != null) {
            this.categoria = new DespesaCategoriaDTO().fromEntity(entity.getCategoria());
        }

        return this;
    }

    @Override
    public Despesa toEntity() {
        Despesa entity = new Despesa();

        entity.setId(this.id);
        entity.setDataVencimento(this.dataVencimento);
        entity.setValor(this.valor);
        entity.setFormaPagamento(this.formaPagamento);

        if (this.categoria != null) {
            entity.setCategoria(this.categoria.toEntity());
        }

        return entity;
    }
}
