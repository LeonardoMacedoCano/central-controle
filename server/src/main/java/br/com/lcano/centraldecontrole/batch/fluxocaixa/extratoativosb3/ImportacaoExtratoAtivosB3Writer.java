package br.com.lcano.centraldecontrole.batch.fluxocaixa.extratoativosb3;

import br.com.lcano.centraldecontrole.domain.Lancamento;
import br.com.lcano.centraldecontrole.repository.LancamentoRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ImportacaoExtratoAtivosB3Writer implements ItemWriter<Lancamento> {

    @Autowired
    LancamentoRepository lancamentoRepository;

    @Override
    public void write(Chunk<? extends Lancamento> lancamentos) {
        lancamentoRepository.saveAll(lancamentos);
    }
}