package br.com.lcano.centraldecontrole.dto.fluxocaixa;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ResumoFluxoCaixaDTO {
    private BigDecimal valorRendaMesAtual;
    private BigDecimal valorRendaAnoAtual;
    private BigDecimal valorDespesaMesAtual;
    private BigDecimal valorDespesaAnoAtual;
    private BigDecimal valorAtivosMesAtual;
    private BigDecimal valorAtivosAnoAtual;
    private BigDecimal percentualMetasMesAtual;
    private BigDecimal percentualMetasAnoAtual;
    private List<String> labelsMensalAnoAtual;
    private List<BigDecimal> valoresRendaAnoAtual;
    private List<BigDecimal> valoresDespesaAnoAtual;
    private BigDecimal valorRendaPassivaMesAtual;
}
