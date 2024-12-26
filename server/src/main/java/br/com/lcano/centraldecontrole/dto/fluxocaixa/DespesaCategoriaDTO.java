package br.com.lcano.centraldecontrole.dto.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.fluxocaixa.DespesaCategoria;
import br.com.lcano.centraldecontrole.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class DespesaCategoriaDTO extends BaseDTO<DespesaCategoria> {
    private Long id;
    private String descricao;

    @Override
    public DespesaCategoriaDTO fromEntity(DespesaCategoria entity) {
        this.id = entity.getId();
        this.descricao = entity.getDescricao();
        return this;
    }

    @Override
    public DespesaCategoria toEntity() {
        DespesaCategoria entity = new DespesaCategoria();
        entity.setId(this.id);
        entity.setDescricao(this.descricao);
        return entity;
    }
}
