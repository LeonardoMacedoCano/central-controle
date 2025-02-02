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
    private ReceitaCategoriaDTO receitaCategoriaPadrao;
    private ReceitaCategoriaDTO receitaCategoriaParaGanhoAtivo;
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

        if (entity.getReceitaCategoriaPadrao() != null) {
            this.receitaCategoriaPadrao = new ReceitaCategoriaDTO().fromEntity(
                    entity.getReceitaCategoriaPadrao()
            );
        }

        if (entity.getReceitaCategoriaParaGanhoAtivo() != null) {
            this.receitaCategoriaParaGanhoAtivo = new ReceitaCategoriaDTO().fromEntity(
                    entity.getReceitaCategoriaParaGanhoAtivo()
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

        if (this.receitaCategoriaPadrao != null) {
            entity.setReceitaCategoriaPadrao(this.receitaCategoriaPadrao.toEntity());
        }

        if (this.receitaCategoriaParaGanhoAtivo != null) {
            entity.setReceitaCategoriaParaGanhoAtivo(this.receitaCategoriaParaGanhoAtivo.toEntity());
        }

        return entity;
    }
}
