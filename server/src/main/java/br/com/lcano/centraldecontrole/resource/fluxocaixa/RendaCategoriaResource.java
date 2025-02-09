package br.com.lcano.centraldecontrole.resource.fluxocaixa;

import br.com.lcano.centraldecontrole.dto.fluxocaixa.RendaCategoriaDTO;
import br.com.lcano.centraldecontrole.service.fluxocaixa.RendaCategoriaService;
import br.com.lcano.centraldecontrole.util.CustomSuccess;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/renda-categoria")
public class RendaCategoriaResource {
    @Autowired
    private final RendaCategoriaService service;

    @GetMapping
    public ResponseEntity<List<RendaCategoriaDTO>> findAllAsDto() {
        List<RendaCategoriaDTO> categorias = service.findAllAsDto();

        categorias.sort(Comparator.comparing(
                RendaCategoriaDTO::getDescricao,
                Comparator.nullsFirst(String.CASE_INSENSITIVE_ORDER)
        ));

        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<RendaCategoriaDTO>> findAllPagedAsDto(Pageable pageable) {
        return ResponseEntity.ok(service.findAllPagedAsDto(pageable));
    }

    @PostMapping
    public ResponseEntity<Object> saveAsDto(@RequestBody RendaCategoriaDTO dto) {
        RendaCategoriaDTO rendaCategoriaDTO = service.saveAsDto(dto);
        return CustomSuccess.buildResponseEntity(
                "Categoria salva com sucesso.",
                "id",
                rendaCategoriaDTO.getId()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return CustomSuccess.buildResponseEntity("Categoria deletada com sucesso.");
    }
}
