package br.com.lcano.centraldecontrole.batch.fluxocaixa.extratocontacorrente;

import br.com.lcano.centraldecontrole.domain.Lancamento;
import br.com.lcano.centraldecontrole.domain.Usuario;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.*;
import br.com.lcano.centraldecontrole.dto.fluxocaixa.ExtratoContaCorrenteDTO;
import br.com.lcano.centraldecontrole.enums.TipoLancamento;
import br.com.lcano.centraldecontrole.enums.fluxocaixa.DespesaFormaPagamento;
import br.com.lcano.centraldecontrole.enums.fluxocaixa.TipoRegraExtratoContaCorrente;
import br.com.lcano.centraldecontrole.service.UsuarioService;
import br.com.lcano.centraldecontrole.service.fluxocaixa.RegraExtratoContaCorrenteService;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@StepScope
public class ImportacaoExtratoContaCorrenteProcessor
        implements ItemProcessor<ExtratoContaCorrenteDTO, Lancamento>, StepExecutionListener {

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
    public Lancamento process(ExtratoContaCorrenteDTO extratoContaDTO) {
        boolean isDespesa = extratoContaDTO.getValor().compareTo(BigDecimal.ZERO) < 0;
        RegraExtratoContaCorrente regra = encontrarRegra(extratoContaDTO, isDespesa);

        if (deveIgnorarRegra(regra)) return null;

        return isDespesa
                ? criarLancamentoDespesa(extratoContaDTO, regra)
                : criarLancamentoRenda(extratoContaDTO, regra);
    }

    private RegraExtratoContaCorrente encontrarRegra(ExtratoContaCorrenteDTO dto, boolean isDespesa) {
        return regras.stream()
                .filter(regra -> correspondeARegra(regra, dto.getDescricao(), isDespesa))
                .findFirst()
                .orElse(null);
    }

    private boolean deveIgnorarRegra(RegraExtratoContaCorrente regra) {
        return regra != null && (
                regra.getTipoRegra() == TipoRegraExtratoContaCorrente.IGNORAR_DESPESA ||
                        regra.getTipoRegra() == TipoRegraExtratoContaCorrente.IGNORAR_RENDA
        );
    }

    private boolean correspondeARegra(RegraExtratoContaCorrente regra, String descricao, boolean isDespesa) {
        boolean contemDescricao = descricao.toUpperCase().contains(regra.getDescricaoMatch().toUpperCase());

        if (!contemDescricao) {
            return false;
        }

        TipoRegraExtratoContaCorrente tipoRegra = regra.getTipoRegra();
        if (isDespesa) {
            return tipoRegra.equals(TipoRegraExtratoContaCorrente.CLASSIFICAR_DESPESA) ||
                    tipoRegra.equals(TipoRegraExtratoContaCorrente.IGNORAR_DESPESA);
        } else {
            return tipoRegra.equals(TipoRegraExtratoContaCorrente.CLASSIFICAR_RENDA) ||
                    tipoRegra.equals(TipoRegraExtratoContaCorrente.IGNORAR_RENDA);
        }
    }


    private Lancamento criarLancamentoDespesa(ExtratoContaCorrenteDTO dto, RegraExtratoContaCorrente regra) {
        Lancamento lancamento = criarLancamentoBase(dto, TipoLancamento.DESPESA, regra);

        Despesa despesa = new Despesa();
        despesa.setValor(dto.getValor().abs());
        despesa.setDataVencimento(dto.getDataLancamento());
        despesa.setFormaPagamento(DespesaFormaPagamento.PIX);
        despesa.setCategoria(obterCategoriaDespesa(regra));
        despesa.setLancamento(lancamento);

        lancamento.setDespesa(despesa);
        return lancamento;
    }

    private Lancamento criarLancamentoRenda(ExtratoContaCorrenteDTO dto, RegraExtratoContaCorrente regra) {
        Lancamento lancamento = criarLancamentoBase(dto, TipoLancamento.RENDA, regra);

        Renda renda = new Renda();
        renda.setValor(dto.getValor().abs());
        renda.setDataRecebimento(dto.getDataLancamento());
        renda.setCategoria(obterCategoriaRenda(regra));
        renda.setLancamento(lancamento);

        lancamento.setRenda(renda);
        return lancamento;
    }

    private Lancamento criarLancamentoBase(ExtratoContaCorrenteDTO dto, TipoLancamento tipo, RegraExtratoContaCorrente regra) {
        Lancamento lancamento = new Lancamento();
        lancamento.setDataLancamento(dto.getDataLancamento());
        lancamento.setDescricao(obterDescricaoLancamento(dto, regra));
        lancamento.setTipo(tipo);
        lancamento.setUsuario(usuario);
        return lancamento;
    }

    private String obterDescricaoLancamento(ExtratoContaCorrenteDTO dto, RegraExtratoContaCorrente regra) {
        if (regra != null && regra.getDescricaoDestino() != null) {
            return regra.getDescricaoDestino();
        }
        return ehTransferencia(dto) ? formatarDescricaoTransferencia(dto) : dto.getDescricao();
    }

    private boolean ehTransferencia(ExtratoContaCorrenteDTO dto) {
        String descricao = dto.getDescricao();
        return descricao.toUpperCase().contains("TRANSFERÊNCIA ENVIADA") || descricao.toUpperCase().contains("TRANSFERÊNCIA RECEBIDA");
    }

    private String formatarDescricaoTransferencia(ExtratoContaCorrenteDTO dto) {
        String descricao = dto.getDescricao();
        String parteFixa = descricao.toUpperCase().startsWith("TRANSFERÊNCIA ENVIADA") ?
                "Transferência enviada - " :
                "Transferência recebida - ";

        return parteFixa + extrairNomePessoa(descricao);
    }

    private String extrairNomePessoa(String descricao) {
        int primeiroHifen = descricao.indexOf(" - ");
        int segundoHifen = descricao.indexOf(" - ", primeiroHifen + 1);

        if (primeiroHifen != -1 && segundoHifen != -1) {
            return descricao.substring(primeiroHifen + 3, segundoHifen).trim();
        }

        return "";
    }


    private DespesaCategoria obterCategoriaDespesa(RegraExtratoContaCorrente regra) {
        return (regra != null && regra.getTipoRegra() == TipoRegraExtratoContaCorrente.CLASSIFICAR_DESPESA &&
                regra.getDespesaCategoriaDestino() != null)
                ? regra.getDespesaCategoriaDestino()
                : extratoContaRegraService.getDespesaCategoriaPadrao();
    }

    private RendaCategoria obterCategoriaRenda(RegraExtratoContaCorrente regra) {
        return (regra != null && regra.getTipoRegra() == TipoRegraExtratoContaCorrente.CLASSIFICAR_RENDA &&
                regra.getRendaCategoriaDestino() != null)
                ? regra.getRendaCategoriaDestino()
                : extratoContaRegraService.getRendaCategoriaPadrao();
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return ExitStatus.COMPLETED;
    }
}
