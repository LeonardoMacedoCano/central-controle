package br.com.lcano.centraldecontrole.resource.servicos;

import br.com.lcano.centraldecontrole.domain.servicos.ServidorConfig;
import br.com.lcano.centraldecontrole.service.servicos.ServidorConfigService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/servidor-config")
public class ServidorConfigResource {

    @Autowired
    private final ServidorConfigService service;

    @GetMapping
    public ResponseEntity<ServidorConfig> getServidorConfig() {
        return service.find()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
