package br.com.lcano.centraldecontrole.dto.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.fluxocaixa.Ativo;
import br.com.lcano.centraldecontrole.dto.CategoriaDTO;
import br.com.lcano.centraldecontrole.dto.LancamentoItemDTO;
import br.com.lcano.centraldecontrole.enums.fluxocaixa.AtivoOperacaoEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class AtivoDTO implements LancamentoItemDTO {
    private Long id;
    private CategoriaDTO categoria;
    private String ticker;
    private AtivoOperacaoEnum operacao;
    private BigDecimal quantidade;
    private BigDecimal precoUnitario;
    private Date dataMovimento;

    public static AtivoDTO converterParaDTO(Ativo ativo) {
        AtivoDTO dto = new AtivoDTO();
        dto.setId(ativo.getId());
        dto.setCategoria(CategoriaDTO.converterParaDTO(ativo.getCategoria()));
        dto.setTicker(ativo.getTicker());
        dto.setOperacao(ativo.getOperacao());
        dto.setQuantidade(ativo.getQuantidade());
        dto.setPrecoUnitario(ativo.getPrecoUnitario());
        dto.setDataMovimento(ativo.getDataMovimento());
        return dto;
    }
}
