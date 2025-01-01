package br.com.lcano.centraldecontrole.resource.fluxocaixa;

import br.com.lcano.centraldecontrole.dto.fluxocaixa.RegraExtratoContaCorrenteDTO;
import br.com.lcano.centraldecontrole.service.fluxocaixa.RegraExtratoContaCorrenteService;
import br.com.lcano.centraldecontrole.util.CustomSuccess;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/regra-extrato-conta-corrente")
public class RegraExtratoContaCorrenteResource {
    @Autowired
    private final RegraExtratoContaCorrenteService service;

    @GetMapping
    public ResponseEntity<List<RegraExtratoContaCorrenteDTO>> findAllAsDto() {
        return ResponseEntity.ok(this.service.findAllAsDto());
    }

    @PostMapping
    public ResponseEntity<Object> validateAndSave(@RequestBody RegraExtratoContaCorrenteDTO extratoContaRegraDTO) {
        this.service.validateAndSave(extratoContaRegraDTO);
        return CustomSuccess.buildResponseEntity("Regra salva com sucesso.");
    }
}
