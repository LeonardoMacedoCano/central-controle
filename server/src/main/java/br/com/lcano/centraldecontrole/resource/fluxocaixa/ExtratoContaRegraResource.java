package br.com.lcano.centraldecontrole.resource.fluxocaixa;

import br.com.lcano.centraldecontrole.dto.fluxocaixa.ExtratoContaRegraDTO;
import br.com.lcano.centraldecontrole.service.fluxocaixa.ExtratoContaRegraService;
import br.com.lcano.centraldecontrole.util.CustomSuccess;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/extrato-conta-regra")
public class ExtratoContaRegraResource {
    @Autowired
    private final ExtratoContaRegraService service;

    @GetMapping
    public ResponseEntity<List<ExtratoContaRegraDTO>> findAllAsDto() {
        return ResponseEntity.ok(this.service.findAllAsDto());
    }

    @PostMapping
    public ResponseEntity<Object> validateAndSave(@RequestBody ExtratoContaRegraDTO extratoContaRegraDTO) {
        this.service.validateAndSave(extratoContaRegraDTO);
        return CustomSuccess.buildResponseEntity("Regra salva com sucesso.");
    }
}
