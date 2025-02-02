package br.com.lcano.centraldecontrole.batch.fluxocaixa.extratocontacorrente;

import br.com.lcano.centraldecontrole.domain.Lancamento;
import br.com.lcano.centraldecontrole.domain.Usuario;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.*;
import br.com.lcano.centraldecontrole.dto.fluxocaixa.*;
import br.com.lcano.centraldecontrole.enums.TipoLancamentoEnum;
import br.com.lcano.centraldecontrole.enums.fluxocaixa.DespesaFormaPagamentoEnum;
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
public class ImportacaoExtratoContaCorrenteProcessor implements ItemProcessor<ExtratoContaCorrenteDTO, Lancamento>, StepExecutionListener {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    RegraExtratoContaCorrenteService extratoContaRegraService;

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
        RegraExtratoContaCorrente regraCorrespondente = findRegraCorrespondente(extratoContaDTO);

        if (isRegraCorrespondenteTipoIgnorar(regraCorrespondente)) return null;

        return isDespesa(extratoContaDTO)
                ? processarDespesa(extratoContaDTO, regraCorrespondente)
                : processarReceita(extratoContaDTO, regraCorrespondente);
    }

    private RegraExtratoContaCorrente findRegraCorrespondente(ExtratoContaCorrenteDTO extratoContaDTO) {
        return regras.stream()
                .filter(regra -> descricaoCorresponde(extratoContaDTO.getDescricao(), regra.getDescricaoMatch()))
                .findFirst()
                .orElse(null);
    }

    private boolean isRegraCorrespondenteTipoIgnorar(RegraExtratoContaCorrente regraCorrespondente) {
        return regraCorrespondente != null && regraCorrespondente.getTipoRegra() == TipoRegraExtratoContaCorrente.IGNORAR;
    }

    private boolean descricaoCorresponde(String descricao, String regraMatch) {
        return descricao.toUpperCase().contains(regraMatch.toUpperCase());
    }

    private boolean isDespesa(ExtratoContaCorrenteDTO extratoContaDTO) {
        return extratoContaDTO.getValor().compareTo(BigDecimal.ZERO) < 0;
    }

    private boolean isTransferencia(ExtratoContaCorrenteDTO extratoContaDTO) {
        String descricao = extratoContaDTO.getDescricao();
        return descricao.contains("Transferência enviada pelo Pix") || descricao.contains("Transferência Recebida");
    }

    private String formatarDescricaoTransferencia(ExtratoContaCorrenteDTO extratoContaDTO) {
        String descricao = extratoContaDTO.getDescricao();
        String parteFixa = descricao.startsWith("Transferência enviada pelo Pix")
                ? "Transferência enviada pelo Pix - "
                : "Transferência Recebida - ";
        return parteFixa + extrairNomePessoa(descricao, parteFixa);
    }

    private String extrairNomePessoa(String descricao, String parteFixa) {
        int inicioNome = descricao.indexOf(parteFixa) + parteFixa.length();
        int fimNome = descricao.indexOf(" - ", inicioNome);
        return descricao.substring(inicioNome, fimNome == -1 ? descricao.length() : fimNome).trim();
    }

    private Lancamento buildLancamento(ExtratoContaCorrenteDTO extratoContaDTO, TipoLancamentoEnum tipo, RegraExtratoContaCorrente regraCorrespondente) {
        Lancamento lancamento = new Lancamento();
        lancamento.setDataLancamento(extratoContaDTO.getDataLancamento());
        lancamento.setDescricao(obterDescricaoLancamento(extratoContaDTO, regraCorrespondente));
        lancamento.setTipo(tipo);
        lancamento.setUsuario(usuario);
        return lancamento;
    }

    private String obterDescricaoLancamento(ExtratoContaCorrenteDTO extratoContaDTO, RegraExtratoContaCorrente regraCorrespondente) {
        if (regraCorrespondente != null && regraCorrespondente.getTipoRegra() == TipoRegraExtratoContaCorrente.CLASSIFICAR && regraCorrespondente.getDescricaoDestino() != null) {
            return regraCorrespondente.getDescricaoDestino();
        }
        return isTransferencia(extratoContaDTO) ? formatarDescricaoTransferencia(extratoContaDTO) : extratoContaDTO.getDescricao();
    }

    private Lancamento processarDespesa(ExtratoContaCorrenteDTO extratoContaDTO, RegraExtratoContaCorrente regraCorrespondente) {
        Lancamento lancamento = buildLancamento(extratoContaDTO, TipoLancamentoEnum.DESPESA, regraCorrespondente);

        Despesa despesa = new Despesa();
        despesa.setValor(extratoContaDTO.getValor().abs());
        despesa.setDataVencimento(extratoContaDTO.getDataLancamento());
        despesa.setFormaPagamento(DespesaFormaPagamentoEnum.PIX);
        despesa.setCategoria(obterCategoriaDespesa(regraCorrespondente));
        despesa.setLancamento(lancamento);

        lancamento.setDespesa(despesa);
        return lancamento;
    }

    private Lancamento processarReceita(ExtratoContaCorrenteDTO extratoContaDTO, RegraExtratoContaCorrente regraCorrespondente) {
        Lancamento lancamento = buildLancamento(extratoContaDTO, TipoLancamentoEnum.RECEITA, regraCorrespondente);

        Receita receita = new Receita();
        receita.setValor(extratoContaDTO.getValor().abs());
        receita.setDataRecebimento(extratoContaDTO.getDataLancamento());
        receita.setCategoria(obterCategoriaReceita(regraCorrespondente));
        receita.setLancamento(lancamento);

        lancamento.setReceita(receita);
        return lancamento;
    }

    private DespesaCategoria obterCategoriaDespesa(RegraExtratoContaCorrente regraCorrespondente) {
        if (regraCorrespondente != null &&
                regraCorrespondente.getTipoRegra() == TipoRegraExtratoContaCorrente.CLASSIFICAR &&
                regraCorrespondente.getDespesaCategoriaDestino() != null) {

            return regraCorrespondente.getDespesaCategoriaDestino();
        } else {
            return extratoContaRegraService.getDespesaCategoriaPadrao();
        }
    }

    private ReceitaCategoria obterCategoriaReceita(RegraExtratoContaCorrente regraCorrespondente) {
        if (regraCorrespondente != null &&
                regraCorrespondente.getTipoRegra() == TipoRegraExtratoContaCorrente.CLASSIFICAR &&
                regraCorrespondente.getReceitaCategoriaDestino() != null) {

            return regraCorrespondente.getReceitaCategoriaDestino();
        } else {
            return extratoContaRegraService.getReceitaCategoriaPadrao();
        }
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return ExitStatus.COMPLETED;
    }
}
