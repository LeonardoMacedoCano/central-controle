package br.com.lcano.centraldecontrole.dto.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.fluxocaixa.FluxoCaixaParametro;
import br.com.lcano.centraldecontrole.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
public class FluxoCaixaParametroDTO extends BaseDTO<FluxoCaixaParametro> {
    private Long id;
    private DespesaCategoriaDTO despesaCategoriaPadrao;
    private BigDecimal metaLimiteDespesaMensal;
    private RendaCategoriaDTO rendaCategoriaPadrao;
    private RendaCategoriaDTO rendaPassivaCategoria;
    private BigDecimal metaAporteMensal;
    private BigDecimal metaAporteTotal;
    private Long diaPadraoVencimentoCartao;

    @Override
    public FluxoCaixaParametroDTO fromEntity(FluxoCaixaParametro entity) {
        if (null == entity) return null;

        this.id = entity.getId();
        this.metaLimiteDespesaMensal = entity.getMetaLimiteDespesaMensal();
        this.metaAporteMensal = entity.getMetaAporteMensal();
        this.metaAporteTotal = entity.getMetaAporteTotal();
        this.diaPadraoVencimentoCartao = entity.getDiaPadraoVencimentoCartao();

        if (entity.getDespesaCategoriaPadrao() != null) {
            this.despesaCategoriaPadrao = new DespesaCategoriaDTO().fromEntity(
                    entity.getDespesaCategoriaPadrao()
            );
        }

        if (entity.getRendaCategoriaPadrao() != null) {
            this.rendaCategoriaPadrao = new RendaCategoriaDTO().fromEntity(
                    entity.getRendaCategoriaPadrao()
            );
        }

        if (entity.getRendaPassivaCategoria() != null) {
            this.rendaPassivaCategoria = new RendaCategoriaDTO().fromEntity(
                    entity.getRendaPassivaCategoria()
            );
        }

        return this;
    }

    @Override
    public FluxoCaixaParametro toEntity() {
        FluxoCaixaParametro entity = new FluxoCaixaParametro();

        entity.setId(this.id);
        entity.setMetaLimiteDespesaMensal(this.metaLimiteDespesaMensal);
        entity.setMetaAporteMensal(this.metaAporteMensal);
        entity.setMetaAporteTotal(this.metaAporteTotal);
        entity.setDiaPadraoVencimentoCartao(this.diaPadraoVencimentoCartao);

        if (this.despesaCategoriaPadrao != null) {
            entity.setDespesaCategoriaPadrao(this.despesaCategoriaPadrao.toEntity());
        }

        if (this.rendaCategoriaPadrao != null) {
            entity.setRendaCategoriaPadrao(this.rendaCategoriaPadrao.toEntity());
        }

        if (this.rendaPassivaCategoria != null) {
            entity.setRendaPassivaCategoria(this.rendaPassivaCategoria.toEntity());
        }

        return entity;
    }
}
