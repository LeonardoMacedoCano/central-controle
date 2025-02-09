package br.com.lcano.centraldecontrole.dto.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.fluxocaixa.Renda;
import br.com.lcano.centraldecontrole.dto.BaseDTO;
import br.com.lcano.centraldecontrole.dto.LancamentoItemDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class RendaDTO extends BaseDTO<Renda> implements LancamentoItemDTO {
    private Long id;
    private RendaCategoriaDTO categoria;
    private BigDecimal valor;
    private Date dataRecebimento;

    @Override
    public RendaDTO fromEntity(Renda entity) {
        this.id = entity.getId();
        this.dataRecebimento = entity.getDataRecebimento();
        this.valor = entity.getValor();
        if (entity.getCategoria() != null) this.categoria = new RendaCategoriaDTO().fromEntity(entity.getCategoria());
        return this;
    }

    @Override
    public Renda toEntity() {
        Renda entity = new Renda();
        entity.setId(this.id);
        entity.setDataRecebimento(this.dataRecebimento);
        entity.setValor(this.valor);
        if (this.categoria != null) entity.setCategoria(this.categoria.toEntity());
        return entity;
    }
}
