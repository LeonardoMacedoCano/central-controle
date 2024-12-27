package br.com.lcano.centraldecontrole.resource.fluxocaixa;

import br.com.lcano.centraldecontrole.dto.fluxocaixa.FluxoCaixaConfigDTO;
import br.com.lcano.centraldecontrole.service.fluxocaixa.FluxoCaixaConfigService;
import br.com.lcano.centraldecontrole.util.CustomSuccess;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/fluxo-caixa-config")
public class FluxoCaixaConfigResource {

    @Autowired
    private final FluxoCaixaConfigService service;

    @GetMapping
    public ResponseEntity<FluxoCaixaConfigDTO> findByUsuarioAsDto() {
        return ResponseEntity.ok(this.service.findByUsuarioAsDto());
    }

    @PostMapping
    public ResponseEntity<Object> saveAsDto(@RequestBody FluxoCaixaConfigDTO fluxoCaixaConfigDTO) {
        Long id = this.service.saveAsDto(fluxoCaixaConfigDTO).getId();
        return CustomSuccess.buildResponseEntity("Configuração salva com sucesso.", "id", id);
    }
}
