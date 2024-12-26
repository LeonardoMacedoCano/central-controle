package br.com.lcano.centraldecontrole.service.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.fluxocaixa.DespesaCategoria;
import br.com.lcano.centraldecontrole.dto.fluxocaixa.DespesaCategoriaDTO;
import br.com.lcano.centraldecontrole.repository.fluxocaixa.DespesaCategoriaRepository;
import br.com.lcano.centraldecontrole.service.AbstractGenericService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class DespesaCategoriaService extends AbstractGenericService<DespesaCategoria, Long> {

    @Autowired
    private final DespesaCategoriaRepository repository;

    @Override
    protected DespesaCategoriaRepository getRepository() {
        return repository;
    }

    @Override
    protected DespesaCategoriaDTO getDtoInstance() {
        return new DespesaCategoriaDTO();
    }

    public DespesaCategoriaDTO findOrCreate(String descricaoCategoria) {
        return repository.findByDescricao(descricaoCategoria)
                .map(entity -> getDtoInstance().fromEntity(entity))
                .orElseGet(() -> {
                    DespesaCategoria novaCategoria = repository.save(new DespesaCategoria(descricaoCategoria));
                    return getDtoInstance().fromEntity(novaCategoria);
                });
    }
}
