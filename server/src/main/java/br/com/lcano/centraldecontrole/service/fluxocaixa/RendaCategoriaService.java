package br.com.lcano.centraldecontrole.service.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.fluxocaixa.RendaCategoria;
import br.com.lcano.centraldecontrole.dto.fluxocaixa.RendaCategoriaDTO;
import br.com.lcano.centraldecontrole.repository.fluxocaixa.RendaCategoriaRepository;
import br.com.lcano.centraldecontrole.service.AbstractGenericService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RendaCategoriaService extends AbstractGenericService<RendaCategoria, Long> {

    @Autowired
    private final RendaCategoriaRepository repository;

    @Override
    protected RendaCategoriaRepository getRepository() {
        return repository;
    }

    @Override
    protected RendaCategoriaDTO getDtoInstance() {
        return new RendaCategoriaDTO();
    }
}
