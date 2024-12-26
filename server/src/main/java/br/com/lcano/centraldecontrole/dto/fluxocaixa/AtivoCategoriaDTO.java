package br.com.lcano.centraldecontrole.dto.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.fluxocaixa.AtivoCategoria;
import br.com.lcano.centraldecontrole.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AtivoCategoriaDTO extends BaseDTO<AtivoCategoria> {
    private Long id;
    private String descricao;

    @Override
    public AtivoCategoriaDTO fromEntity(AtivoCategoria entity) {
        this.id = entity.getId();
        this.descricao = entity.getDescricao();
        return this;
    }

    @Override
    public AtivoCategoria toEntity() {
        AtivoCategoria entity = new AtivoCategoria();
        entity.setId(this.id);
        entity.setDescricao(this.descricao);
        return entity;
    }
}
