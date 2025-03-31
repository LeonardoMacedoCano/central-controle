package br.com.lcano.centraldecontrole.batch.fluxocaixa.extratomovimentacaob3;

import br.com.lcano.centraldecontrole.domain.Lancamento;
import br.com.lcano.centraldecontrole.domain.Usuario;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.*;
import br.com.lcano.centraldecontrole.dto.fluxocaixa.ExtratoMovimentacaoB3DTO;
import br.com.lcano.centraldecontrole.enums.TipoLancamento;
import br.com.lcano.centraldecontrole.enums.fluxocaixa.AtivoCategoria;
import br.com.lcano.centraldecontrole.enums.fluxocaixa.TipoOperacaoExtratoMovimentacaoB3;
import br.com.lcano.centraldecontrole.enums.fluxocaixa.TipoMovimentacaoExtratoMovimentacaoB3;
import br.com.lcano.centraldecontrole.service.UsuarioService;
import br.com.lcano.centraldecontrole.service.fluxocaixa.FluxoCaixaParametroService;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;

@Component
@StepScope
public class ImportacaoExtratoMovimentacaoB3Processor implements ItemProcessor<ExtratoMovimentacaoB3DTO, Lancamento>, StepExecutionListener {

    private final UsuarioService usuarioService;
    private final FluxoCaixaParametroService fluxoCaixaParametroService;
    private final Long usuarioId;
    private Usuario usuario;

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#0.######");

    public ImportacaoExtratoMovimentacaoB3Processor(
            UsuarioService usuarioService,
            FluxoCaixaParametroService fluxoCaixaParametroService,
            @Value("#{jobParameters['usuarioId']}") Long usuarioId
    ) {
        this.usuarioService = usuarioService;
        this.fluxoCaixaParametroService = fluxoCaixaParametroService;
        this.usuarioId = usuarioId;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        this.usuario = usuarioService.getUsuarioById(usuarioId);
    }

    @Override
    public Lancamento process(ExtratoMovimentacaoB3DTO extratoDTO) {
        return buildLancamento(extratoDTO);
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return ExitStatus.COMPLETED;
    }

    private Lancamento buildLancamento(ExtratoMovimentacaoB3DTO extratoDTO) {
        TipoMovimentacaoExtratoMovimentacaoB3 tipoMovimentacao = TipoMovimentacaoExtratoMovimentacaoB3.fromDescricao(extratoDTO.getTipoMovimentacao());

        Lancamento lancamento = new Lancamento();
        lancamento.setDataLancamento(extratoDTO.getDataMovimentacao());
        lancamento.setUsuario(usuario);

        if (tipoMovimentacao.isAtivo()) {
            Ativo ativo = buildAtivo(extratoDTO);
            ativo.setLancamento(lancamento);

            lancamento.setDescricao(obterDescricaoLancamentoAtivo(ativo));
            lancamento.setTipo(TipoLancamento.ATIVO);
            lancamento.setAtivo(ativo);
        } else {
            Renda renda = buildRenda(extratoDTO);
            renda.setLancamento(lancamento);

            lancamento.setDescricao(obterDescricaoLancamentoRenda(extratoDTO));
            lancamento.setTipo(TipoLancamento.RENDA);
            lancamento.setRenda(renda);
        }

        return lancamento;
    }

    private Ativo buildAtivo(ExtratoMovimentacaoB3DTO extratoDTO) {
        Ativo ativo = new Ativo();
        ativo.setCategoria(AtivoCategoria.DESCONHECIDO);
        ativo.setTicker(formatTickerProduto(extratoDTO.getProduto()));
        ativo.setOperacao(
                TipoOperacaoExtratoMovimentacaoB3.fromDescricao(
                        extratoDTO.getTipoOperacao().toUpperCase()
                )
        );
        ativo.setQuantidade(extratoDTO.getQuantidade());
        ativo.setPrecoUnitario(extratoDTO.getPrecoUnitario());
        ativo.setDataMovimento(extratoDTO.getDataMovimentacao());
        return ativo;
    }

    private Renda buildRenda(ExtratoMovimentacaoB3DTO extratoDTO) {
        Renda renda = new Renda();
        renda.setValor(extratoDTO.getPrecoTotal());
        renda.setDataRecebimento(extratoDTO.getDataMovimentacao());
        renda.setCategoria(fluxoCaixaParametroService.getRendaPassivaCategoria());
        return renda;
    }

    private String obterDescricaoLancamentoAtivo(Ativo ativo) {
        String descricaoOperacao = ativo.getOperacao() == TipoOperacaoExtratoMovimentacaoB3.CREDITO ? "Compra" : "Venda";
        return String.format("%s - %sx %s",
                descricaoOperacao,
                DECIMAL_FORMAT.format(ativo.getQuantidade()),
                ativo.getTicker());
    }

    private String obterDescricaoLancamentoRenda(ExtratoMovimentacaoB3DTO extratoDTO) {
        return String.format("%s - %sx %s",
                extratoDTO.getTipoMovimentacao(),
                DECIMAL_FORMAT.format(extratoDTO.getQuantidade()),
                formatTickerProduto(extratoDTO.getProduto()));
    }

    private String formatTickerProduto(String produto) {
        return produto.split(" - ")[0];
    }
}
