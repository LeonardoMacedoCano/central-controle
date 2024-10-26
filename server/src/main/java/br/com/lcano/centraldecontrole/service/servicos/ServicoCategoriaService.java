package br.com.lcano.centraldecontrole.service.servicos;

import br.com.lcano.centraldecontrole.domain.servicos.ServicoCategoria;
import br.com.lcano.centraldecontrole.domain.servicos.ServicoCategoriaRel;
import br.com.lcano.centraldecontrole.dto.servicos.ServicoCategoriaDTO;
import br.com.lcano.centraldecontrole.repository.servicos.ServicoCategoriaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ServicoCategoriaService {
    @Autowired
    private final ServicoCategoriaRepository servicoCategoriaRepository;
    @Autowired
    private final ServicoCategoriaRelService servicoCategoriaRelService;

    public List<ServicoCategoriaDTO> findByServicoId(Long servicoId) {
        List<ServicoCategoriaRel> servicoCategoriaRels = servicoCategoriaRelService.findByServicoId(servicoId);
        return servicoCategoriaRels.stream()
                .map(ServicoCategoriaDTO::converterParaDTO)
                .collect(Collectors.toList());
    }

    public List<ServicoCategoriaDTO> getAll() {
        List<ServicoCategoria> servicoCategorias = servicoCategoriaRepository.findAll();
        return servicoCategorias.stream()
                .map(ServicoCategoriaDTO::converterParaDTO)
                .collect(Collectors.toList());
    }
}
