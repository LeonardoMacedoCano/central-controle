package br.com.lcano.centraldecontrole.service.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.fluxocaixa.FluxoCaixaParametro;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.RendaCategoria;
import br.com.lcano.centraldecontrole.dto.fluxocaixa.ResumoFluxoCaixaDTO;
import br.com.lcano.centraldecontrole.enums.TipoLancamentoEnum;
import br.com.lcano.centraldecontrole.service.LancamentoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Service
public class FluxoCaixaResumoService {
    private final LancamentoService lancamentoService;
    private final FluxoCaixaParametroService parametroService;

    public ResumoFluxoCaixaDTO getResumoFluxoCaixa() {
        ResumoFluxoCaixaDTO dto = new ResumoFluxoCaixaDTO();
        YearMonth mesAtual = YearMonth.now();
        Year anoAtual = Year.now();
        FluxoCaixaParametro parametros = parametroService.findByUsuario();

        calculateAndSetValoresMensais(dto, mesAtual);
        calculateAndSetValoresAnuais(dto, anoAtual);

        dto.setPercentualMetasMesAtual(calculatePercentualMetasMensais(dto, parametros));
        dto.setPercentualMetasAnoAtual(calculatePercentualMetasAnuais(dto, parametros));

        calculateAndSetRendasEDespesasAnuais(dto, anoAtual);
        dto.setValorRendaPassivaMesAtual(calculateRendaPassivaPeriodo(mesAtual, parametros.getRendaPassivaCategoria()));
        return dto;
    }


    private void calculateAndSetValoresMensais(ResumoFluxoCaixaDTO dto, YearMonth mesAtual) {
        dto.setValorRendaMesAtual(calculateRendasPeriodo(mesAtual));
        dto.setValorDespesaMesAtual(calculateDespesasPeriodo(mesAtual));
        dto.setValorAtivosMesAtual(calculateAtivosPeriodo(mesAtual));
    }

    private void calculateAndSetValoresAnuais(ResumoFluxoCaixaDTO dto, Year anoAtual) {
        dto.setValorRendaAnoAtual(calculateRendasPeriodo(anoAtual));
        dto.setValorDespesaAnoAtual(calculateDespesasPeriodo(anoAtual));
        dto.setValorAtivosAnoAtual(calculateAtivosPeriodo(anoAtual));
    }

    private void calculateAndSetRendasEDespesasAnuais(ResumoFluxoCaixaDTO dto, Year anoAtual) {
        List<String> labels = new ArrayList<>();
        List<BigDecimal> valoresRenda = new ArrayList<>();
        List<BigDecimal> valoresDespesa = new ArrayList<>();

        for (int mes = 1; mes <= 12; mes++) {
            AddAndSetRendasEDespesasMensais(labels, valoresRenda, valoresDespesa, anoAtual, mes);
        }

        dto.setLabelsMensalAnoAtual(labels);
        if (!valoresRenda.isEmpty()) dto.setValoresRendaAnoAtual(valoresRenda);
        if (!valoresDespesa.isEmpty()) dto.setValoresDespesaAnoAtual(valoresDespesa);
    }

    private void AddAndSetRendasEDespesasMensais(List<String> labels, List<BigDecimal> valoresRenda, List<BigDecimal> valoresDespesa, Year anoAtual, int mes) {
        YearMonth yearMonth = YearMonth.of(anoAtual.getValue(), mes);
        BigDecimal renda = calculateRendasPeriodo(yearMonth);
        BigDecimal despesa = calculateDespesasPeriodo(yearMonth);

        if (renda.compareTo(BigDecimal.ZERO) > 0 || despesa.compareTo(BigDecimal.ZERO) > 0) {
            SimpleDateFormat monthFormat = new SimpleDateFormat("MMM");
            labels.add(String.format("%s/%d", monthFormat.format(Date.from(yearMonth.atDay(1).atStartOfDay(ZoneId.systemDefault()).toInstant())), anoAtual.getValue() % 100));
            valoresRenda.add(renda);
            valoresDespesa.add(despesa);
        }
    }

    private BigDecimal calculateRendasPeriodo(YearMonth periodo) {
        return calculateValorPeriodo(TipoLancamentoEnum.RENDA, periodo);
    }

    private BigDecimal calculateDespesasPeriodo(YearMonth periodo) {
        return calculateValorPeriodo(TipoLancamentoEnum.DESPESA, periodo);
    }

    private BigDecimal calculateAtivosPeriodo(YearMonth periodo) {
        return calculateValorPeriodo(TipoLancamentoEnum.ATIVO, periodo);
    }

    private BigDecimal calculateRendasPeriodo(Year ano) {
        return calculateValorPeriodo(TipoLancamentoEnum.RENDA, ano);
    }

    private BigDecimal calculateDespesasPeriodo(Year ano) {
        return calculateValorPeriodo(TipoLancamentoEnum.DESPESA, ano);
    }

    private BigDecimal calculateAtivosPeriodo(Year ano) {
        return calculateValorPeriodo(TipoLancamentoEnum.ATIVO, ano);
    }

    private BigDecimal calculateValorPeriodo(TipoLancamentoEnum tipo, YearMonth periodo) {
        Date inicio = Date.from(periodo.atDay(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date fim = Date.from(periodo.atEndOfMonth().atStartOfDay(ZoneId.systemDefault()).toInstant());
        return calculateValor(tipo, inicio, fim);
    }

    private BigDecimal calculateValorPeriodo(TipoLancamentoEnum tipo, Year ano) {
        Date inicio = Date.from(ano.atDay(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date fim = Date.from(ano.atMonth(12).atEndOfMonth().atStartOfDay(ZoneId.systemDefault()).toInstant());
        return calculateValor(tipo, inicio, fim);
    }

    private BigDecimal calculateValor(TipoLancamentoEnum tipo, Date inicio, Date fim) {
        return lancamentoService.findByUsuarioAutenticadoAndTipoAndDateRange(tipo, inicio, fim)
                .stream()
                .map(lancamento -> switch (lancamento.getTipo()) {
                    case RENDA -> lancamento.getRenda().getValor();
                    case DESPESA -> lancamento.getDespesa().getValor();
                    case ATIVO -> lancamento.getAtivo().getPrecoUnitario().multiply(lancamento.getAtivo().getQuantidade());
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateRendaPassivaPeriodo(YearMonth periodo, RendaCategoria categoriaRendaPassiva) {
        if (categoriaRendaPassiva == null) {
            return BigDecimal.ZERO;
        }
        Date inicio = Date.from(periodo.atDay(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date fim = Date.from(periodo.atEndOfMonth().atStartOfDay(ZoneId.systemDefault()).toInstant());
        return lancamentoService.findByUsuarioAutenticadoAndTipoAndDateRange(TipoLancamentoEnum.RENDA, inicio, fim)
                .stream()
                .filter(l -> l.getRenda().getCategoria().equals(categoriaRendaPassiva))
                .map(l -> l.getRenda().getValor())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculatePercentualMetasMensais(ResumoFluxoCaixaDTO dto, FluxoCaixaParametro parametros) {
        BigDecimal percentualLimiteDespesa = calculatePercentualLimiteDespesa(dto.getValorDespesaMesAtual(), parametros.getMetaLimiteDespesaMensal());
        BigDecimal percentualAporteMensal = calculatePercentualAporteMensal(dto.getValorAtivosMesAtual(), parametros.getMetaAporteMensal());

        return percentualLimiteDespesa.add(percentualAporteMensal).divide(BigDecimal.valueOf(2), 2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculatePercentualLimiteDespesa(BigDecimal valorDespesaMesAtual, BigDecimal metaLimiteDespesa) {
        if (metaLimiteDespesa == null || metaLimiteDespesa.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }
        return valorDespesaMesAtual.compareTo(metaLimiteDespesa) <= 0 ? BigDecimal.valueOf(100) : BigDecimal.ZERO;
    }

    private BigDecimal calculatePercentualAporteMensal(BigDecimal valorAtivosMesAtual, BigDecimal metaAporteMensal) {
        if (metaAporteMensal == null || metaAporteMensal.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal percentual = valorAtivosMesAtual.divide(metaAporteMensal, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)).setScale(2, RoundingMode.HALF_UP);
        return percentual.min(BigDecimal.valueOf(100));
    }


    private BigDecimal calculatePercentualMetasAnuais(ResumoFluxoCaixaDTO dto, FluxoCaixaParametro parametros) {
        return calculatePercentualAporteTotal(dto.getValorRendaAnoAtual().subtract(dto.getValorDespesaAnoAtual()), parametros.getMetaAporteTotal());
    }

    private BigDecimal calculatePercentualAporteTotal(BigDecimal rendaLiquidaAnual, BigDecimal metaAporteTotal) {
        if (metaAporteTotal == null || metaAporteTotal.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }
        return rendaLiquidaAnual.divide(metaAporteTotal, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)).setScale(2, RoundingMode.HALF_UP);
    }

}
