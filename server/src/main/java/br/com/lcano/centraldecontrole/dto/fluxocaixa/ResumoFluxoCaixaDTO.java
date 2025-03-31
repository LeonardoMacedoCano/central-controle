package br.com.lcano.centraldecontrole.dto.fluxocaixa;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ResumoFluxoCaixaDTO {
    private BigDecimal valorRendaMesAnterior;
    private BigDecimal valorRendaAnoAtual;
    private BigDecimal valorDespesaMesAnterior;
    private BigDecimal valorDespesaAnoAtual;
    private BigDecimal valorAtivosMesAnterior;
    private BigDecimal valorAtivosAnoAtual;
    private BigDecimal percentualMetasMesAnterior;
    private BigDecimal percentualMetasAnoAtual;
    private List<String> labelsMensalAnoAtual;
    private List<BigDecimal> valoresRendaAnoAtual;
    private List<BigDecimal> valoresDespesaAnoAtual;
    private BigDecimal valorRendaPassivaMesAnterior;
}
