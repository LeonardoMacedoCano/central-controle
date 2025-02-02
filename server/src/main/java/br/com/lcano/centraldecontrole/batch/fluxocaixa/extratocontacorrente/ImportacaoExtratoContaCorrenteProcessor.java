package br.com.lcano.centraldecontrole.batch.fluxocaixa.extratocontacorrente;

import br.com.lcano.centraldecontrole.domain.Lancamento;
import br.com.lcano.centraldecontrole.domain.Usuario;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.*;
import br.com.lcano.centraldecontrole.dto.fluxocaixa.ExtratoContaCorrenteDTO;
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
                : criarLancamentoReceita(extratoContaDTO, regra);
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
                        regra.getTipoRegra() == TipoRegraExtratoContaCorrente.IGNORAR_RECEITA
        );
    }

    private boolean correspondeARegra(RegraExtratoContaCorrente regra, String descricao, boolean isDespesa) {
        boolean contemDescricao = descricao.toUpperCase().contains(regra.getDescricaoMatch().toUpperCase());

        return contemDescricao && (
                (isDespesa && regra.getTipoRegra().equals(TipoRegraExtratoContaCorrente.CLASSIFICAR_DESPESA)) ||
                        (!isDespesa && regra.getTipoRegra().equals(TipoRegraExtratoContaCorrente.CLASSIFICAR_RECEITA))
        );
    }

    private Lancamento criarLancamentoDespesa(ExtratoContaCorrenteDTO dto, RegraExtratoContaCorrente regra) {
        Lancamento lancamento = criarLancamentoBase(dto, TipoLancamentoEnum.DESPESA, regra);

        Despesa despesa = new Despesa();
        despesa.setValor(dto.getValor().abs());
        despesa.setDataVencimento(dto.getDataLancamento());
        despesa.setFormaPagamento(DespesaFormaPagamentoEnum.PIX);
        despesa.setCategoria(obterCategoriaDespesa(regra));
        despesa.setLancamento(lancamento);

        lancamento.setDespesa(despesa);
        return lancamento;
    }

    private Lancamento criarLancamentoReceita(ExtratoContaCorrenteDTO dto, RegraExtratoContaCorrente regra) {
        Lancamento lancamento = criarLancamentoBase(dto, TipoLancamentoEnum.RECEITA, regra);

        Receita receita = new Receita();
        receita.setValor(dto.getValor().abs());
        receita.setDataRecebimento(dto.getDataLancamento());
        receita.setCategoria(obterCategoriaReceita(regra));
        receita.setLancamento(lancamento);

        lancamento.setReceita(receita);
        return lancamento;
    }

    private Lancamento criarLancamentoBase(ExtratoContaCorrenteDTO dto, TipoLancamentoEnum tipo, RegraExtratoContaCorrente regra) {
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
        return descricao.contains("Transferência enviada pelo Pix") || descricao.contains("Transferência Recebida");
    }

    private String formatarDescricaoTransferencia(ExtratoContaCorrenteDTO dto) {
        String descricao = dto.getDescricao();
        String parteFixa = descricao.startsWith("Transferência enviada pelo Pix") ?
                "Transferência enviada pelo Pix - " :
                "Transferência Recebida - ";

        return parteFixa + extrairNomePessoa(descricao, parteFixa);
    }

    private String extrairNomePessoa(String descricao, String parteFixa) {
        int inicioNome = descricao.indexOf(parteFixa) + parteFixa.length();
        int fimNome = descricao.indexOf(" - ", inicioNome);
        return descricao.substring(inicioNome, fimNome == -1 ? descricao.length() : fimNome).trim();
    }

    private DespesaCategoria obterCategoriaDespesa(RegraExtratoContaCorrente regra) {
        return (regra != null && regra.getTipoRegra() == TipoRegraExtratoContaCorrente.CLASSIFICAR_DESPESA &&
                regra.getDespesaCategoriaDestino() != null)
                ? regra.getDespesaCategoriaDestino()
                : extratoContaRegraService.getDespesaCategoriaPadrao();
    }

    private ReceitaCategoria obterCategoriaReceita(RegraExtratoContaCorrente regra) {
        return (regra != null && regra.getTipoRegra() == TipoRegraExtratoContaCorrente.CLASSIFICAR_RECEITA &&
                regra.getReceitaCategoriaDestino() != null)
                ? regra.getReceitaCategoriaDestino()
                : extratoContaRegraService.getReceitaCategoriaPadrao();
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return ExitStatus.COMPLETED;
    }
}
