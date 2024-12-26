package br.com.lcano.centraldecontrole.service.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.Lancamento;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.Ativo;
import br.com.lcano.centraldecontrole.dto.fluxocaixa.AtivoDTO;
import br.com.lcano.centraldecontrole.enums.TipoLancamentoEnum;
import br.com.lcano.centraldecontrole.repository.LancamentoItemRepository;
import br.com.lcano.centraldecontrole.repository.fluxocaixa.AtivoRepository;
import br.com.lcano.centraldecontrole.service.AbstractLancamentoItemService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AtivoService extends AbstractLancamentoItemService<Ativo, AtivoDTO> {

    @Autowired
    private final AtivoRepository repository;

    @Override
    protected LancamentoItemRepository<Ativo> getRepository() {
        return repository;
    }

    @Override
    protected AtivoDTO getDtoInstance() {
        return new AtivoDTO();
    }

    @Override
    protected void setLancamento(Ativo entity, Lancamento lancamento) {
        entity.setLancamento(lancamento);
    }

    @Override
    public TipoLancamentoEnum getTipoLancamento() {
        return TipoLancamentoEnum.ATIVO;
    }
}
