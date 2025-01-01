package br.com.lcano.centraldecontrole.dto.fluxocaixa;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ExtratoMensalCartaoDTO {
    private Date dataLancamento;
    private BigDecimal valor;
    private String descricao;
    private String categoria;
}