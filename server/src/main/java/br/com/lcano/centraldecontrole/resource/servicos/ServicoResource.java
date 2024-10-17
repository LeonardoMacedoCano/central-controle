package br.com.lcano.centraldecontrole.resource.servicos;

import br.com.lcano.centraldecontrole.domain.servicos.Servico;
import br.com.lcano.centraldecontrole.service.servicos.ServicoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/servico")
public class ServicoResource {

    private final ServicoService servicoService;

    @GetMapping
    public List<Servico> getAllServicos() {
        return servicoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Servico> getServicoById(@PathVariable Long id) {
        return servicoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
