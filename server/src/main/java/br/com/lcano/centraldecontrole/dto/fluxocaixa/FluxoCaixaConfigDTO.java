package br.com.lcano.centraldecontrole.dto.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.fluxocaixa.FluxoCaixaConfig;
import br.com.lcano.centraldecontrole.dto.CategoriaDTO;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class FluxoCaixaConfigDTO {
    private Long id;
    private BigDecimal valorMetaSaldoMensal;
    private CategoriaDTO despesaCategoriaPadrao;
    private CategoriaDTO receitaCategoriaPadrao;
    private Long diaPadraoVencimentoFatura;

    public static FluxoCaixaConfigDTO converterParaDTO(FluxoCaixaConfig fluxoCaixaConfig) {
        FluxoCaixaConfigDTO dto = new FluxoCaixaConfigDTO();
        dto.setId(fluxoCaixaConfig.getId());
        dto.setDiaPadraoVencimentoFatura(fluxoCaixaConfig.getDiaPadraoVencimentoFatura());
        dto.setValorMetaSaldoMensal(fluxoCaixaConfig.getValorMetaSaldoMensal());

        if (fluxoCaixaConfig.getDespesaCategoriaPadrao() != null) dto.setDespesaCategoriaPadrao(CategoriaDTO.converterParaDTO(fluxoCaixaConfig.getDespesaCategoriaPadrao()));
        if (fluxoCaixaConfig.getReceitaCategoriaPadrao() != null) dto.setReceitaCategoriaPadrao(CategoriaDTO.converterParaDTO(fluxoCaixaConfig.getReceitaCategoriaPadrao()));

        return dto;
    }
}
