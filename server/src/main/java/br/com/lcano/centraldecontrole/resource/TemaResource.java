package br.com.lcano.centraldecontrole.resource;

import br.com.lcano.centraldecontrole.dto.TemaDTO;
import br.com.lcano.centraldecontrole.service.TemaService;
import br.com.lcano.centraldecontrole.util.CustomSuccess;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/tema")
public class TemaResource {
    @Autowired
    private final TemaService service;

    @GetMapping
    public ResponseEntity<List<TemaDTO>> findAllAsDto() {
        return ResponseEntity.ok(service.findAllAsDto());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TemaDTO> findByIdAsDto(@PathVariable Long id) {
        return ResponseEntity.ok(this.service.findByIdAsDto(id));
    }

    @GetMapping("/default")
    public ResponseEntity<TemaDTO> getDefaultTheme() {
        return service.findDefaultTheme()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Object> saveAsDto(@RequestBody TemaDTO dto) {
        Long id = this.service.saveAsDto(dto).getId();
        return CustomSuccess.buildResponseEntity("Tema salvo com sucesso.", "id", id);
    }
}
