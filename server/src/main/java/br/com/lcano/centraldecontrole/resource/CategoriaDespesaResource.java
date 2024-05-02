package br.com.lcano.centraldecontrole.resource;

import br.com.lcano.centraldecontrole.dto.CategoriaDTO;
import br.com.lcano.centraldecontrole.service.CategoriaDespesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/categoriadespesa")
public class CategoriaDespesaResource {
    @Autowired
    private final CategoriaDespesaService categoriaDespesaService;

    public CategoriaDespesaResource(CategoriaDespesaService categoriaDespesaService) {
        this.categoriaDespesaService = categoriaDespesaService;
    }

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> getTodasCategoriasDespesa() {
        List<CategoriaDTO> categoriasDespesa = categoriaDespesaService.getTodasCategoriasDespesa();
        return ResponseEntity.ok(categoriasDespesa);
    }
}
