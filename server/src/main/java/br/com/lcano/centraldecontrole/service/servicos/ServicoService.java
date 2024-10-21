package br.com.lcano.centraldecontrole.service.servicos;

import br.com.lcano.centraldecontrole.domain.servicos.Servico;
import br.com.lcano.centraldecontrole.dto.servicos.ServicoDTO;
import br.com.lcano.centraldecontrole.enums.servicos.ContainerActionEnum;
import br.com.lcano.centraldecontrole.enums.servicos.DockerStatusEnum;
import br.com.lcano.centraldecontrole.repository.servicos.ServicoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ServicoService {
    @Autowired
    private final ServicoRepository servicoRepository;
    @Autowired
    private final DockerService dockerService;
    @Autowired
    private final UsuarioServicoService usuarioServicoService;

    public List<ServicoDTO> getAllServicos() {
        List<Servico> servicos = servicoRepository.findAll();
        return servicos.stream()
                .map(servico -> {
                    DockerStatusEnum status = dockerService.getContainerStatusByName(servico.getNome());
                    Boolean hasPermission = usuarioServicoService.hasPermissionForService(servico.getId());
                    return ServicoDTO.converterParaDTO(servico, status, hasPermission);
                })
                .collect(Collectors.toList());
    }

    public DockerStatusEnum changeContainerStatusByName(String name, ContainerActionEnum action) {
        return dockerService.changeContainerStatusByName(name, action);
    }
}
