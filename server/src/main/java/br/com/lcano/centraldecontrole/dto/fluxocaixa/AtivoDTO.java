package br.com.lcano.centraldecontrole.dto.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.fluxocaixa.Ativo;
import br.com.lcano.centraldecontrole.dto.BaseDTO;
import br.com.lcano.centraldecontrole.dto.LancamentoItemDTO;
import br.com.lcano.centraldecontrole.enums.fluxocaixa.AtivoOperacaoEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class AtivoDTO extends BaseDTO<Ativo> implements LancamentoItemDTO {
    private Long id;
    private AtivoCategoriaDTO categoria;
    private String ticker;
    private AtivoOperacaoEnum operacao;
    private BigDecimal quantidade;
    private BigDecimal precoUnitario;
    private Date dataMovimento;

    @Override
    public AtivoDTO fromEntity(Ativo entity) {
        this.id = entity.getId();
        this.ticker = entity.getTicker();
        this.operacao = entity.getOperacao();
        this.quantidade = entity.getQuantidade();
        this.precoUnitario = entity.getPrecoUnitario();
        this.dataMovimento = entity.getDataMovimento();

        if (entity.getCategoria() != null) {
            this.categoria = new AtivoCategoriaDTO().fromEntity(entity.getCategoria());
        }

        return this;
    }

    @Override
    public Ativo toEntity() {
        Ativo entity = new Ativo();

        entity.setId(this.id);
        entity.setTicker(this.ticker);
        entity.setOperacao(this.operacao);
        entity.setQuantidade(this.quantidade);
        entity.setPrecoUnitario(this.precoUnitario);
        entity.setDataMovimento(this.dataMovimento);

        if (this.categoria != null) {
            entity.setCategoria(this.categoria.toEntity());
        }

        return entity;
    }
}