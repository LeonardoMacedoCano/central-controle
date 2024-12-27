package br.com.lcano.centraldecontrole.dto.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.fluxocaixa.ExtratoContaRegra;
import br.com.lcano.centraldecontrole.dto.BaseDTO;
import br.com.lcano.centraldecontrole.enums.fluxocaixa.TipoRegraExtratoConta;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ExtratoContaRegraDTO extends BaseDTO<ExtratoContaRegra> {
    private Long id;
    private String tipoRegra;
    private String descricaoMatch;
    private String descricaoDestino;
    private Long idCategoria;
    private Long prioridade;
    private boolean ativo;

    @Override
    public ExtratoContaRegraDTO fromEntity(ExtratoContaRegra entity) {
        this.id = entity.getId();
        this.tipoRegra = entity.getTipoRegra().getDescricao();
        this.descricaoMatch = entity.getDescricaoMatch();
        this.descricaoDestino = entity.getDescricaoDestino();
        this.idCategoria = entity.getIdCategoria();
        this.prioridade = entity.getPrioridade();
        this.ativo = entity.isAtivo();
        return this;
    }

    @Override
    public ExtratoContaRegra toEntity() {
        ExtratoContaRegra entity = new ExtratoContaRegra();
        entity.setId(this.id);
        entity.setTipoRegra(TipoRegraExtratoConta.valueOf(this.tipoRegra));
        entity.setDescricaoMatch(this.descricaoMatch);
        entity.setDescricaoDestino(this.descricaoDestino);
        entity.setIdCategoria(this.idCategoria);
        entity.setPrioridade(this.prioridade);
        entity.setAtivo(this.ativo);
        return null;
    }
}
