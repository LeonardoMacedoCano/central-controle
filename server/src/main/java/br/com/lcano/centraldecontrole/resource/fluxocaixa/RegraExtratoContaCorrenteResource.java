package br.com.lcano.centraldecontrole.resource.fluxocaixa;

import br.com.lcano.centraldecontrole.dto.fluxocaixa.RegraExtratoContaCorrenteDTO;
import br.com.lcano.centraldecontrole.service.fluxocaixa.RegraExtratoContaCorrenteService;
import br.com.lcano.centraldecontrole.util.CustomSuccess;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/regra-extrato-conta-corrente")
public class RegraExtratoContaCorrenteResource {
    @Autowired
    private final RegraExtratoContaCorrenteService service;

    @GetMapping("/{id}")
    public ResponseEntity<RegraExtratoContaCorrenteDTO> findByIdAsDto(@PathVariable Long id) {
        return ResponseEntity.ok(this.service.findByIdAsDto(id));
    }

    @GetMapping
    public ResponseEntity<List<RegraExtratoContaCorrenteDTO>> findAllAsDto() {
        return ResponseEntity.ok(this.service.findAllAsDto());
    }

    @GetMapping("/search")
    public ResponseEntity<Page<RegraExtratoContaCorrenteDTO>> findAllPagedAsDto(Pageable pageable) {
        return ResponseEntity.ok(service.findAllPagedAsDto(pageable));
    }

    @PostMapping
    public ResponseEntity<Object> saveAsDto(@RequestBody RegraExtratoContaCorrenteDTO extratoContaRegraDTO) {
        RegraExtratoContaCorrenteDTO regraExtratoContaCorrenteDTO = service.saveAsDto(extratoContaRegraDTO);
        return CustomSuccess.buildResponseEntity(
                "Regra salva com sucesso.",
                "id",
                regraExtratoContaCorrenteDTO.getId()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return CustomSuccess.buildResponseEntity("Regra deletada com sucesso.");
    }
}
