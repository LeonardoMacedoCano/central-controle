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
    private final FluxoCaixaConfigService fluxoCaixaConfigService;

    @GetMapping
    public ResponseEntity<FluxoCaixaConfigDTO> getConfig() {
        return ResponseEntity.ok(this.fluxoCaixaConfigService.getConfig());
    }

    @PostMapping
    public ResponseEntity<Object> saveConfig(@RequestBody FluxoCaixaConfigDTO fluxoCaixaConfigDTO) {
        Long id = this.fluxoCaixaConfigService.saveConfig(fluxoCaixaConfigDTO);
        return CustomSuccess.buildResponseEntity("Configuração salva com sucesso.", "id", id);
    }
}
