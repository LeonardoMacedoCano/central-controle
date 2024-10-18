package br.com.lcano.centraldecontrole.service.servicos;

import br.com.lcano.centraldecontrole.domain.servicos.Servico;
import br.com.lcano.centraldecontrole.dto.servicos.ServicoDTO;
import br.com.lcano.centraldecontrole.repository.servicos.ServicoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ServicoService {
    @Autowired
    private final ServicoRepository servicoRepository;

    public List<ServicoDTO> getAllServicos() {
        List<Servico> servicos = servicoRepository.findAll();
        return servicos.stream()
                .map(ServicoDTO::converterParaDTO)
                .collect(Collectors.toList());
    }

    public Optional<Servico> findById(Long id) {
        return servicoRepository.findById(id);
    }
}
