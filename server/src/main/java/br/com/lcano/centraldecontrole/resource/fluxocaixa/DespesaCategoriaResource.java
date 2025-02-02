package br.com.lcano.centraldecontrole.resource.fluxocaixa;

import br.com.lcano.centraldecontrole.dto.fluxocaixa.DespesaCategoriaDTO;
import br.com.lcano.centraldecontrole.service.fluxocaixa.DespesaCategoriaService;
import br.com.lcano.centraldecontrole.util.CustomSuccess;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;

@AllArgsConstructor
@RestController
@RequestMapping("/api/despesa-categoria")
public class DespesaCategoriaResource {
    @Autowired
    private final DespesaCategoriaService service;

    @GetMapping
    public ResponseEntity<List<DespesaCategoriaDTO>> findAllAsDto() {
        List<DespesaCategoriaDTO> categorias = service.findAllAsDto();

        categorias.sort(Comparator.comparing(
                DespesaCategoriaDTO::getDescricao,
                Comparator.nullsFirst(String.CASE_INSENSITIVE_ORDER)
        ));

        return ResponseEntity.ok(categorias);
    }


    @GetMapping("/search")
    public ResponseEntity<Page<DespesaCategoriaDTO>> findAllPagedAsDto(Pageable pageable) {
        return ResponseEntity.ok(service.findAllPagedAsDto(pageable));
    }

    @PostMapping
    public ResponseEntity<Object> saveAsDto(@RequestBody DespesaCategoriaDTO dto) {
        DespesaCategoriaDTO despesaCategoriaBaseDTO = service.saveAsDto(dto);
        return CustomSuccess.buildResponseEntity(
                "Categoria salva com sucesso.",
                "id",
                despesaCategoriaBaseDTO.getId()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return CustomSuccess.buildResponseEntity("Categoria deletada com sucesso.");
    }
}
