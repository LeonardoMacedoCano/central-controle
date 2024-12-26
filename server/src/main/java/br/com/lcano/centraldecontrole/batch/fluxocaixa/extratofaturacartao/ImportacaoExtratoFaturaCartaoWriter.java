package br.com.lcano.centraldecontrole.batch.fluxocaixa.extratofaturacartao;

import br.com.lcano.centraldecontrole.domain.Lancamento;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.Despesa;
import br.com.lcano.centraldecontrole.dto.LancamentoDTO;
import br.com.lcano.centraldecontrole.dto.fluxocaixa.DespesaDTO;
import br.com.lcano.centraldecontrole.repository.LancamentoRepository;
import br.com.lcano.centraldecontrole.service.fluxocaixa.DespesaCategoriaService;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ImportacaoExtratoFaturaCartaoWriter implements ItemWriter<LancamentoDTO> {

    @Autowired
    LancamentoRepository lancamentoRepository;
    @Autowired
    DespesaCategoriaService despesaCategoriaService;

    @Override
    public void write(Chunk<? extends LancamentoDTO> lancamentos) {
        List<Lancamento> lancamentosParaSalvar = new ArrayList<>();

        for (LancamentoDTO lancamentoDTO : lancamentos.getItems()) {
            Lancamento lancamento = lancamentoDTO.toEntity();

            DespesaDTO despesaDTO = (DespesaDTO) lancamentoDTO.getItemDTO();

            Despesa despesa = despesaDTO.toEntity();
            despesa.setLancamento(lancamento);

            lancamento.setDespesa(despesa);
            lancamentosParaSalvar.add(lancamento);
        }

        lancamentoRepository.saveAll(lancamentosParaSalvar);
    }
}
