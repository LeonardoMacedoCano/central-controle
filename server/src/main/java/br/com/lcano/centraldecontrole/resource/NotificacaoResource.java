package br.com.lcano.centraldecontrole.resource;

import br.com.lcano.centraldecontrole.dto.FilterDTO;
import br.com.lcano.centraldecontrole.dto.NotificacaoDTO;
import br.com.lcano.centraldecontrole.service.NotificacaoService;
import br.com.lcano.centraldecontrole.util.CustomSuccess;
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
    public ResponseEntity<NotificacaoDTO> findByIdAsDto(@PathVariable Long id) {
        return ResponseEntity.ok(this.service.findByIdAsDto(id));
    }

    @PostMapping("/search")
    public ResponseEntity<Page<NotificacaoDTO>> search(Pageable pageable,
                                                       @RequestBody(required = false) List<FilterDTO> filterDTOs) {
        return ResponseEntity.ok(service.search(pageable, filterDTOs));
    }

    @PostMapping("/{id}/alterar-status/{visto}")
    public void alterStatus(@PathVariable Long id,
                            @PathVariable Boolean visto) {
        service.alterStatus(id, visto);
    }

    @GetMapping("/nao-lidas/total")
    public ResponseEntity<Long> getTotalNotificacoesNaoLidas() {
        return ResponseEntity.ok(service.getTotalNotificacoesNaoLidas());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable Long id) {
        this.service.deleteById(id);
        return CustomSuccess.buildResponseEntity("Notificação deletada com sucesso.");
    }
}
