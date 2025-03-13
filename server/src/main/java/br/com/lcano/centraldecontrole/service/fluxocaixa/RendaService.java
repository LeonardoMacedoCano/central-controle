package br.com.lcano.centraldecontrole.service.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.Lancamento;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.Renda;
import br.com.lcano.centraldecontrole.dto.fluxocaixa.RendaDTO;
import br.com.lcano.centraldecontrole.enums.TipoLancamento;
import br.com.lcano.centraldecontrole.repository.fluxocaixa.RendaRepository;
import br.com.lcano.centraldecontrole.service.AbstractLancamentoItemService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RendaService extends AbstractLancamentoItemService<Renda, RendaDTO> {

    @Autowired
    private final RendaRepository repository;

    @Override
    protected RendaRepository getRepository() {
        return repository;
    }

    @Override
    protected RendaDTO getDtoInstance() {
        return new RendaDTO();
    }

    @Override
    protected void setLancamento(Renda entity, Lancamento lancamento) {
        entity.setLancamento(lancamento);
    }

    @Override
    public TipoLancamento getTipoLancamento() {
        return TipoLancamento.RENDA;
    }
}