package br.com.lcano.centraldecontrole.dto.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.fluxocaixa.Receita;
import br.com.lcano.centraldecontrole.dto.BaseDTO;
import br.com.lcano.centraldecontrole.dto.LancamentoItemDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class ReceitaDTO extends BaseDTO<Receita> implements LancamentoItemDTO {
    private Long id;
    private ReceitaCategoriaDTO categoria;
    private BigDecimal valor;
    private Date dataRecebimento;

    @Override
    public ReceitaDTO fromEntity(Receita entity) {
        this.id = entity.getId();
        this.dataRecebimento = entity.getDataRecebimento();
        this.valor = entity.getValor();

        if (entity.getCategoria() != null) {
            this.categoria = (ReceitaCategoriaDTO) new ReceitaCategoriaDTO().fromEntity(entity.getCategoria());
        }

        return this;
    }

    @Override
    public Receita toEntity() {
        Receita entity = new Receita();

        entity.setId(this.id);
        entity.setDataRecebimento(this.dataRecebimento);
        entity.setValor(this.valor);

        if (this.categoria != null) {
            entity.setCategoria(this.categoria.toEntity());
        }

        return entity;
    }
}
