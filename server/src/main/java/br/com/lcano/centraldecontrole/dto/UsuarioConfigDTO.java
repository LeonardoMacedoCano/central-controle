package br.com.lcano.centraldecontrole.dto;

import br.com.lcano.centraldecontrole.domain.FormaPagamento;
import br.com.lcano.centraldecontrole.domain.UsuarioConfig;
import lombok.Data;

@Data
public class UsuarioConfigDTO {
    private Long id;

    private int despesaNumeroMaxItemPagina;

    private double despesaValorMetaMensal;

    private int despesaDiaPadraoVencimento;

    private FormaPagamento despesaFormaPagamentoPadrao;

    public static UsuarioConfigDTO converterParaDTO(UsuarioConfig usuarioConfig) {
        UsuarioConfigDTO dto = new UsuarioConfigDTO();

        dto.setId(usuarioConfig.getId());
        dto.setDespesaNumeroMaxItemPagina(usuarioConfig.getDespesaNumeroMaxItemPagina());
        dto.setDespesaValorMetaMensal(usuarioConfig.getDespesaValorMetaMensal());
        dto.setDespesaDiaPadraoVencimento(usuarioConfig.getDespesaDiaPadraoVencimento());
        dto.setDespesaFormaPagamentoPadrao(usuarioConfig.getDespesaFormaPagamentoPadrao());

        return dto;
    }
}
