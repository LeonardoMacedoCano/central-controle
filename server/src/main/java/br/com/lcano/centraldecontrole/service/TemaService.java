package br.com.lcano.centraldecontrole.service;

import br.com.lcano.centraldecontrole.domain.Tema;
import br.com.lcano.centraldecontrole.dto.TemaDTO;
import br.com.lcano.centraldecontrole.repository.TemaRepository;
import br.com.lcano.centraldecontrole.util.UsuarioUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class TemaService extends AbstractGenericService<Tema, Long> {
    @Autowired
    private final TemaRepository repository;
    @Autowired
    private final UsuarioUtil usuarioUtil;

    @Override
    protected TemaRepository getRepository() {
        return repository;
    }

    @Override
    protected TemaDTO getDtoInstance() {
        return new TemaDTO();
    }

    @Override
    public List<TemaDTO> findAllAsDto() {
        return repository.findByUsuarioIdOrUsuarioIsNull(usuarioUtil.getUsuarioAutenticado().getId()).stream()
                .map(entity -> getDtoInstance().fromEntity(entity))
                .toList();
    }

    public Optional<TemaDTO> findDefaultTheme() {
        return repository.findByIsDefault(Boolean.TRUE)
                .map(entity -> getDtoInstance().fromEntity(entity));
    }
}