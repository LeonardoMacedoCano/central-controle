package br.com.lcano.centraldecontrole.service.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.Lancamento;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.Despesa;
import br.com.lcano.centraldecontrole.dto.fluxocaixa.DespesaDTO;
import br.com.lcano.centraldecontrole.enums.TipoLancamentoEnum;
import br.com.lcano.centraldecontrole.repository.LancamentoItemRepository;
import br.com.lcano.centraldecontrole.repository.fluxocaixa.DespesaRepository;
import br.com.lcano.centraldecontrole.service.AbstractLancamentoItemService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DespesaService extends AbstractLancamentoItemService<Despesa, DespesaDTO> {

    @Autowired
    private final DespesaRepository repository;

    @Override
    protected LancamentoItemRepository<Despesa> getRepository() {
        return repository;
    }

    @Override
    protected DespesaDTO getDtoInstance() {
        return new DespesaDTO();
    }

    @Override
    protected void setLancamento(Despesa entity, Lancamento lancamento) {
        entity.setLancamento(lancamento);
    }

    @Override
    public TipoLancamentoEnum getTipoLancamento() {
        return TipoLancamentoEnum.DESPESA;
    }
}
