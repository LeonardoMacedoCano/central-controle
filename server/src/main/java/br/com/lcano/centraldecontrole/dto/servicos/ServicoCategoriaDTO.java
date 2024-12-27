package br.com.lcano.centraldecontrole.dto.servicos;

import br.com.lcano.centraldecontrole.domain.servicos.ServicoCategoria;
import br.com.lcano.centraldecontrole.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ServicoCategoriaDTO extends BaseDTO<ServicoCategoria> {
    private Long id;
    private String descricao;
    private String icone;

    @Override
    public ServicoCategoriaDTO fromEntity(ServicoCategoria entity) {
        this.id = entity.getId();
        this.descricao = entity.getDescricao();
        this.icone = entity.getIcone();
        return this;
    }

    @Override
    public ServicoCategoria toEntity() {
        ServicoCategoria entity = new ServicoCategoria();
        entity.setId(this.id);
        entity.setDescricao(this.descricao);
        entity.setIcone(this.icone);
        return entity;
    }
}
