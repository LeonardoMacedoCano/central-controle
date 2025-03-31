package br.com.lcano.centraldecontrole.batch.fluxocaixa.extratofaturacartao;

import br.com.lcano.centraldecontrole.domain.Lancamento;
import br.com.lcano.centraldecontrole.domain.Usuario;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.Despesa;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.DespesaCategoria;
import br.com.lcano.centraldecontrole.dto.fluxocaixa.ExtratoFaturaCartaoDTO;
import br.com.lcano.centraldecontrole.enums.TipoLancamento;
import br.com.lcano.centraldecontrole.enums.fluxocaixa.DespesaFormaPagamento;
import br.com.lcano.centraldecontrole.service.UsuarioService;
import br.com.lcano.centraldecontrole.service.fluxocaixa.DespesaCategoriaService;
import br.com.lcano.centraldecontrole.service.fluxocaixa.FluxoCaixaParametroService;
import br.com.lcano.centraldecontrole.util.StringUtil;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Component
@StepScope
public class ImportacaoExtratoFaturaCartaoProcessor implements ItemProcessor<ExtratoFaturaCartaoDTO, Lancamento>, StepExecutionListener {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    DespesaCategoriaService despesaCategoriaService;

    @Autowired
    FluxoCaixaParametroService fluxoCaixaParametroService;

    private final Long usuarioId;
    private final Date dataVencimento;
    private Usuario usuario;

    @Autowired
    public ImportacaoExtratoFaturaCartaoProcessor(
            @Value("#{jobParameters['usuarioId']}") Long usuarioId,
            @Value("#{jobParameters['dataVencimento']}") Date dataVencimento) {
        this.usuarioId = usuarioId;
        this.dataVencimento = dataVencimento;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        this.usuario = usuarioService.getUsuarioById(usuarioId);
    }

    @Override
    public Lancamento process(ExtratoFaturaCartaoDTO extratoFaturaCartaoDTO) {
        if (this.isDespesa(extratoFaturaCartaoDTO)) {
            Lancamento lancamento = this.buildLancamento(extratoFaturaCartaoDTO);
            Despesa despesa = this.buildDespesa(extratoFaturaCartaoDTO, lancamento);
            lancamento.setDespesa(despesa);
            return lancamento;
        }

        return null;
    }

    private Boolean isDespesa(ExtratoFaturaCartaoDTO extratoFaturaCartaoDTO) {
        return extratoFaturaCartaoDTO.getValor().compareTo(BigDecimal.valueOf(0.00)) > 0;
    }

    private Lancamento buildLancamento(ExtratoFaturaCartaoDTO extratoFaturaCartaoDTO) {
        Lancamento lancamento = new Lancamento();
        lancamento.setDataLancamento(extratoFaturaCartaoDTO.getDataLancamento());
        lancamento.setDescricao(extratoFaturaCartaoDTO.getDescricao());
        lancamento.setTipo(TipoLancamento.DESPESA);
        lancamento.setUsuario(usuario);
        return lancamento;
    }

    private Despesa buildDespesa(ExtratoFaturaCartaoDTO extratoFaturaCartaoDTO, Lancamento lancamento) {
        Despesa despesa = new Despesa();
        String descricaoCategoriaFormatada = StringUtil.capitalizeFirstLetter(extratoFaturaCartaoDTO.getCategoria());
        despesa.setCategoria(this.getDespesaCategoriaDTO(descricaoCategoriaFormatada));
        despesa.setDataVencimento(dataVencimento);
        despesa.setValor(extratoFaturaCartaoDTO.getValor());
        despesa.setFormaPagamento(DespesaFormaPagamento.CARTAO_CREDITO);
        despesa.setLancamento(lancamento);
        return despesa;
    }

    private DespesaCategoria getDespesaCategoriaDTO(String descricaoCategoria) {
        return (descricaoCategoria.isEmpty()) ?
                this.fluxoCaixaParametroService.getDespesaCategoriaPadrao() :
                this.despesaCategoriaService.findOrCreate(descricaoCategoria).toEntity();
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }
}
