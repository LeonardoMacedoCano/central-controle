package br.com.lcano.centraldecontrole.batch.fluxocaixa.extratoativosb3;

import br.com.lcano.centraldecontrole.domain.Lancamento;
import br.com.lcano.centraldecontrole.domain.Usuario;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.*;
import br.com.lcano.centraldecontrole.dto.fluxocaixa.ExtratoAtivosB3DTO;
import br.com.lcano.centraldecontrole.enums.TipoLancamento;
import br.com.lcano.centraldecontrole.enums.fluxocaixa.AtivoCategoria;
import br.com.lcano.centraldecontrole.enums.fluxocaixa.AtivoOperacao;
import br.com.lcano.centraldecontrole.service.UsuarioService;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;

@Component
@StepScope
public class ImportacaoExtratoAtivosB3Processor implements ItemProcessor<ExtratoAtivosB3DTO, Lancamento>, StepExecutionListener {

    @Autowired
    UsuarioService usuarioService;

    private final Long usuarioId;
    private Usuario usuario;

    @Autowired
    public ImportacaoExtratoAtivosB3Processor(@Value("#{jobParameters['usuarioId']}") Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        this.usuario = usuarioService.getUsuarioById(usuarioId);
    }

    @Override
    public Lancamento process(ExtratoAtivosB3DTO extratoAtivosB3DTO) {
        return buildLancamentoAtivo(extratoAtivosB3DTO);
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return ExitStatus.COMPLETED;
    }

    private Lancamento buildLancamentoAtivo(ExtratoAtivosB3DTO extratoAtivosB3DTO) {
        Ativo ativo = new Ativo();
        ativo.setCategoria(AtivoCategoria.DESCONHECIDO);
        ativo.setTicker(formatTickerAtivo(extratoAtivosB3DTO.getCodigoNegociacao()));
        ativo.setOperacao(
                AtivoOperacao.fromDescricao(
                        extratoAtivosB3DTO.getTipoMovimentacao().toUpperCase()
                )
        );
        ativo.setQuantidade(extratoAtivosB3DTO.getQuantidade());
        ativo.setPrecoUnitario(extratoAtivosB3DTO.getPreco());
        ativo.setDataMovimento(extratoAtivosB3DTO.getDataNegocio());


        Lancamento lancamento = new Lancamento();
        lancamento.setDataLancamento(extratoAtivosB3DTO.getDataNegocio());
        lancamento.setDescricao(obterDescricaoLancamento(ativo));
        lancamento.setTipo(TipoLancamento.ATIVO);
        lancamento.setUsuario(usuario);
        lancamento.setAtivo(ativo);
        ativo.setLancamento(lancamento);

        return lancamento;
    }

    private String obterDescricaoLancamento(Ativo ativo) {
        DecimalFormat decimalFormat = new DecimalFormat("#0.######");
        String quantidadeFormatada = decimalFormat.format(ativo.getQuantidade());

        return String.format("%s - %s x %s", ativo.getOperacao().getDescricao(), quantidadeFormatada, ativo.getTicker());
    }


    private String formatTickerAtivo(String codigoNegociacao) {
        return codigoNegociacao.split(" - ")[0];
    }

}
