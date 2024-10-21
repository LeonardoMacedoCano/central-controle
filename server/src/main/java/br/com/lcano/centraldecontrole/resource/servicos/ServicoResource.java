package br.com.lcano.centraldecontrole.resource.servicos;

import br.com.lcano.centraldecontrole.dto.servicos.ServicoDTO;
import br.com.lcano.centraldecontrole.enums.servicos.ContainerActionEnum;
import br.com.lcano.centraldecontrole.service.servicos.ServicoService;
import br.com.lcano.centraldecontrole.util.CustomSuccess;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Object> changeContainerStatusByName(@PathVariable String name, @PathVariable ContainerActionEnum action) {
        this.servicoService.changeContainerStatusByName(name, action);
        return CustomSuccess.buildResponseEntity(String.format("Serviço %s com sucesso.", action.getDescricao()));
    }
}
