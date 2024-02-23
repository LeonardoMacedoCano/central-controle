package br.com.lcano.centraldecontrole.resource;

import br.com.lcano.centraldecontrole.dto.CategoriaDTO;
import br.com.lcano.centraldecontrole.service.CategoriaIdeiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/categoriaideia")
public class CategoriaIdeiaResource {
    @Autowired
    private final CategoriaIdeiaService categoriaIdeiaService;

    public CategoriaIdeiaResource(CategoriaIdeiaService categoriaIdeiaService) {
        this.categoriaIdeiaService = categoriaIdeiaService;
    }

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> getTodasCategoriasIdeia() {
        List<CategoriaDTO> categoriasIdeia = categoriaIdeiaService.getTodasCategoriasIdeia();
        return ResponseEntity.ok(categoriasIdeia);
    }
}
