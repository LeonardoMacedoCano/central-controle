package br.com.lcano.centraldecontrole.service.servicos;

import br.com.lcano.centraldecontrole.domain.servicos.ServidorConfig;
import br.com.lcano.centraldecontrole.repository.servicos.ServidorConfigRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class ServidorConfigService {
    @Autowired
    private final ServidorConfigRepository servidorConfigRepository;

    public Optional<ServidorConfig> find() {
        return servidorConfigRepository.findById(1L);
    }
}