package br.com.lcano.centraldecontrole.dto.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.fluxocaixa.FluxoCaixaConfig;
import br.com.lcano.centraldecontrole.dto.CategoriaDTO;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class FluxoCaixaConfigDTO {
    private Long id;
    private CategoriaDTO despesaCategoriaPadrao;
    private BigDecimal metaLimiteDespesaMensal;
    private CategoriaDTO receitaCategoriaPadrao;
    private CategoriaDTO receitaCategoriaParaGanhoAtivo;
    private BigDecimal metaAporteMensal;
    private BigDecimal metaAporteTotal;
    private Long diaPadraoVencimentoFatura;

    public static FluxoCaixaConfigDTO converterParaDTO(FluxoCaixaConfig fluxoCaixaConfig) {
        FluxoCaixaConfigDTO dto = new FluxoCaixaConfigDTO();
        dto.setId(fluxoCaixaConfig.getId());
        dto.setMetaLimiteDespesaMensal(fluxoCaixaConfig.getMetaLimiteDespesaMensal());
        dto.setMetaAporteMensal(fluxoCaixaConfig.getMetaAporteMensal());
        dto.setMetaAporteTotal(fluxoCaixaConfig.getMetaAporteTotal());
        dto.setDiaPadraoVencimentoFatura(fluxoCaixaConfig.getDiaPadraoVencimentoFatura());

        if (fluxoCaixaConfig.getDespesaCategoriaPadrao() != null) dto.setDespesaCategoriaPadrao(CategoriaDTO.converterParaDTO(fluxoCaixaConfig.getDespesaCategoriaPadrao()));
        if (fluxoCaixaConfig.getReceitaCategoriaPadrao() != null) dto.setReceitaCategoriaPadrao(CategoriaDTO.converterParaDTO(fluxoCaixaConfig.getReceitaCategoriaPadrao()));
        if (fluxoCaixaConfig.getReceitaCategoriaParaGanhoAtivo() != null) dto.setReceitaCategoriaParaGanhoAtivo(CategoriaDTO.converterParaDTO(fluxoCaixaConfig.getReceitaCategoriaParaGanhoAtivo()));

        return dto;
    }
}
