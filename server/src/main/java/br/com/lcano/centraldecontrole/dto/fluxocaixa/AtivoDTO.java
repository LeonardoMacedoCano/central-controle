package br.com.lcano.centraldecontrole.dto.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.fluxocaixa.Ativo;
import br.com.lcano.centraldecontrole.dto.BaseDTO;
import br.com.lcano.centraldecontrole.dto.LancamentoItemDTO;
import br.com.lcano.centraldecontrole.enums.fluxocaixa.AtivoCategoria;
import br.com.lcano.centraldecontrole.enums.fluxocaixa.TipoOperacaoExtratoMovimentacaoB3;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class AtivoDTO extends BaseDTO<Ativo> implements LancamentoItemDTO {
    private Long id;
    private AtivoCategoria categoria;
    private TipoOperacaoExtratoMovimentacaoB3 operacao;
    private BigDecimal valor;
    private Date dataMovimento;

    @Override
    public AtivoDTO fromEntity(Ativo entity) {
        this.id = entity.getId();
        this.operacao = entity.getOperacao();
        this.valor = entity.getValor();
        this.dataMovimento = entity.getDataMovimento();
        this.categoria = entity.getCategoria();
        return this;
    }

    @Override
    public Ativo toEntity() {
        Ativo entity = new Ativo();
        entity.setId(this.id);
        entity.setOperacao(this.operacao);
        entity.setValor(this.valor);
        entity.setDataMovimento(this.dataMovimento);
        entity.setCategoria(this.categoria);
        return entity;
    }
}
