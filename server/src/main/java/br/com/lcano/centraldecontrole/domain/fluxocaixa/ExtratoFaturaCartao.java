package br.com.lcano.centraldecontrole.domain.fluxocaixa;

import lombok.Data;

import java.util.Date;

@Data
public class ExtratoFaturaCartao {
    private Date dataLancamento;
    private Double valor;
    private String descricao;
    private String categoria;
}