package br.com.lcano.centraldecontrole.service.servicos;

import br.com.lcano.centraldecontrole.domain.servicos.ServicoCategoria;
import br.com.lcano.centraldecontrole.domain.servicos.ServicoCategoriaRel;
import br.com.lcano.centraldecontrole.dto.servicos.ServicoCategoriaDTO;
import br.com.lcano.centraldecontrole.repository.servicos.ServicoCategoriaRepository;
import br.com.lcano.centraldecontrole.service.AbstractGenericService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ServicoCategoriaService extends AbstractGenericService<ServicoCategoria, Long> {
    @Autowired
    private final ServicoCategoriaRepository repository;
    @Autowired
    private final ServicoCategoriaRelService servicoCategoriaRelService;

    @Override
    protected JpaRepository<ServicoCategoria, Long> getRepository() {
        return repository;
    }

    @Override
    protected ServicoCategoriaDTO getDtoInstance() {
        return new ServicoCategoriaDTO();
    }

    public List<ServicoCategoriaDTO> findByServicoId(Long servicoId) {
        List<ServicoCategoriaRel> servicoCategoriaRels = servicoCategoriaRelService.findByServicoId(servicoId);
        return servicoCategoriaRels.stream()
                .map(item -> new ServicoCategoriaDTO().fromEntity(item.getServicoCategoria()))
                .collect(Collectors.toList());
    }
}
