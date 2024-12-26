package br.com.lcano.centraldecontrole.dto.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.fluxocaixa.FluxoCaixaConfig;
import br.com.lcano.centraldecontrole.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
public class FluxoCaixaConfigDTO extends BaseDTO<FluxoCaixaConfig> {
    private Long id;
    private DespesaCategoriaDTO despesaCategoriaPadrao;
    private BigDecimal metaLimiteDespesaMensal;
    private ReceitaCategoriaDTO receitaCategoriaPadrao;
    private ReceitaCategoriaDTO receitaCategoriaParaGanhoAtivo;
    private BigDecimal metaAporteMensal;
    private BigDecimal metaAporteTotal;
    private Long diaPadraoVencimentoFatura;

    @Override
    public FluxoCaixaConfigDTO fromEntity(FluxoCaixaConfig entity) {
        this.id = entity.getId();
        this.metaLimiteDespesaMensal = entity.getMetaLimiteDespesaMensal();
        this.metaAporteMensal = entity.getMetaAporteMensal();
        this.metaAporteTotal = entity.getMetaAporteTotal();
        this.diaPadraoVencimentoFatura = entity.getDiaPadraoVencimentoFatura();

        if (entity.getDespesaCategoriaPadrao() != null) {
            this.despesaCategoriaPadrao = (DespesaCategoriaDTO) new DespesaCategoriaDTO().fromEntity(
                    entity.getDespesaCategoriaPadrao()
            );
        }

        if (entity.getReceitaCategoriaPadrao() != null) {
            this.receitaCategoriaPadrao = (ReceitaCategoriaDTO) new ReceitaCategoriaDTO().fromEntity(
                    entity.getReceitaCategoriaPadrao()
            );
        }

        if (entity.getReceitaCategoriaParaGanhoAtivo() != null) {
            this.receitaCategoriaPadrao = (ReceitaCategoriaDTO) new ReceitaCategoriaDTO().fromEntity(
                    entity.getReceitaCategoriaParaGanhoAtivo()
            );
        }

        return this;
    }

    @Override
    public FluxoCaixaConfig toEntity() {
        FluxoCaixaConfig entity = new FluxoCaixaConfig();

        entity.setId(this.id);
        entity.setMetaLimiteDespesaMensal(this.metaLimiteDespesaMensal);
        entity.setMetaAporteMensal(this.metaAporteMensal);
        entity.setMetaAporteTotal(this.metaAporteTotal);
        entity.setDiaPadraoVencimentoFatura(this.diaPadraoVencimentoFatura);

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
