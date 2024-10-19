package br.com.lcano.centraldecontrole.service.servicos;

import br.com.lcano.centraldecontrole.domain.servicos.ServicoCategoriaRel;
import br.com.lcano.centraldecontrole.repository.servicos.ServicoCategoriaRelRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ServicoCategoriaRelService {
    @Autowired
    private final ServicoCategoriaRelRepository servicoCategoriaRelRepository;

    public List<ServicoCategoriaRel> findByServicoId(Long servicoId) {
        return servicoCategoriaRelRepository.findByServicoId(servicoId);
    }
}
