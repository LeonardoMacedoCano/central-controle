package br.com.lcano.centraldecontrole.dto.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.fluxocaixa.ReceitaCategoria;
import br.com.lcano.centraldecontrole.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ReceitaCategoriaDTO extends BaseDTO<ReceitaCategoria> {

    private Long id;
    private String descricao;

    @Override
    public BaseDTO<ReceitaCategoria> fromEntity(ReceitaCategoria entity) {
        this.id = entity.getId();
        this.descricao = entity.getDescricao();
        return this;
    }

    @Override
    public ReceitaCategoria toEntity() {
        ReceitaCategoria entity = new ReceitaCategoria();
        entity.setId(this.id);
        entity.setDescricao(this.descricao);
        return entity;
    }
}
