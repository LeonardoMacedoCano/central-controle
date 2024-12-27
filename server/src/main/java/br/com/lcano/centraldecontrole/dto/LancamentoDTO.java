package br.com.lcano.centraldecontrole.dto;

import br.com.lcano.centraldecontrole.domain.Lancamento;
import br.com.lcano.centraldecontrole.dto.fluxocaixa.AtivoDTO;
import br.com.lcano.centraldecontrole.dto.fluxocaixa.DespesaDTO;
import br.com.lcano.centraldecontrole.dto.fluxocaixa.ReceitaDTO;
import br.com.lcano.centraldecontrole.enums.TipoLancamentoEnum;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class LancamentoDTO extends BaseDTO<Lancamento> {
    private Long id;
    private Date dataLancamento;
    private String descricao;
    private TipoLancamentoEnum tipo;

    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY, property = "tipo")
    @JsonSubTypes({
            @JsonSubTypes.Type(value = DespesaDTO.class, name = "DESPESA"),
            @JsonSubTypes.Type(value = ReceitaDTO.class, name = "RECEITA"),
            @JsonSubTypes.Type(value = AtivoDTO.class, name = "ATIVO"),
    })
    private LancamentoItemDTO itemDTO;

    public LancamentoDTO fromEntityWithItem(Lancamento entity, LancamentoItemDTO itemDTO) {
        this.fromEntity(entity);
        this.itemDTO = itemDTO;
        return this;
    }

    @Override
    public LancamentoDTO fromEntity(Lancamento entity) {
        this.id = entity.getId();
        this.dataLancamento = entity.getDataLancamento();
        this.descricao = entity.getDescricao();
        this.tipo = entity.getTipo();
        return this;
    }

    @Override
    public Lancamento toEntity() {
        Lancamento entity = new Lancamento();
        entity.setId(this.id);
        entity.setDataLancamento(this.dataLancamento);
        entity.setDescricao(this.descricao);
        entity.setTipo(this.tipo);
        return entity;
    }
}
