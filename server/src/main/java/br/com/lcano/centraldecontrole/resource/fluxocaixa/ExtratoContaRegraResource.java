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
    private final ExtratoContaRegraService extratoContaRegraService;

    @GetMapping
    public ResponseEntity<List<ExtratoContaRegraDTO>> getRegras() {
        return ResponseEntity.ok(this.extratoContaRegraService.getRegras());
    }

    @PostMapping
    public ResponseEntity<Object> saveRegra(@RequestBody ExtratoContaRegraDTO extratoContaRegraDTO) {
        this.extratoContaRegraService.saveRegra(extratoContaRegraDTO);
        return CustomSuccess.buildResponseEntity("Regra salva com sucesso.");
    }
}
