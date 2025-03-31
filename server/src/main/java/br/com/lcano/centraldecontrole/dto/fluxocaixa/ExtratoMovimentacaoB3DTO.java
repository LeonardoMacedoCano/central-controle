package br.com.lcano.centraldecontrole.dto.fluxocaixa;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
public class ExtratoMovimentacaoB3DTO {
    private String tipoOperacao;
    private Date dataMovimentacao;
    private String tipoMovimentacao;
    private String produto;
    private BigDecimal quantidade;
    private BigDecimal precoUnitario;
    private BigDecimal precoTotal;
}
