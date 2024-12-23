package br.com.lcano.centraldecontrole.resource.fluxocaixa;

import br.com.lcano.centraldecontrole.dto.CategoriaDTO;
import br.com.lcano.centraldecontrole.service.fluxocaixa.DespesaCategoriaService;
import br.com.lcano.centraldecontrole.util.CustomSuccess;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/despesa-categoria")
public class DespesaCategoriaResource {
    @Autowired
    private final DespesaCategoriaService despesaCategoriaService;

    @GetMapping
    public ResponseEntity<Page<CategoriaDTO>> getTodasCategorias(Pageable pageable) {
        return ResponseEntity.ok(despesaCategoriaService.getTodasCategorias(pageable));
    }

    @PostMapping
    public ResponseEntity<Object> saveCategoria(@RequestBody CategoriaDTO categoriaDTO) {
        Long id = this.despesaCategoriaService.saveCategoria(categoriaDTO);
        return CustomSuccess.buildResponseEntity("Categoria salva com sucesso.", "id", id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCategoria(@PathVariable Long id) {
        this.despesaCategoriaService.deleteCategoria(id);
        return CustomSuccess.buildResponseEntity("Categoria deletada com sucesso.");
    }
}
