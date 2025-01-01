package br.com.lcano.centraldecontrole.dto.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.fluxocaixa.RegraExtratoContaCorrente;
import br.com.lcano.centraldecontrole.dto.BaseDTO;
import br.com.lcano.centraldecontrole.enums.fluxocaixa.TipoRegraExtratoContaCorrente;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RegraExtratoContaCorrenteDTO extends BaseDTO<RegraExtratoContaCorrente> {
    private Long id;
    private String tipoRegra;
    private String descricaoMatch;
    private String descricaoDestino;
    private Long idCategoria;
    private Long prioridade;
    private boolean ativo;

    @Override
    public RegraExtratoContaCorrenteDTO fromEntity(RegraExtratoContaCorrente entity) {
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
    public RegraExtratoContaCorrente toEntity() {
        RegraExtratoContaCorrente entity = new RegraExtratoContaCorrente();
        entity.setId(this.id);
        entity.setTipoRegra(TipoRegraExtratoContaCorrente.valueOf(this.tipoRegra));
        entity.setDescricaoMatch(this.descricaoMatch);
        entity.setDescricaoDestino(this.descricaoDestino);
        entity.setIdCategoria(this.idCategoria);
        entity.setPrioridade(this.prioridade);
        entity.setAtivo(this.ativo);
        return null;
    }
}
