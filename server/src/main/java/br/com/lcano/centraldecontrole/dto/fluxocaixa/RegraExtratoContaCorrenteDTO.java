package br.com.lcano.centraldecontrole.dto.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.fluxocaixa.RegraExtratoContaCorrente;
import br.com.lcano.centraldecontrole.dto.BaseDTO;
import br.com.lcano.centraldecontrole.enums.fluxocaixa.AtivoCategoria;
import br.com.lcano.centraldecontrole.enums.fluxocaixa.TipoRegraExtratoContaCorrente;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RegraExtratoContaCorrenteDTO extends BaseDTO<RegraExtratoContaCorrente> {
    private Long id;
    private TipoRegraExtratoContaCorrente tipoRegra;
    private String descricao;
    private String descricaoMatch;
    private String descricaoDestino;
    private DespesaCategoriaDTO despesaCategoriaDestino;
    private RendaCategoriaDTO rendaCategoriaDestino;
    private AtivoCategoria ativoCategoriaDestino;
    private Long prioridade;
    private boolean ativo;

    @Override
    public RegraExtratoContaCorrenteDTO fromEntity(RegraExtratoContaCorrente entity) {
        this.id = entity.getId();
        this.tipoRegra = entity.getTipoRegra();
        this.descricao = entity.getDescricao();
        this.descricaoMatch = entity.getDescricaoMatch();
        this.descricaoDestino = entity.getDescricaoDestino();
        this.despesaCategoriaDestino = entity.getDespesaCategoriaDestino() != null
                ? new DespesaCategoriaDTO().fromEntity(entity.getDespesaCategoriaDestino())
                : null;
        this.rendaCategoriaDestino = entity.getRendaCategoriaDestino() != null
                ? new RendaCategoriaDTO().fromEntity(entity.getRendaCategoriaDestino())
                : null;
        this.ativoCategoriaDestino = entity.getAtivoCategoriaDestino();
        this.prioridade = entity.getPrioridade();
        this.ativo = entity.isAtivo();
        return this;
    }

    @Override
    public RegraExtratoContaCorrente toEntity() {
        RegraExtratoContaCorrente entity = new RegraExtratoContaCorrente();
        entity.setId(this.id);
        entity.setTipoRegra(this.tipoRegra);
        entity.setDescricao(this.descricao);
        entity.setDescricaoMatch(this.descricaoMatch);
        entity.setDescricaoDestino(this.descricaoDestino);
        if (this.despesaCategoriaDestino != null) entity.setDespesaCategoriaDestino(this.despesaCategoriaDestino.toEntity());
        if (this.rendaCategoriaDestino != null) entity.setRendaCategoriaDestino(this.rendaCategoriaDestino.toEntity());
        entity.setAtivoCategoriaDestino(this.ativoCategoriaDestino);
        entity.setPrioridade(this.prioridade);
        entity.setAtivo(this.ativo);
        return entity;
    }
}
