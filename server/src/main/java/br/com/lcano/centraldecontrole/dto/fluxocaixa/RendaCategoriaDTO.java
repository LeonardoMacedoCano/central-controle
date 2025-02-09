package br.com.lcano.centraldecontrole.dto.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.fluxocaixa.RendaCategoria;
import br.com.lcano.centraldecontrole.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RendaCategoriaDTO extends BaseDTO<RendaCategoria> {

    private Long id;
    private String descricao;

    @Override
    public RendaCategoriaDTO fromEntity(RendaCategoria entity) {
        this.id = entity.getId();
        this.descricao = entity.getDescricao();
        return this;
    }

    @Override
    public RendaCategoria toEntity() {
        RendaCategoria entity = new RendaCategoria();
        entity.setId(this.id);
        entity.setDescricao(this.descricao);
        return entity;
    }
}
