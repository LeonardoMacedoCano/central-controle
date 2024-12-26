package br.com.lcano.centraldecontrole.service.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.fluxocaixa.ReceitaCategoria;
import br.com.lcano.centraldecontrole.dto.fluxocaixa.ReceitaCategoriaDTO;
import br.com.lcano.centraldecontrole.repository.fluxocaixa.ReceitaCategoriaRepository;
import br.com.lcano.centraldecontrole.service.AbstractGenericService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ReceitaCategoriaService extends AbstractGenericService<ReceitaCategoria, Long> {

    @Autowired
    private final ReceitaCategoriaRepository repository;

    @Override
    protected JpaRepository<ReceitaCategoria, Long> getRepository() {
        return repository;
    }

    @Override
    protected ReceitaCategoriaDTO getDtoInstance() {
        return new ReceitaCategoriaDTO();
    }
}
