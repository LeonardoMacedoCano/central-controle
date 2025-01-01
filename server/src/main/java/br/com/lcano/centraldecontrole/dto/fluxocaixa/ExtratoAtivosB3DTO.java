package br.com.lcano.centraldecontrole.dto.fluxocaixa;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ExtratoAtivosB3DTO {
    private Date dataNegocio;
    private String tipoMovimentacao;
    private String codigoNegociacao;
    private BigDecimal quantidade;
    private BigDecimal preco;
}
