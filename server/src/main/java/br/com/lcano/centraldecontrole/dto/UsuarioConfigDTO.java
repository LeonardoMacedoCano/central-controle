package br.com.lcano.centraldecontrole.dto;

import br.com.lcano.centraldecontrole.domain.UsuarioConfig;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UsuarioConfigDTO {
    private Long id;

    private int despesaNumeroMaxItemPagina;

    private BigDecimal despesaValorMetaMensal;

    private int despesaDiaPadraoVencimento;


    public static UsuarioConfigDTO converterParaDTO(UsuarioConfig usuarioConfig) {
        UsuarioConfigDTO dto = new UsuarioConfigDTO();

        dto.setId(usuarioConfig.getId());
        dto.setDespesaValorMetaMensal(usuarioConfig.getDespesaValorMetaMensal());

        return dto;
    }
}
