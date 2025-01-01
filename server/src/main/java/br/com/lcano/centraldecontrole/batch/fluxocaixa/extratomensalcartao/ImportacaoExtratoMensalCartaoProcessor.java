package br.com.lcano.centraldecontrole.batch.fluxocaixa.extratomensalcartao;

import br.com.lcano.centraldecontrole.domain.Lancamento;
import br.com.lcano.centraldecontrole.domain.Usuario;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.Despesa;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.DespesaCategoria;
import br.com.lcano.centraldecontrole.dto.fluxocaixa.ExtratoMensalCartaoDTO;
import br.com.lcano.centraldecontrole.enums.TipoLancamentoEnum;
import br.com.lcano.centraldecontrole.enums.fluxocaixa.DespesaFormaPagamentoEnum;
import br.com.lcano.centraldecontrole.service.UsuarioService;
import br.com.lcano.centraldecontrole.service.fluxocaixa.DespesaCategoriaService;
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
public class ImportacaoExtratoMensalCartaoProcessor implements ItemProcessor<ExtratoMensalCartaoDTO, Lancamento>, StepExecutionListener {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    DespesaCategoriaService despesaCategoriaService;

    private final Long usuarioId;
    private final Date dataVencimento;
    private Usuario usuario;

    @Autowired
    public ImportacaoExtratoMensalCartaoProcessor(
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
    public Lancamento process(ExtratoMensalCartaoDTO extratoMensalCartaoDTO) {
        if (this.isDespesa(extratoMensalCartaoDTO)) {
            Lancamento lancamento = this.buildLancamento(extratoMensalCartaoDTO);
            Despesa despesa = this.buildDespesa(extratoMensalCartaoDTO, lancamento);
            lancamento.setDespesa(despesa);
            return lancamento;
        }

        return null;
    }

    private Boolean isDespesa(ExtratoMensalCartaoDTO extratoMensalCartaoDTO) {
        return extratoMensalCartaoDTO.getValor().compareTo(BigDecimal.valueOf(0.00)) > 0;
    }

    private Lancamento buildLancamento(ExtratoMensalCartaoDTO extratoMensalCartaoDTO) {
        Lancamento lancamento = new Lancamento();
        lancamento.setDataLancamento(extratoMensalCartaoDTO.getDataLancamento());
        lancamento.setDescricao(extratoMensalCartaoDTO.getDescricao());
        lancamento.setTipo(TipoLancamentoEnum.DESPESA);
        lancamento.setUsuario(usuario);
        return lancamento;
    }

    private Despesa buildDespesa(ExtratoMensalCartaoDTO extratoMensalCartaoDTO, Lancamento lancamento) {
        Despesa despesa = new Despesa();
        String descricaoCategoriaFormatada = StringUtil.capitalizeFirstLetter(extratoMensalCartaoDTO.getCategoria());
        despesa.setCategoria(this.getDespesaCategoriaDTO(descricaoCategoriaFormatada));
        despesa.setDataVencimento(dataVencimento);
        despesa.setValor(extratoMensalCartaoDTO.getValor());
        despesa.setFormaPagamento(DespesaFormaPagamentoEnum.CARTAO_CREDITO);
        despesa.setLancamento(lancamento);
        return despesa;
    }

    private DespesaCategoria getDespesaCategoriaDTO(String descricaoCategoria) {
        return this.despesaCategoriaService.findOrCreate(descricaoCategoria).toEntity();
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }
}
