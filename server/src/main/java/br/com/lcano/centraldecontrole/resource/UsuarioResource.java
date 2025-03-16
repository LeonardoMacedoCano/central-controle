package br.com.lcano.centraldecontrole.resource;

import br.com.lcano.centraldecontrole.dto.UsuarioFormDTO;
import br.com.lcano.centraldecontrole.service.UsuarioService;
import br.com.lcano.centraldecontrole.util.CustomSuccess;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/usuario")
public class UsuarioResource {
    private final UsuarioService service;

    @PutMapping
    public ResponseEntity<Object> updateAsDto(@ModelAttribute UsuarioFormDTO dto) throws Exception {
        this.service.updateAsDto(dto);
        return CustomSuccess.buildResponseEntity("Usuário salvo com sucesso.");
    }
}
