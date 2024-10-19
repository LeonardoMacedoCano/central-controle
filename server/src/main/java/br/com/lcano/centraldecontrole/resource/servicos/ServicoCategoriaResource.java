package br.com.lcano.centraldecontrole.resource.servicos;

import br.com.lcano.centraldecontrole.dto.servicos.ServicoCategoriaDTO;
import br.com.lcano.centraldecontrole.service.servicos.ServicoCategoriaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/servico-categoria")
public class ServicoCategoriaResource {

    private final ServicoCategoriaService servicoCategoriaService;

    @GetMapping("/searchByServicoId/{id}")
    public ResponseEntity<List<ServicoCategoriaDTO>> getCategoriasByServicoId(@PathVariable Long id) {
        return ResponseEntity.ok(servicoCategoriaService.findByServicoId(id));
    }
}
