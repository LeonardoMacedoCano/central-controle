package br.com.lcano.centraldecontrole.resource.fluxocaixa;

import br.com.lcano.centraldecontrole.dto.fluxocaixa.AtivoCategoriaDTO;
import br.com.lcano.centraldecontrole.service.fluxocaixa.AtivoCategoriaService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/ativo-categoria")
public class AtivoCategoriaResource {
    @Autowired
    private final AtivoCategoriaService ativoCategoriaService;

    @GetMapping
    public ResponseEntity<List<AtivoCategoriaDTO>> findAllAsDto() {
        return ResponseEntity.ok(ativoCategoriaService.findAllAsDto());
    }
}