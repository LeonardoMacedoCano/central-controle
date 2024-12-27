package br.com.lcano.centraldecontrole.resource;

import br.com.lcano.centraldecontrole.dto.FilterDTO;
import br.com.lcano.centraldecontrole.dto.LancamentoDTO;
import br.com.lcano.centraldecontrole.service.LancamentoService;
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
@RequestMapping("/api/lancamento")
public class LancamentoResource {
    @Autowired
    private final LancamentoService lancamentoService;

    @PostMapping
    public ResponseEntity<Object> saveAsDto(@RequestBody LancamentoDTO lancamentoDTO) {
        Long id = this.lancamentoService.saveAsDto(lancamentoDTO).getId();
        return CustomSuccess.buildResponseEntity("Lançamento salvo com sucesso.", "id", id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable Long id) {
        this.lancamentoService.deleteById(id);
        return CustomSuccess.buildResponseEntity("Lançamento deletado com sucesso.");
    }

    @PostMapping("/search")
    public ResponseEntity<Page<LancamentoDTO>> search(Pageable pageable,
                                                              @RequestBody(required = false) List<FilterDTO> filterDTOs) {
        return ResponseEntity.ok(lancamentoService.search(pageable, filterDTOs));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LancamentoDTO> findByIdAsDto(@PathVariable Long id) {
        return ResponseEntity.ok(this.lancamentoService.findByIdAsDto(id));
    }
}
