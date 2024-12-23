package br.com.lcano.centraldecontrole.dto;

import br.com.lcano.centraldecontrole.domain.Lancamento;
import br.com.lcano.centraldecontrole.domain.Usuario;
import br.com.lcano.centraldecontrole.dto.fluxocaixa.AtivoDTO;
import br.com.lcano.centraldecontrole.dto.fluxocaixa.DespesaDTO;
import br.com.lcano.centraldecontrole.dto.fluxocaixa.ReceitaDTO;
import br.com.lcano.centraldecontrole.enums.TipoLancamentoEnum;
import br.com.lcano.centraldecontrole.util.CustomDateDeserializer;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.util.Date;

@Data
public class LancamentoDTO {
    private Long id;

    @JsonDeserialize(using = CustomDateDeserializer.class)
    private Date dataLancamento;

    private String descricao;

    private TipoLancamentoEnum tipo;

    private Usuario usuario;

    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY, property = "tipo")
    @JsonSubTypes({
            @JsonSubTypes.Type(value = DespesaDTO.class, name = "DESPESA"),
            @JsonSubTypes.Type(value = ReceitaDTO.class, name = "RECEITA"),
            @JsonSubTypes.Type(value = AtivoDTO.class, name = "ATIVO"),
    })
    private LancamentoItemDTO itemDTO;

    public static LancamentoDTO converterParaDTO(Lancamento lancamento, LancamentoItemDTO itemDTO) {
        LancamentoDTO dto = new LancamentoDTO();
        dto.setId(lancamento.getId());
        dto.setDataLancamento(lancamento.getDataLancamento());
        dto.setDescricao(lancamento.getDescricao());
        dto.setTipo(lancamento.getTipo());
        dto.setItemDTO(itemDTO);
        return dto;
    }

    public static LancamentoDTO converterParaDTO(Lancamento lancamento) {
        LancamentoDTO dto = new LancamentoDTO();
        dto.setId(lancamento.getId());
        dto.setDataLancamento(lancamento.getDataLancamento());
        dto.setDescricao(lancamento.getDescricao());
        dto.setTipo(lancamento.getTipo());
        return dto;
    }

    public Lancamento toEntity() {
        Lancamento lancamento = new Lancamento();
        lancamento.setId(this.id);
        lancamento.setDataLancamento(this.dataLancamento);
        lancamento.setDescricao(this.descricao);
        lancamento.setTipo(this.tipo);
        lancamento.setUsuario(this.usuario);
        return lancamento;
    }
}
