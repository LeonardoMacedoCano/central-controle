package br.com.lcano.centraldecontrole.batch.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.Lancamento;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.ExtratoFaturaCartao;
import br.com.lcano.centraldecontrole.dto.CategoriaDTO;
import br.com.lcano.centraldecontrole.dto.fluxocaixa.DespesaDTO;
import br.com.lcano.centraldecontrole.enums.TipoLancamentoEnum;
import br.com.lcano.centraldecontrole.service.fluxocaixa.DespesaCategoriaService;
import br.com.lcano.centraldecontrole.util.UsuarioUtil;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ImportacaoExtratoFaturaCartaoProcessor implements ItemProcessor<ExtratoFaturaCartao, Lancamento> {
    @Autowired
    UsuarioUtil usuarioUtil;
    @Autowired
    DespesaCategoriaService despesaCategoriaService;

    @Override
    public Lancamento process(ExtratoFaturaCartao extratoFaturaCartao) throws Exception {
        if (this.isDespesa(extratoFaturaCartao)) {
            Lancamento lancamento = this.createLancamento(extratoFaturaCartao);

            DespesaDTO despesaDTO = new DespesaDTO();
            despesaDTO.setCategoria(this.getCategoria(extratoFaturaCartao.getCategoria()));
            despesaDTO.setValorTotal(extratoFaturaCartao.getValor());

            //retirar parcela e adicionar item ao lancamento

            return lancamento;
        }

        return null;
    }

    private Boolean isDespesa(ExtratoFaturaCartao extratoFaturaCartao) {
        return extratoFaturaCartao.getValor().compareTo(0.00) > 0;
    }

    private Lancamento createLancamento(ExtratoFaturaCartao extratoFaturaCartao) {
        Lancamento lancamento = new Lancamento();

        lancamento.setDataLancamento(extratoFaturaCartao.getDataLancamento());
        lancamento.setDescricao(extratoFaturaCartao.getDescricao());
        lancamento.setTipo(TipoLancamentoEnum.DESPESA);
        lancamento.setUsuario(usuarioUtil.getUsuarioAutenticado()); // alterar para pegar dos parametros do job

        return lancamento;
    }

    private CategoriaDTO getCategoria(String descricaoCategoria) {
        return this.despesaCategoriaService.findOrCreateCategoria(descricaoCategoria);
    }
}
