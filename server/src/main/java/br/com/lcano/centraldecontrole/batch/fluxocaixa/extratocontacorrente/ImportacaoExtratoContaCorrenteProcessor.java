package br.com.lcano.centraldecontrole.batch.fluxocaixa.extratocontacorrente;

import br.com.lcano.centraldecontrole.domain.*;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.*;
import br.com.lcano.centraldecontrole.dto.fluxocaixa.ExtratoContaCorrenteDTO;
import br.com.lcano.centraldecontrole.enums.TipoLancamento;
import br.com.lcano.centraldecontrole.enums.fluxocaixa.*;
import br.com.lcano.centraldecontrole.service.UsuarioService;
import br.com.lcano.centraldecontrole.service.fluxocaixa.RegraExtratoContaCorrenteService;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@StepScope
public class ImportacaoExtratoContaCorrenteProcessor implements ItemProcessor<ExtratoContaCorrenteDTO, Lancamento>, StepExecutionListener {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RegraExtratoContaCorrenteService extratoContaRegraService;

    private final Long usuarioId;
    private Usuario usuario;
    private List<RegraExtratoContaCorrente> regras;

    @Autowired
    public ImportacaoExtratoContaCorrenteProcessor(@Value("#{jobParameters['usuarioId']}") Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        this.usuario = usuarioService.getUsuarioById(usuarioId);
        this.regras = extratoContaRegraService.findByUsuarioAndAtivoOrderByPrioridadeAsc(usuario);
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return ExitStatus.COMPLETED;
    }

    @Override
    public Lancamento process(ExtratoContaCorrenteDTO dto) {
        boolean isDespesa = dto.getValor().compareTo(BigDecimal.ZERO) < 0;
        RegraExtratoContaCorrente regra = findRegraByDescricaoAndTipo(dto.getDescricao(), isDespesa);

        if (regra == null) {
            return buildFromDTOWithoutRegra(dto, isDespesa);
        }

        return buildFromDTOWithRegra(dto, regra, isDespesa);
    }

    private Lancamento buildFromDTOWithRegra(ExtratoContaCorrenteDTO dto, RegraExtratoContaCorrente regra, boolean isDespesa) {
        return switch (regra.getTipoRegra()) {
            case CLASSIFICAR_DESPESA -> buildDespesaFromDTOAndRegra(dto, regra);
            case CLASSIFICAR_RENDA -> buildRendaFromDTOAndRegra(dto, regra);
            case CLASSIFICAR_ATIVO -> buildAtivoFromDTOAndRegra(dto, regra, isDespesa);
            case IGNORAR_DESPESA, IGNORAR_RENDA -> null;
        };
    }

    private Lancamento buildFromDTOWithoutRegra(ExtratoContaCorrenteDTO dto, boolean isDespesa) {
        return isDespesa
                ? buildDespesaFromDTOAndRegra(dto, null)
                : buildRendaFromDTOAndRegra(dto, null);
    }

    private RegraExtratoContaCorrente findRegraByDescricaoAndTipo(String descricao, boolean isDespesa) {
        return regras.stream()
                .filter(r -> isDescricaoMatchingWithTipo(r, descricao, isDespesa))
                .findFirst().orElse(null);
    }

    private boolean isDescricaoMatchingWithTipo(RegraExtratoContaCorrente regra, String descricao, boolean isDespesa) {
        if (!descricao.toUpperCase().contains(regra.getDescricaoMatch().toUpperCase())) return false;

        TipoRegraExtratoContaCorrente tipo = regra.getTipoRegra();
        return isDespesa
                ? tipo.isDespesa() || tipo.equals(TipoRegraExtratoContaCorrente.CLASSIFICAR_ATIVO)
                : tipo.isRenda();
    }

    private Lancamento buildDespesaFromDTOAndRegra(ExtratoContaCorrenteDTO dto, RegraExtratoContaCorrente regra) {
        Lancamento lancamento = buildLancamentoBaseFromDTOAndTipoAndRegra(dto, TipoLancamento.DESPESA, regra);

        Despesa despesa = new Despesa();
        despesa.setValor(dto.getValor().abs());
        despesa.setDataVencimento(dto.getDataLancamento());
        despesa.setFormaPagamento(DespesaFormaPagamento.PIX);
        despesa.setCategoria(resolveCategoriaDespesaFromRegra(regra));
        despesa.setLancamento(lancamento);

        lancamento.setDespesa(despesa);
        return lancamento;
    }

    private Lancamento buildRendaFromDTOAndRegra(ExtratoContaCorrenteDTO dto, RegraExtratoContaCorrente regra) {
        Lancamento lancamento = buildLancamentoBaseFromDTOAndTipoAndRegra(dto, TipoLancamento.RENDA, regra);

        Renda renda = new Renda();
        renda.setValor(dto.getValor().abs());
        renda.setDataRecebimento(dto.getDataLancamento());
        renda.setCategoria(resolveCategoriaRendaFromRegra(regra));
        renda.setLancamento(lancamento);

        lancamento.setRenda(renda);
        return lancamento;
    }

    private Lancamento buildAtivoFromDTOAndRegra(ExtratoContaCorrenteDTO dto, RegraExtratoContaCorrente regra, boolean isDespesa) {
        Lancamento lancamento = buildLancamentoBaseFromDTOAndTipoAndRegra(dto, TipoLancamento.ATIVO, regra);

        Ativo ativo = new Ativo();
        ativo.setCategoria(regra.getAtivoCategoriaDestino());
        ativo.setOperacao(isDespesa ? TipoOperacaoExtratoMovimentacaoB3.CREDITO : TipoOperacaoExtratoMovimentacaoB3.DEBITO);
        ativo.setValor(dto.getValor().abs());
        ativo.setDataMovimento(dto.getDataLancamento());
        ativo.setLancamento(lancamento);

        lancamento.setAtivo(ativo);
        return lancamento;
    }

    private Lancamento buildLancamentoBaseFromDTOAndTipoAndRegra(ExtratoContaCorrenteDTO dto, TipoLancamento tipo, RegraExtratoContaCorrente regra) {
        Lancamento lancamento = new Lancamento();
        lancamento.setDataLancamento(dto.getDataLancamento());
        lancamento.setDescricao(resolveDescricaoFromDTOAndRegra(dto, regra));
        lancamento.setTipo(tipo);
        lancamento.setUsuario(usuario);
        return lancamento;
    }

    private String resolveDescricaoFromDTOAndRegra(ExtratoContaCorrenteDTO dto, RegraExtratoContaCorrente regra) {
        if (regra != null && regra.getDescricaoDestino() != null) return regra.getDescricaoDestino();
        return isTransferenciaDescricao(dto.getDescricao()) ? buildDescricaoFromTransferencia(dto.getDescricao()) : dto.getDescricao();
    }

    private boolean isTransferenciaDescricao(String descricao) {
        String upper = descricao.toUpperCase();
        return upper.contains("TRANSFERÊNCIA ENVIADA") || upper.contains("TRANSFERÊNCIA RECEBIDA");
    }

    private String buildDescricaoFromTransferencia(String descricao) {
        String prefixo = descricao.toUpperCase().startsWith("TRANSFERÊNCIA ENVIADA")
                ? "Transferência enviada - " : "Transferência recebida - ";
        return prefixo + extractNomeFromDescricao(descricao);
    }

    private String extractNomeFromDescricao(String descricao) {
        int idx1 = descricao.indexOf(" - ");
        int idx2 = descricao.indexOf(" - ", idx1 + 1);
        return (idx1 != -1 && idx2 != -1) ? descricao.substring(idx1 + 3, idx2).trim() : "";
    }

    private DespesaCategoria resolveCategoriaDespesaFromRegra(RegraExtratoContaCorrente regra) {
        return (regra != null && regra.getTipoRegra() == TipoRegraExtratoContaCorrente.CLASSIFICAR_DESPESA &&
                regra.getDespesaCategoriaDestino() != null)
                ? regra.getDespesaCategoriaDestino()
                : extratoContaRegraService.getDespesaCategoriaPadrao();
    }

    private RendaCategoria resolveCategoriaRendaFromRegra(RegraExtratoContaCorrente regra) {
        return (regra != null && regra.getTipoRegra() == TipoRegraExtratoContaCorrente.CLASSIFICAR_RENDA &&
                regra.getRendaCategoriaDestino() != null)
                ? regra.getRendaCategoriaDestino()
                : extratoContaRegraService.getRendaCategoriaPadrao();
    }
}