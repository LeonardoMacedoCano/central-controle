package br.com.lcano.centraldecontrole.batch.fluxocaixa.extratoconta;

import br.com.lcano.centraldecontrole.domain.Lancamento;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.Despesa;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.Receita;
import br.com.lcano.centraldecontrole.dto.LancamentoDTO;
import br.com.lcano.centraldecontrole.dto.fluxocaixa.DespesaDTO;
import br.com.lcano.centraldecontrole.dto.fluxocaixa.ReceitaDTO;
import br.com.lcano.centraldecontrole.enums.TipoLancamentoEnum;
import br.com.lcano.centraldecontrole.repository.LancamentoRepository;
import br.com.lcano.centraldecontrole.service.fluxocaixa.DespesaCategoriaService;
import br.com.lcano.centraldecontrole.service.fluxocaixa.ReceitaCategoriaService;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ImportacaoExtratoContaWriter implements ItemWriter<LancamentoDTO> {

    @Autowired
    LancamentoRepository lancamentoRepository;

    @Autowired
    DespesaCategoriaService despesaCategoriaService;

    @Autowired
    ReceitaCategoriaService receitaCategoriaService;

    @Override
    public void write(Chunk<? extends LancamentoDTO> lancamentos) {
        List<Lancamento> lancamentosParaSalvar = lancamentos.getItems().stream()
                .map(this::mapearLancamento)
                .collect(Collectors.toList());

        lancamentoRepository.saveAll(lancamentosParaSalvar);
    }

    private Lancamento mapearLancamento(LancamentoDTO lancamentoDTO) {
        Lancamento lancamento = lancamentoDTO.toEntity();

        if (lancamento.getTipo() == TipoLancamentoEnum.DESPESA) {
            Despesa despesa = mapearDespesa((DespesaDTO) lancamentoDTO.getItemDTO(), lancamento);
            lancamento.setDespesa(despesa);
        } else {
            Receita receita = mapearReceita((ReceitaDTO) lancamentoDTO.getItemDTO(), lancamento);
            lancamento.setReceita(receita);
        }

        return lancamento;
    }

    private Despesa mapearDespesa(DespesaDTO despesaDTO, Lancamento lancamento) {
        return despesaDTO.toEntity(
                despesaDTO.getId(),
                lancamento,
                despesaCategoriaService.getCategoriaById(despesaDTO.getCategoria().getId()),
                despesaDTO.getDataVencimento(),
                despesaDTO.getValor(),
                despesaDTO.getFormaPagamento()
        );
    }

    private Receita mapearReceita(ReceitaDTO receitaDTO, Lancamento lancamento) {
        return receitaDTO.toEntity(
                receitaDTO.getId(),
                lancamento,
                receitaCategoriaService.getCategoriaById(receitaDTO.getCategoria().getId()),
                receitaDTO.getDataRecebimento(),
                receitaDTO.getValor()
        );
    }
}
