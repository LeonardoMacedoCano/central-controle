package br.com.lcano.centraldecontrole.batch.fluxocaixa.extratoconta;

import br.com.lcano.centraldecontrole.domain.Usuario;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.ExtratoContaRegra;
import br.com.lcano.centraldecontrole.dto.LancamentoDTO;
import br.com.lcano.centraldecontrole.dto.fluxocaixa.*;
import br.com.lcano.centraldecontrole.enums.TipoLancamentoEnum;
import br.com.lcano.centraldecontrole.enums.fluxocaixa.DespesaFormaPagamentoEnum;
import br.com.lcano.centraldecontrole.enums.fluxocaixa.TipoRegraExtratoConta;
import br.com.lcano.centraldecontrole.service.UsuarioService;
import br.com.lcano.centraldecontrole.service.fluxocaixa.DespesaCategoriaService;
import br.com.lcano.centraldecontrole.service.fluxocaixa.ExtratoContaRegraService;
import br.com.lcano.centraldecontrole.service.fluxocaixa.ReceitaCategoriaService;
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
public class ImportacaoExtratoContaProcessor implements ItemProcessor<ExtratoContaDTO, LancamentoDTO>, StepExecutionListener {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    DespesaCategoriaService despesaCategoriaService;

    @Autowired
    ReceitaCategoriaService receitaCategoriaService;

    @Autowired
    ExtratoContaRegraService extratoContaRegraService;

    private final Long usuarioId;
    private Usuario usuario;
    private List<ExtratoContaRegra> regras;

    @Autowired
    public ImportacaoExtratoContaProcessor(@Value("#{jobParameters['usuarioId']}") Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        this.usuario = usuarioService.getUsuarioById(usuarioId);
        this.regras = extratoContaRegraService.findByUsuarioAndAtivoOrderByPrioridadeAsc(usuario);
    }

    @Override
    public LancamentoDTO process(ExtratoContaDTO extratoContaDTO) {
        ExtratoContaRegra regraCorrespondente = findRegraCorrespondente(extratoContaDTO);

        if (isRegraCorrespondenteTipoIgnorar(regraCorrespondente)) return null;

        return isDespesa(extratoContaDTO)
                ? processarDespesa(extratoContaDTO, regraCorrespondente)
                : processarReceita(extratoContaDTO, regraCorrespondente);
    }

    private ExtratoContaRegra findRegraCorrespondente(ExtratoContaDTO extratoContaDTO) {
        return regras.stream()
                .filter(regra -> descricaoCorresponde(extratoContaDTO.getDescricao(), regra.getDescricaoMatch()))
                .findFirst()
                .orElse(null);
    }

    private boolean isRegraCorrespondenteTipoIgnorar(ExtratoContaRegra regraCorrespondente) {
        return regraCorrespondente != null && regraCorrespondente.getTipoRegra() == TipoRegraExtratoConta.IGNORAR;
    }

    private boolean descricaoCorresponde(String descricao, String regraMatch) {
        return descricao.toUpperCase().contains(regraMatch.toUpperCase());
    }

    private boolean isDespesa(ExtratoContaDTO extratoContaDTO) {
        return extratoContaDTO.getValor().compareTo(BigDecimal.ZERO) < 0;
    }

    private boolean isTransferencia(ExtratoContaDTO extratoContaDTO) {
        String descricao = extratoContaDTO.getDescricao();
        return descricao.contains("Transferência enviada pelo Pix") || descricao.contains("Transferência Recebida");
    }

    private String formatarDescricaoTransferencia(ExtratoContaDTO extratoContaDTO) {
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

    private LancamentoDTO criarLancamentoDTO(ExtratoContaDTO extratoContaDTO, TipoLancamentoEnum tipo, ExtratoContaRegra regraCorrespondente) {
        LancamentoDTO lancamentoDTO = new LancamentoDTO();
        lancamentoDTO.setDataLancamento(extratoContaDTO.getDataLancamento());
        lancamentoDTO.setDescricao(obterDescricaoLancamento(extratoContaDTO, regraCorrespondente));
        lancamentoDTO.setTipo(tipo);
        lancamentoDTO.setUsuario(usuario);
        return lancamentoDTO;
    }

    private String obterDescricaoLancamento(ExtratoContaDTO extratoContaDTO, ExtratoContaRegra regraCorrespondente) {
        if (regraCorrespondente != null && regraCorrespondente.getTipoRegra() == TipoRegraExtratoConta.CLASSIFICAR && regraCorrespondente.getDescricaoDestino() != null) {
            return regraCorrespondente.getDescricaoDestino();
        }
        return isTransferencia(extratoContaDTO) ? formatarDescricaoTransferencia(extratoContaDTO) : extratoContaDTO.getDescricao();
    }

    private LancamentoDTO processarDespesa(ExtratoContaDTO extratoContaDTO, ExtratoContaRegra regraCorrespondente) {
        LancamentoDTO lancamentoDTO = criarLancamentoDTO(extratoContaDTO, TipoLancamentoEnum.DESPESA, regraCorrespondente);

        DespesaDTO despesaDTO = new DespesaDTO();
        despesaDTO.setValor(extratoContaDTO.getValor().abs());
        despesaDTO.setDataVencimento(extratoContaDTO.getDataLancamento());
        despesaDTO.setFormaPagamento(DespesaFormaPagamentoEnum.PIX);
        despesaDTO.setCategoria(obterCategoriaDespesa(regraCorrespondente));

        lancamentoDTO.setItemDTO(despesaDTO);
        return lancamentoDTO;
    }

    private LancamentoDTO processarReceita(ExtratoContaDTO extratoContaDTO, ExtratoContaRegra regraCorrespondente) {
        LancamentoDTO lancamentoDTO = criarLancamentoDTO(extratoContaDTO, TipoLancamentoEnum.RECEITA, regraCorrespondente);

        ReceitaDTO receitaDTO = new ReceitaDTO();
        receitaDTO.setValor(extratoContaDTO.getValor().abs());
        receitaDTO.setDataRecebimento(extratoContaDTO.getDataLancamento());
        receitaDTO.setCategoria(obterCategoriaReceita(regraCorrespondente));

        lancamentoDTO.setItemDTO(receitaDTO);
        return lancamentoDTO;
    }

    private DespesaCategoriaDTO obterCategoriaDespesa(ExtratoContaRegra regraCorrespondente) {
        if (regraCorrespondente != null &&
                regraCorrespondente.getTipoRegra() == TipoRegraExtratoConta.CLASSIFICAR &&
                regraCorrespondente.getIdCategoria() != null) {

            return (DespesaCategoriaDTO) new DespesaCategoriaDTO().fromEntity(despesaCategoriaService.findById(regraCorrespondente.getIdCategoria()));
        } else {
            return extratoContaRegraService.getDespesaCategoriaPadrao();
        }
    }

    private ReceitaCategoriaDTO obterCategoriaReceita(ExtratoContaRegra regraCorrespondente) {
        if (regraCorrespondente != null &&
                regraCorrespondente.getTipoRegra() == TipoRegraExtratoConta.CLASSIFICAR &&
                regraCorrespondente.getIdCategoria() != null) {

            return (ReceitaCategoriaDTO) new ReceitaCategoriaDTO().fromEntity(receitaCategoriaService.findById(regraCorrespondente.getIdCategoria()));
        } else {
            return extratoContaRegraService.getReceitaCategoriaPadrao();
        }
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return ExitStatus.COMPLETED;
    }
}
