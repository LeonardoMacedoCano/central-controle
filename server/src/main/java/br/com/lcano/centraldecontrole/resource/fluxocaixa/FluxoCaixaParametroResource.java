package br.com.lcano.centraldecontrole.resource.fluxocaixa;

import br.com.lcano.centraldecontrole.dto.fluxocaixa.FluxoCaixaParametroDTO;
import br.com.lcano.centraldecontrole.service.fluxocaixa.FluxoCaixaParametroService;
import br.com.lcano.centraldecontrole.util.CustomSuccess;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/fluxo-caixa-parametro")
public class FluxoCaixaParametroResource {

    @Autowired
    private final FluxoCaixaParametroService service;

    @GetMapping
    public ResponseEntity<FluxoCaixaParametroDTO> findByUsuarioAsDto() {
        return ResponseEntity.ok(
                new FluxoCaixaParametroDTO().fromEntity(
                        this.service.findByUsuario()
                )
        );
    }

    @PostMapping
    public ResponseEntity<Object> saveAsDto(@RequestBody FluxoCaixaParametroDTO fluxoCaixaParametroDTO) {
        Long id = this.service.saveAsDto(fluxoCaixaParametroDTO).getId();
        return CustomSuccess.buildResponseEntity("Parâmetros salvos com sucesso.", "id", id);
    }
}
