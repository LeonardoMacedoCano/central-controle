package br.com.lcano.centraldecontrole.resource.servicos;

import br.com.lcano.centraldecontrole.dto.FilterDTO;
import br.com.lcano.centraldecontrole.dto.servicos.ServicoCategoriaDTO;
import br.com.lcano.centraldecontrole.dto.servicos.ServicoDTO;
import br.com.lcano.centraldecontrole.enums.servicos.ContainerActionEnum;
import br.com.lcano.centraldecontrole.service.servicos.ServicoService;
import br.com.lcano.centraldecontrole.util.CustomSuccess;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/servico")
public class ServicoResource {

    private final ServicoService servicoService;

    @PostMapping("/search")
    public ResponseEntity<Page<ServicoDTO>> getServicos(Pageable pageable,
                                                        @RequestBody(required = false) List<FilterDTO> filterDTOs) {
        return ResponseEntity.ok(servicoService.getServicos(pageable, filterDTOs));
    }

    @PostMapping("/status/{name}/{action}")
    public ResponseEntity<Object> changeContainerStatusByName(@PathVariable String name, @PathVariable ContainerActionEnum action) {
        this.servicoService.changeContainerStatusByName(name, action);
        return CustomSuccess.buildResponseEntity(String.format("Serviço %s com sucesso.", action.getDescricao()));
    }

    @GetMapping("/categorias")
    public ResponseEntity<List<ServicoCategoriaDTO>> getAllServicoCategoria() {
        return ResponseEntity.ok(servicoService.getAllServicoCategoria());
    }
}
