package br.com.lcano.centraldecontrole.resource.servicos;

import br.com.lcano.centraldecontrole.domain.servicos.ServidorConfig;
import br.com.lcano.centraldecontrole.service.servicos.ServidorConfigService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/servidor-config")
public class ServidorConfigResource {

    private final ServidorConfigService servidorConfigService;

    @GetMapping
    public ResponseEntity<ServidorConfig> getServidorConfig() {
        return servidorConfigService.find()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
