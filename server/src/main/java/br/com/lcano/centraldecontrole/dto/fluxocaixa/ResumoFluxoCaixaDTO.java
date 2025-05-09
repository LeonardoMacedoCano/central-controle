package br.com.lcano.centraldecontrole.dto.fluxocaixa;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ResumoFluxoCaixaDTO {
    private BigDecimal valorRendaUltimoMes;
    private BigDecimal valorRendaAnoAtual;
    private BigDecimal valorDespesaUltimoMes;
    private BigDecimal valorDespesaAnoAtual;
    private BigDecimal valorAtivosUltimoMes;
    private BigDecimal valorAtivosAnoAtual;
    private BigDecimal percentualMetasUltimoMes;
    private BigDecimal percentualMetasAnoAtual;
    private List<String> labelsMensalAnoAtual;
    private List<BigDecimal> valoresRendaAnoAtual;
    private List<BigDecimal> valoresDespesaAnoAtual;
    private BigDecimal valorRendaPassivaUltimoMes;
}