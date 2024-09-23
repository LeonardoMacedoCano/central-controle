package br.com.lcano.centraldecontrole.batch.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.Usuario;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.ExtratoFaturaCartao;
import br.com.lcano.centraldecontrole.dto.CategoriaDTO;
import br.com.lcano.centraldecontrole.dto.LancamentoDTO;
import br.com.lcano.centraldecontrole.dto.fluxocaixa.DespesaDTO;
import br.com.lcano.centraldecontrole.enums.TipoLancamentoEnum;
import br.com.lcano.centraldecontrole.service.UsuarioService;
import br.com.lcano.centraldecontrole.service.fluxocaixa.DespesaCategoriaService;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@StepScope
public class ImportacaoExtratoFaturaCartaoProcessor implements ItemProcessor<ExtratoFaturaCartao, LancamentoDTO>, StepExecutionListener {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    DespesaCategoriaService despesaCategoriaService;

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
    public LancamentoDTO process(ExtratoFaturaCartao extratoFaturaCartao) {
        if (this.isDespesa(extratoFaturaCartao)) {
            LancamentoDTO lancamentoDTO = this.buildLancamentoDTO(extratoFaturaCartao);
            DespesaDTO despesaDTO = this.buildDespesaDTO(extratoFaturaCartao);
            lancamentoDTO.setItemDTO(despesaDTO);

            return lancamentoDTO;
        }

        return null;
    }

    private Boolean isDespesa(ExtratoFaturaCartao extratoFaturaCartao) {
        return extratoFaturaCartao.getValor().compareTo(0.00) > 0;
    }

    private LancamentoDTO buildLancamentoDTO(ExtratoFaturaCartao extratoFaturaCartao) {
        LancamentoDTO lancamentoDTO = new LancamentoDTO();

        lancamentoDTO.setDataLancamento(extratoFaturaCartao.getDataLancamento());
        lancamentoDTO.setDescricao(extratoFaturaCartao.getDescricao());
        lancamentoDTO.setTipo(TipoLancamentoEnum.DESPESA);
        lancamentoDTO.setUsuario(usuario);

        return lancamentoDTO;
    }

    private DespesaDTO buildDespesaDTO(ExtratoFaturaCartao extratoFaturaCartao) {
        DespesaDTO despesaDTO = new DespesaDTO();

        despesaDTO.setCategoria(this.getDespesaCategoriaDTO(extratoFaturaCartao.getCategoria()));
        despesaDTO.setDataVencimento(dataVencimento);
        despesaDTO.setValor(extratoFaturaCartao.getValor());
        despesaDTO.setPago(true);

        return despesaDTO;
    }

    private CategoriaDTO getDespesaCategoriaDTO(String descricaoCategoria) {
        return this.despesaCategoriaService.findOrCreateCategoria(descricaoCategoria);
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }
}
