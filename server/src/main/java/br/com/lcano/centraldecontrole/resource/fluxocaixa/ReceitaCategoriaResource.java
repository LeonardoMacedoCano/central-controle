package br.com.lcano.centraldecontrole.resource.fluxocaixa;

import br.com.lcano.centraldecontrole.dto.fluxocaixa.ReceitaCategoriaDTO;
import br.com.lcano.centraldecontrole.service.fluxocaixa.ReceitaCategoriaService;
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
@RequestMapping("/api/receita-categoria")
public class ReceitaCategoriaResource {
    @Autowired
    private final ReceitaCategoriaService service;

    @GetMapping
    public ResponseEntity<List<ReceitaCategoriaDTO>> findAllAsDto() {
        List<ReceitaCategoriaDTO> categorias = service.findAllAsDto();

        List<ReceitaCategoriaDTO> categoriasOrdenadas = categorias
                .stream()
                .sorted(Comparator.comparing(ReceitaCategoriaDTO::getDescricao))
                .toList();

        return ResponseEntity.ok(categoriasOrdenadas);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ReceitaCategoriaDTO>> findAllPagedAsDto(Pageable pageable) {
        return ResponseEntity.ok(service.findAllPagedAsDto(pageable));
    }

    @PostMapping
    public ResponseEntity<Object> saveAsDto(@RequestBody ReceitaCategoriaDTO dto) {
        ReceitaCategoriaDTO receitaCategoriaDTO = service.saveAsDto(dto);
        return CustomSuccess.buildResponseEntity(
                "Categoria salva com sucesso.",
                "id",
                receitaCategoriaDTO.getId()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return CustomSuccess.buildResponseEntity("Categoria deletada com sucesso.");
    }
}
