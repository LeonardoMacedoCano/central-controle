package br.com.lcano.centraldecontrole.resource.fluxocaixa;

import br.com.lcano.centraldecontrole.dto.CategoriaDTO;
import br.com.lcano.centraldecontrole.service.fluxocaixa.DespesaCategoriaService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/despesa-categoria")
public class DespesaCategoriaResource {
    @Autowired
    private final DespesaCategoriaService despesaCategoriaService;

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> getTodasCategoriasDespesa() {
        List<CategoriaDTO> categoriasDespesa = despesaCategoriaService.getTodasCategoriasDespesa();
        return ResponseEntity.ok(categoriasDespesa);
    }
}
