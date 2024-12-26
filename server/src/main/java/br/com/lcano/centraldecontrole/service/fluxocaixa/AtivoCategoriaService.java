package br.com.lcano.centraldecontrole.service.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.fluxocaixa.AtivoCategoria;
import br.com.lcano.centraldecontrole.dto.fluxocaixa.AtivoCategoriaDTO;
import br.com.lcano.centraldecontrole.repository.fluxocaixa.AtivoCategoriaRepository;
import br.com.lcano.centraldecontrole.service.AbstractGenericService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AtivoCategoriaService extends AbstractGenericService<AtivoCategoria, Long> {

    @Autowired
    private final AtivoCategoriaRepository repository;

    @Override
    protected JpaRepository<AtivoCategoria, Long> getRepository() {
        return repository;
    }

    @Override
    protected AtivoCategoriaDTO getDtoInstance() {
        return new AtivoCategoriaDTO();
    }
}
