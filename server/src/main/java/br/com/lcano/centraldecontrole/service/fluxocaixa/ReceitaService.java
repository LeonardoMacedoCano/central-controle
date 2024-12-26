package br.com.lcano.centraldecontrole.service.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.Lancamento;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.Receita;
import br.com.lcano.centraldecontrole.dto.fluxocaixa.ReceitaDTO;
import br.com.lcano.centraldecontrole.enums.TipoLancamentoEnum;
import br.com.lcano.centraldecontrole.repository.LancamentoItemRepository;
import br.com.lcano.centraldecontrole.repository.fluxocaixa.ReceitaRepository;
import br.com.lcano.centraldecontrole.service.AbstractLancamentoItemService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReceitaService extends AbstractLancamentoItemService<Receita, ReceitaDTO> {

    @Autowired
    private final ReceitaRepository repository;

    @Override
    protected LancamentoItemRepository<Receita> getRepository() {
        return repository;
    }

    @Override
    protected ReceitaDTO getDtoInstance() {
        return new ReceitaDTO();
    }

    @Override
    protected void setLancamento(Receita entity, Lancamento lancamento) {
        entity.setLancamento(lancamento);
    }

    @Override
    public TipoLancamentoEnum getTipoLancamento() {
        return TipoLancamentoEnum.RECEITA;
    }
}