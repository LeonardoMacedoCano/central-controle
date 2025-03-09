package br.com.lcano.centraldecontrole.resource;

import br.com.lcano.centraldecontrole.dto.FilterDTO;
import br.com.lcano.centraldecontrole.dto.NotificacaoDTO;
import br.com.lcano.centraldecontrole.service.NotificacaoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/notificacao")
public class NotificacaoResource {
    @Autowired
    private NotificacaoService service;

    @GetMapping("/{id}")
    public ResponseEntity<List<NotificacaoDTO>> findByIdAsDto(@PathVariable Long id) {
        return ResponseEntity.ok(this.service.findByIdAsDto(id));
    }

    @PostMapping("/search")
    public ResponseEntity<Page<NotificacaoDTO>> search(Pageable pageable,
                                                       @RequestBody(required = false) List<FilterDTO> filterDTOs) {
        return ResponseEntity.ok(service.search(pageable, filterDTOs));
    }

    @PostMapping("/{id}/read")
    public void markAsRead(@PathVariable Long id) {
        service.markAsRead(id);
    }
}
