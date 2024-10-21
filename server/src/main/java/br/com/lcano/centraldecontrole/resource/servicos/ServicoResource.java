package br.com.lcano.centraldecontrole.resource.servicos;

import br.com.lcano.centraldecontrole.dto.servicos.ServicoDTO;
import br.com.lcano.centraldecontrole.enums.servicos.ContainerActionEnum;
import br.com.lcano.centraldecontrole.enums.servicos.DockerStatusEnum;
import br.com.lcano.centraldecontrole.service.servicos.ServicoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/servico")
public class ServicoResource {

    private final ServicoService servicoService;

    @GetMapping
    public List<ServicoDTO> getAllServicos() {
        return servicoService.getAllServicos();
    }

    @PostMapping("/status/{name}/{action}")
    public DockerStatusEnum changeContainerStatusByName(@PathVariable String name, @PathVariable ContainerActionEnum action) {
        return servicoService.changeContainerStatusByName(name, action);
    }
}
