package br.com.lcano.centraldecontrole.resource;

import br.com.lcano.centraldecontrole.dto.CategoriaDTO;
import br.com.lcano.centraldecontrole.service.CategoriaTarefaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/categoriatarefa")
public class CategoriaTarefaResource {
    @Autowired
    private final CategoriaTarefaService categoriaTarefaService;

    public CategoriaTarefaResource(CategoriaTarefaService categoriaTarefaService) {
        this.categoriaTarefaService = categoriaTarefaService;
    }

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> getTodasCategoriasTarefa() {
        List<CategoriaDTO> categoriasTarefa = categoriaTarefaService.getTodasCategoriasTarefa();
        return ResponseEntity.ok(categoriasTarefa);
    }
}
