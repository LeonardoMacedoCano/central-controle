package br.com.lcano.centraldecontrole.resource.fluxocaixa;

import br.com.lcano.centraldecontrole.dto.fluxocaixa.ReceitaCategoriaDTO;
import br.com.lcano.centraldecontrole.service.fluxocaixa.ReceitaCategoriaService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/receita-categoria")
public class ReceitaCategoriaResource {
    @Autowired
    private final ReceitaCategoriaService service;

    @GetMapping
    public ResponseEntity<List<ReceitaCategoriaDTO>> findAllAsDto() {
        return ResponseEntity.ok(service.findAllAsDto());
    }
}
