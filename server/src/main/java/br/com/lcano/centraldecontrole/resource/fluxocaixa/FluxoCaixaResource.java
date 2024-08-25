package br.com.lcano.centraldecontrole.resource.fluxocaixa;

import br.com.lcano.centraldecontrole.dto.LancamentoDTO;
import br.com.lcano.centraldecontrole.service.fluxocaixa.FluxoCaixaService;
import br.com.lcano.centraldecontrole.util.CustomSuccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fluxo-caixa")
public class FluxoCaixaResource {

    @Autowired
    private final FluxoCaixaService fluxoCaixaService;

    public FluxoCaixaResource(FluxoCaixaService fluxoCaixaService) {
        this.fluxoCaixaService = fluxoCaixaService;
    }

    @PostMapping
    public ResponseEntity<Object> createLancamento(@RequestBody LancamentoDTO lancamentoDTO) {
        this.fluxoCaixaService.createLancamento(lancamentoDTO);
        return CustomSuccess.buildResponseEntity("Lançamento efetuado com sucesso.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateLancamento(@PathVariable Long id, @RequestBody LancamentoDTO lancamentoDTO) {
        this.fluxoCaixaService.updateLancamento(id, lancamentoDTO);
        return CustomSuccess.buildResponseEntity("Lançamento editado com sucesso.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteLancamento(@PathVariable Long id) {
        this.fluxoCaixaService.deleteLancamento(id);
        return CustomSuccess.buildResponseEntity("Lançamento deletado com sucesso.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<LancamentoDTO> getLancamento(@PathVariable Long id) {
        return ResponseEntity.ok(this.fluxoCaixaService.getLancamentoDTO(id));
    }
}
