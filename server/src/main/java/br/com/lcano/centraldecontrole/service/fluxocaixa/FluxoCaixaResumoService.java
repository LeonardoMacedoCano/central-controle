package br.com.lcano.centraldecontrole.service.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.fluxocaixa.FluxoCaixaParametro;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.RendaCategoria;
import br.com.lcano.centraldecontrole.dto.fluxocaixa.ResumoFluxoCaixaDTO;
import br.com.lcano.centraldecontrole.enums.TipoLancamento;
import br.com.lcano.centraldecontrole.service.LancamentoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
public class FluxoCaixaResumoService {
    private static final int SCALE = 2;
    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;
    private static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);
    private static final String[] MONTHS_IN_PORTUGUESE = {
            "Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"
    };
    private static final Map<String, Integer> MONTH_MAP = Map.ofEntries(
            Map.entry("Jan", 1), Map.entry("Fev", 2), Map.entry("Mar", 3), Map.entry("Abr", 4),
            Map.entry("Mai", 5), Map.entry("Jun", 6), Map.entry("Jul", 7), Map.entry("Ago", 8),
            Map.entry("Set", 9), Map.entry("Out", 10), Map.entry("Nov", 11), Map.entry("Dez", 12)
    );


    private final LancamentoService lancamentoService;
    private final FluxoCaixaParametroService parametroService;

    public ResumoFluxoCaixaDTO getResumoFluxoCaixa() {
        parametroService.validateParametro();

        ResumoFluxoCaixaDTO dto = new ResumoFluxoCaixaDTO();
        FluxoCaixaParametro parametros = parametroService.findByUsuario();
        Year anoAtual = Year.now();

        populateMonthlySummary(dto, anoAtual);

        YearMonth ultimoMes = getUltimoMes(dto.getLabelsMensalAnoAtual());

        if (ultimoMes != null) {
            fillResumoWithDados(dto, parametros, anoAtual, ultimoMes);
        } else {
            resetValoresResumo(dto);
        }

        return dto;
    }

    private void fillResumoWithDados(ResumoFluxoCaixaDTO dto, FluxoCaixaParametro parametros, Year anoAtual, YearMonth ultimoMes) {
        populateMonthlyValues(dto, ultimoMes);
        populateAnnualValues(dto, anoAtual);

        dto.setPercentualMetasUltimoMes(calculateMonthlyGoalsPercentage(dto, parametros));
        dto.setPercentualMetasAnoAtual(calculateAnnualGoalsPercentage(dto, parametros));
        dto.setValorRendaPassivaUltimoMes(calculatePassiveIncome(ultimoMes, parametros.getRendaPassivaCategoria()));
    }

    private void resetValoresResumo(ResumoFluxoCaixaDTO dto) {
        dto.setValorRendaUltimoMes(BigDecimal.ZERO);
        dto.setValorDespesaUltimoMes(BigDecimal.ZERO);
        dto.setValorAtivosUltimoMes(BigDecimal.ZERO);
        dto.setValorRendaAnoAtual(BigDecimal.ZERO);
        dto.setValorDespesaAnoAtual(BigDecimal.ZERO);
        dto.setValorAtivosAnoAtual(BigDecimal.ZERO);
        dto.setPercentualMetasUltimoMes(BigDecimal.ZERO);
        dto.setPercentualMetasAnoAtual(BigDecimal.ZERO);
        dto.setValorRendaPassivaUltimoMes(BigDecimal.ZERO);
    }

    private YearMonth getUltimoMes(List<String> labels) {
        if (labels == null || labels.isEmpty()) {
            return null;
        }

        String ultimoLabel = labels.get(labels.size() - 1);
        String[] parts = ultimoLabel.split("/");

        int mes = MONTH_MAP.get(parts[0]);

        return YearMonth.of(Year.now().getValue(), mes);
    }

    private void populateMonthlySummary(ResumoFluxoCaixaDTO dto, Year currentYear) {
        List<String> labels = new ArrayList<>();
        List<BigDecimal> incomeValues = new ArrayList<>();
        List<BigDecimal> expenseValues = new ArrayList<>();

        for (int month = 1; month <= 12; month++) {
            YearMonth yearMonth = YearMonth.of(currentYear.getValue(), month);
            BigDecimal income = calculatePeriodAmount(TipoLancamento.RENDA, yearMonth);
            BigDecimal expense = calculatePeriodAmount(TipoLancamento.DESPESA, yearMonth);

            if (income.compareTo(BigDecimal.ZERO) > 0 || expense.compareTo(BigDecimal.ZERO) > 0) {
                labels.add(String.format("%s/%d", MONTHS_IN_PORTUGUESE[month - 1], currentYear.getValue() % 100));
                incomeValues.add(income);
                expenseValues.add(expense);
            }
        }

        dto.setLabelsMensalAnoAtual(labels);
        if (!incomeValues.isEmpty()) dto.setValoresRendaAnoAtual(incomeValues);
        if (!expenseValues.isEmpty()) dto.setValoresDespesaAnoAtual(expenseValues);
    }

    private void populateMonthlyValues(ResumoFluxoCaixaDTO dto, YearMonth lastMonth) {
        Year currentYear = Year.now();
        if (lastMonth.getYear() == currentYear.getValue()) {
            dto.setValorRendaUltimoMes(calculatePeriodAmount(TipoLancamento.RENDA, lastMonth));
            dto.setValorDespesaUltimoMes(calculatePeriodAmount(TipoLancamento.DESPESA, lastMonth));
            dto.setValorAtivosUltimoMes(calculatePeriodAmount(TipoLancamento.ATIVO, lastMonth));
        } else {
            dto.setValorRendaUltimoMes(BigDecimal.ZERO);
            dto.setValorDespesaUltimoMes(BigDecimal.ZERO);
            dto.setValorAtivosUltimoMes(BigDecimal.ZERO);
        }
    }

    private void populateAnnualValues(ResumoFluxoCaixaDTO dto, Year currentYear) {
        dto.setValorRendaAnoAtual(calculatePeriodAmount(TipoLancamento.RENDA, currentYear));
        dto.setValorDespesaAnoAtual(calculatePeriodAmount(TipoLancamento.DESPESA, currentYear));
        dto.setValorAtivosAnoAtual(calculatePeriodAmount(TipoLancamento.ATIVO, currentYear));
    }

    private BigDecimal calculatePeriodAmount(TipoLancamento type, YearMonth period) {
        Date start = convertToDate(period.atDay(1));
        Date end = convertToDate(period.atEndOfMonth());
        return calculateAmount(type, start, end);
    }

    private BigDecimal calculatePeriodAmount(TipoLancamento type, Year year) {
        Date start = convertToDate(year.atDay(1));
        Date end = convertToDate(year.atMonth(12).atEndOfMonth());
        return calculateAmount(type, start, end);
    }

    private BigDecimal calculateAmount(TipoLancamento type, Date start, Date end) {
        return lancamentoService.findByUsuarioAutenticadoAndTipoAndDateRange(type, start, end)
                .stream()
                .map(lancamento -> switch (lancamento.getTipo()) {
                    case RENDA -> lancamento.getRenda().getValor();
                    case DESPESA -> lancamento.getDespesa().getValor();
                    case ATIVO -> lancamento.getAtivo().getValor();
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculatePassiveIncome(YearMonth period, RendaCategoria passiveIncomeCategory) {
        if (passiveIncomeCategory == null) {
            return BigDecimal.ZERO;
        }

        Date start = convertToDate(period.atDay(1));
        Date end = convertToDate(period.atEndOfMonth());

        return lancamentoService.findByUsuarioAutenticadoAndTipoAndDateRange(TipoLancamento.RENDA, start, end)
                .stream()
                .filter(l -> l.getRenda().getCategoria().equals(passiveIncomeCategory))
                .map(l -> l.getRenda().getValor())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateMonthlyGoalsPercentage(ResumoFluxoCaixaDTO dto, FluxoCaixaParametro parameters) {
        BigDecimal expenseLimitPercentage = calculateExpenseLimitPercentage(
                dto.getValorDespesaUltimoMes(),
                parameters.getMetaLimiteDespesaMensal());

        BigDecimal monthlyContributionPercentage = calculateMonthlyContributionPercentage(
                dto.getValorAtivosUltimoMes(),
                parameters.getMetaAporteMensal());

        return expenseLimitPercentage.add(monthlyContributionPercentage)
                .divide(BigDecimal.valueOf(2), SCALE, ROUNDING_MODE);
    }

    private BigDecimal calculateExpenseLimitPercentage(BigDecimal monthlyExpense, BigDecimal expenseLimitGoal) {
        if (expenseLimitGoal == null || expenseLimitGoal.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }
        return monthlyExpense.compareTo(expenseLimitGoal) <= 0 ? ONE_HUNDRED : BigDecimal.ZERO;
    }

    private BigDecimal calculateMonthlyContributionPercentage(BigDecimal monthlyAssets, BigDecimal monthlyContributionGoal) {
        if (monthlyContributionGoal == null || monthlyContributionGoal.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal percentage = monthlyAssets
                .divide(monthlyContributionGoal, 4, ROUNDING_MODE)
                .multiply(ONE_HUNDRED)
                .setScale(SCALE, ROUNDING_MODE);

        return percentage.min(ONE_HUNDRED);
    }

    private BigDecimal calculateAnnualGoalsPercentage(ResumoFluxoCaixaDTO dto, FluxoCaixaParametro parameters) {
        BigDecimal annualIncome = getValueOrZero(dto.getValorRendaAnoAtual());
        BigDecimal annualExpense = getValueOrZero(dto.getValorDespesaAnoAtual());
        BigDecimal netIncome = annualIncome.subtract(annualExpense);

        return calculateTotalContributionPercentage(netIncome, parameters.getMetaAporteTotal());
    }

    private BigDecimal calculateTotalContributionPercentage(BigDecimal annualNetIncome, BigDecimal totalContributionGoal) {
        if (totalContributionGoal == null || totalContributionGoal.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }

        return annualNetIncome
                .divide(totalContributionGoal, 4, ROUNDING_MODE)
                .multiply(ONE_HUNDRED)
                .setScale(SCALE, ROUNDING_MODE);
    }

    private BigDecimal getValueOrZero(BigDecimal value) {
        return value != null ? value : BigDecimal.ZERO;
    }

    private Date convertToDate(java.time.temporal.TemporalAccessor temporal) {
        return Date.from(java.time.LocalDateTime.of(
                        temporal.get(java.time.temporal.ChronoField.YEAR),
                        temporal.get(java.time.temporal.ChronoField.MONTH_OF_YEAR),
                        temporal.get(java.time.temporal.ChronoField.DAY_OF_MONTH),
                        0, 0)
                .atZone(ZoneId.systemDefault()).toInstant());
    }
}